package io.empowerhack.hub.service;

import io.empowerhack.hub.domain.Member;
import io.empowerhack.hub.domain.member.Interest;
import io.empowerhack.hub.repository.InterestRepository;
import io.empowerhack.hub.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InterestService {

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private MemberService memberService;

    public Member save(Interest interest) {

        Member existingMember = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        existingMember.getInterests().add(interest);
        existingMember.setUpdatedOn(new Date());

        this.memberService.save(existingMember);

        return existingMember;
    }

    public Member delete(String uid) {

        Member existingMember = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        // @ToDo: check if Interest belongs to user
        Interest interest = this.interestRepository.findOneByUid(uid);

        existingMember.getInterests().remove(interest);
        existingMember.setUpdatedOn(new Date());

        this.memberService.save(existingMember);

        return existingMember;
    }
}
