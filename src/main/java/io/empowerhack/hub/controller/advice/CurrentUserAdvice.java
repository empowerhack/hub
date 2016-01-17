package io.empowerhack.hub.controller.advice;

import io.empowerhack.hub.domain.Member;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class CurrentUserAdvice {

    @ModelAttribute("currentUser")
    public Member getCurrentUser(Principal principal) {

        if (principal == null) {
            return new Member(null);
        }

        return new Member(principal.getName());
    }
}
