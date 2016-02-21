package io.empowerhack.hub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultController {

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String overview() {

        return "default/overview";
    }
}
