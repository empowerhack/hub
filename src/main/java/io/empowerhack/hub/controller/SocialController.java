package io.empowerhack.hub.controller;

import io.empowerhack.hub.domain.Member;
import io.empowerhack.hub.domain.member.Social;
import io.empowerhack.hub.service.MemberService;
import io.empowerhack.hub.service.SocialService;
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

@Controller
@EnableOAuth2Sso
public class SocialController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SocialService socialService;

    @RequestMapping(value = {"/account/social"}, method = RequestMethod.GET)
    public String edit(Model model, final RedirectAttributes redirectAttributes) {

        Member member  = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "You must complete your Profile first");

            return "redirect:/account/profile";
        }

        model.addAttribute("socials", member.getSocials());
        model.addAttribute("total", member.getSocials().size());
        model.addAttribute("social", new Social());

        return "social/edit";
    }

    @RequestMapping(value = {"/account/social"}, method = RequestMethod.POST)
    public String save(@Valid Social social, BindingResult bindingResult, final RedirectAttributes redirectAttributes, Model model) {

        Member member  = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "You must complete your Profile first");

            return "redirect:/account/profile";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "An error occurred please try again");
            model.addAttribute("socials", member.getSocials());
            model.addAttribute("total", member.getSocials().size());

            return "social/edit";
        }

        this.socialService.save(social);

        redirectAttributes.addFlashAttribute("success", "Social added");

        return "redirect:/account/social";
    }

    @RequestMapping(value = {"/account/social/delete/{uid}"}, method = RequestMethod.GET)
    public String delete(@PathVariable("uid") String uid, final RedirectAttributes redirectAttributes) {

        Member member  = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "You must complete your Profile first");

            return "redirect:/account/profile";
        }

        this.socialService.delete(uid);

        redirectAttributes.addFlashAttribute("success", "Social removed");

        return "redirect:/account/social";
    }
}
