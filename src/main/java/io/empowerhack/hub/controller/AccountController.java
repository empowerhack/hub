package io.empowerhack.hub.controller;

import io.empowerhack.hub.domain.User;
import io.empowerhack.hub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public final class AccountController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public String profile(Model model) {

        User user = userService.findByCurrentUser();
        model.addAttribute("user", user);

        return "account/profile";
    }

    @RequestMapping(value = {"/profile"}, method = RequestMethod.POST)
    public String profileSave(
            @Valid User user,
            BindingResult bindingResult,
            final RedirectAttributes redirectAttributes,
            Model model
    ) {

        userService.findByCurrentUser();
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "An error occurred please try again");

            return "account/profile";
        }

        userService.saveByCurrentUser(user);
        redirectAttributes.addFlashAttribute("success", "Successfully saved");

        return "redirect:/profile";
    }
}
