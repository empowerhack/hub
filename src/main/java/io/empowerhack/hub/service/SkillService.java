package io.empowerhack.hub.service;

import io.empowerhack.hub.domain.Member;
import io.empowerhack.hub.domain.member.Skill;
import io.empowerhack.hub.repository.MemberRepository;
import io.empowerhack.hub.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private MemberService memberService;

    public Member save(Skill skill) {

        Member existingMember = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        existingMember.getSkills().add(skill);
        existingMember.setUpdatedOn(new Date());

        this.memberService.save(existingMember);

        return existingMember;
    }

    public Member delete(String uid) {

        Member existingMember = this.memberService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        // @ToDo: check if Skill belongs to user
        Skill skill = this.skillRepository.findOneByUid(uid);

        existingMember.getSkills().remove(skill);
        existingMember.setUpdatedOn(new Date());

        this.memberService.save(existingMember);

        return existingMember;
    }
}
