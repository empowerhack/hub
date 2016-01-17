package io.empowerhack.hub.controller;

import io.empowerhack.hub.domain.Member;
import io.empowerhack.hub.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@EnableOAuth2Sso
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = {"/member"}, method = RequestMethod.GET)
    public String list(Model model) {

        List<Member> members = memberService.findAll();

        model.addAttribute("members", members);
        model.addAttribute("total", members.size());

        return "member/list";
    }
}
