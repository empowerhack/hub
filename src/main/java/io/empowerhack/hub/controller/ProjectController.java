package io.empowerhack.hub.controller;

import io.empowerhack.hub.domain.Project;
import io.empowerhack.hub.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = {"/project"}, method = RequestMethod.GET)
    public String list(Model model) {

        List<Project> projects  = this.projectService.findAll();

        model.addAttribute("projects", projects);
        model.addAttribute("total", projects.size());

        return "project/list";
    }

    @RequestMapping(value = {"/project/add"}, method = RequestMethod.GET)
    public String add(Model model) {

        Project project = new Project();

        model.addAttribute("project", project);

        return "project/add";
    }

    @RequestMapping(value = {"/project/edit/{uid}"}, method = RequestMethod.GET)
    public String edit(@PathVariable("uid") String uid, Model model, final RedirectAttributes redirectAttributes) {

        Project project  = this.projectService.findByUid(uid);
        if (project == null) {
            redirectAttributes.addFlashAttribute("error", "Not found");

            return "redirect:/project";
        }

        model.addAttribute("uid", uid);
        model.addAttribute("project", project);

        return "project/edit";
    }

    @RequestMapping(value = {"/project/add"}, method = RequestMethod.POST)
    public String addSave(@Valid Project project, BindingResult bindingResult, final RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "An error occurred please try again");

            return "project/add";
        }

        this.projectService.save(project);
        redirectAttributes.addFlashAttribute("success", "Successfully saved");

        return "redirect:/project";
    }

    @RequestMapping(value = {"/project/edit/{uid}"}, method = RequestMethod.POST)
    public String editSave(@PathVariable("uid") String uid, @Valid Project project, BindingResult bindingResult, final RedirectAttributes redirectAttributes, Model model) {

        this.projectService.findByUid(uid);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "An error occurred please try again");

            return "project/edit";
        }

        this.projectService.save(project);
        redirectAttributes.addFlashAttribute("success", "Successfully saved");

        return "redirect:/project";
    }
}
