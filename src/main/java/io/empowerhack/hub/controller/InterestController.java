package io.empowerhack.hub.controller;

import io.empowerhack.hub.domain.Member;
import io.empowerhack.hub.domain.member.Interest;
import io.empowerhack.hub.service.MemberService;
import io.empowerhack.hub.service.InterestService;
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
public class InterestController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private InterestService interestService;

    @RequestMapping(value = {"/account/interest"}, method = RequestMethod.GET)
    public String edit(Model model, final RedirectAttributes redirectAttributes) {

        Member member  = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("interests", member.getInterests());
        model.addAttribute("total", member.getInterests().size());
        model.addAttribute("interest", new Interest());

        return "interest/edit";
    }

    @RequestMapping(value = {"/account/interest"}, method = RequestMethod.POST)
    public String save(@Valid Interest interest, BindingResult bindingResult, final RedirectAttributes redirectAttributes, Model model) {

        Member member  = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "An error occurred please try again");
            model.addAttribute("interests", member.getInterests());
            model.addAttribute("total", member.getInterests().size());

            return "interest/edit";
        }

        this.interestService.save(interest);

        redirectAttributes.addFlashAttribute("success", "Interest added");

        return "redirect:/account/interest";
    }

    @RequestMapping(value = {"/account/interest/delete/{uid}"}, method = RequestMethod.GET)
    public String delete(@PathVariable("uid") String uid, final RedirectAttributes redirectAttributes) {

        this.interestService.delete(uid);

        redirectAttributes.addFlashAttribute("success", "Interest removed");

        return "redirect:/account/interest";
    }
}
