package io.empowerhack.hub.controller;

import io.empowerhack.hub.domain.Member;
import io.empowerhack.hub.domain.member.Skill;
import io.empowerhack.hub.service.MemberService;
import io.empowerhack.hub.service.SkillService;
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
import java.security.Principal;
import java.util.List;

@Controller
@EnableOAuth2Sso
public class SkillController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SkillService skillService;

    @RequestMapping(value = {"/account/skill"}, method = RequestMethod.GET)
    public String edit(Model model, final RedirectAttributes redirectAttributes) {

        Member member  = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "You must complete your Profile first");

            return "redirect:/account/profile";
        }

        model.addAttribute("skills", member.getSkills());
        model.addAttribute("total", member.getSkills().size());
        model.addAttribute("skill", new Skill());

        return "skill/edit";
    }

    @RequestMapping(value = {"/account/skill"}, method = RequestMethod.POST)
    public String save(@Valid Skill skill, BindingResult bindingResult, final RedirectAttributes redirectAttributes, Model model) {

        Member member  = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "You must complete your Profile first");

            return "redirect:/account/profile";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "An error occurred please try again");
            model.addAttribute("skills", member.getSkills());
            model.addAttribute("total", member.getSkills().size());

            return "skill/edit";
        }

        this.skillService.save(skill);

        redirectAttributes.addFlashAttribute("success", "Skill added");

        return "redirect:/account/skill";
    }

    @RequestMapping(value = {"/account/skill/delete/{uid}"}, method = RequestMethod.GET)
    public String delete(@PathVariable("uid") String uid, final RedirectAttributes redirectAttributes) {

        Member member  = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "You must complete your Profile first");

            return "redirect:/account/profile";
        }

        this.skillService.delete(uid);

        redirectAttributes.addFlashAttribute("success", "Skill removed");

        return "redirect:/account/skill";
    }
}
