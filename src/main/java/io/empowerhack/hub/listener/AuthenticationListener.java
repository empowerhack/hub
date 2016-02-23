package io.empowerhack.hub.listener;

import io.empowerhack.hub.domain.User;
import io.empowerhack.hub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public final class AuthenticationListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(final InteractiveAuthenticationSuccessEvent event) {
        User user = userService.findByUid(event.getAuthentication().getName());

        if (user == null) {
            userService.save(new User(event.getAuthentication().getName()));
        }

        user = userService.findByUid(event.getAuthentication().getName());
        userService.save(user);
    }
}
