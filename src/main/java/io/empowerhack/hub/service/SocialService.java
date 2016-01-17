package io.empowerhack.hub.service;

import io.empowerhack.hub.domain.Member;
import io.empowerhack.hub.domain.member.Social;
import io.empowerhack.hub.repository.SocialRepository;
import io.empowerhack.hub.repository.SocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SocialService {

    @Autowired
    private SocialRepository socialRepository;

    @Autowired
    private MemberService memberService;

    public Member save(Social social) {

        Member existingMember = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        existingMember.getSocials().add(social);
        existingMember.setUpdatedOn(new Date());

        this.memberService.save(existingMember);

        return existingMember;
    }

    public Member delete(String uid) {

        Member existingMember = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        // @ToDo: check if Social belongs to user
        Social social = this.socialRepository.findOneByUid(uid);

        existingMember.getSocials().remove(social);
        existingMember.setUpdatedOn(new Date());

        this.memberService.save(existingMember);

        return existingMember;
    }
}
