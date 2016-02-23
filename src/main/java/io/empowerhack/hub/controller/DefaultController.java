package io.empowerhack.hub.controller;

import io.empowerhack.hub.domain.User;
import io.empowerhack.hub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public final class DefaultController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String overview(Model model) {
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "default/overview";
    }
}
