package io.empowerhack.hub.controller.advice;

import io.empowerhack.hub.domain.User;
import io.empowerhack.hub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class ProfileCheckAdvice {

    @Autowired
    UserService userService;

    @ModelAttribute("info")
    public Boolean getCurrentUser(Principal principal) {

        if (principal == null) {
            return null;
        }

        User user = userService.findByCurrentUser();

        if (user.getName() != null) {
            return null;
        }

        return true;
    }
}
