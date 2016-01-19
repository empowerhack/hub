package io.empowerhack.hub.controller;

import io.empowerhack.hub.domain.Calendar;
import io.empowerhack.hub.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@EnableOAuth2Sso
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @RequestMapping(value = {"/calendar"}, method = RequestMethod.GET)
    public String list(Model model) {

        List<Calendar> calendars  = this.calendarService.findAll();

        model.addAttribute("calendars", calendars);
        model.addAttribute("total", calendars.size());

        return "calendar/list";
    }

    @RequestMapping(value = {"/calendar/add"}, method = RequestMethod.GET)
    public String add(Model model) {

        Calendar calendar = new Calendar();

        model.addAttribute("calendar", calendar);

        return "calendar/add";
    }

    @RequestMapping(value = {"/calendar/edit/{uid}"}, method = RequestMethod.GET)
    public String edit(@PathVariable("uid") String uid, Model model, final RedirectAttributes redirectAttributes) {

        Calendar calendar  = this.calendarService.findByUid(uid);
        if (calendar == null) {
            redirectAttributes.addFlashAttribute("error", "Not found");

            return "redirect:/calendar";
        }

        model.addAttribute("uid", uid);
        model.addAttribute("calendar", calendar);

        return "calendar/edit";
    }

    @RequestMapping(value = {"/calendar/add"}, method = RequestMethod.POST)
    public String addSave(@Valid Calendar calendar, BindingResult bindingResult, final RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "An error occurred please try again");

            return "calendar/add";
        }

        this.calendarService.save(calendar);
        redirectAttributes.addFlashAttribute("success", "Successfully saved");

        return "redirect:/calendar";
    }

    @RequestMapping(value = {"/calendar/edit/{uid}"}, method = RequestMethod.POST)
    public String editSave(@PathVariable("uid") String uid, @Valid Calendar calendar, BindingResult bindingResult, final RedirectAttributes redirectAttributes, Model model) {

        this.calendarService.findByUid(uid);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "An error occurred please try again");

            return "calendar/edit";
        }

        this.calendarService.save(calendar);
        redirectAttributes.addFlashAttribute("success", "Successfully saved");

        return "redirect:/calendar";
    }
}
