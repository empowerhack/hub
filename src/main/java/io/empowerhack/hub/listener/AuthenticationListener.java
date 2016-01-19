package io.empowerhack.hub.listener;

import io.empowerhack.hub.domain.Member;
import io.empowerhack.hub.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    @Autowired
    private MemberService memberService;

    @Override
    public void onApplicationEvent(final InteractiveAuthenticationSuccessEvent event) {
        Member member = this.memberService.findByUsername(event.getAuthentication().getName());

        if (member == null) {
            this.memberService.save(new Member(event.getAuthentication().getName()));
        }
    }
}
