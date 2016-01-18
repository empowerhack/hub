package io.empowerhack.hub.controller;

import io.empowerhack.hub.domain.Member;
import io.empowerhack.hub.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@EnableOAuth2Sso
public class ProfileController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = {"/account/profile"}, method = RequestMethod.GET)
    public String edit(Model model) {

        Member member  = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (member == null) {
            member = new Member(SecurityContextHolder.getContext().getAuthentication().getName());
        }

        model.addAttribute("member", member);

        return "profile/edit";
    }

    @RequestMapping(value = {"/account/profile"}, method = RequestMethod.POST)
    public String save(@Valid Member member, BindingResult bindingResult, final RedirectAttributes redirectAttributes, Model model) {

        member.setUsername(SecurityContextHolder.getContext().getAuthentication().getName()); // force the username to the current logged in user
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "An error occurred please try again");

            return "profile/edit";
        }

        this.memberService.save(member);
        redirectAttributes.addFlashAttribute("success", "Successfully saved");

        return "redirect:/account/profile";
    }
}
