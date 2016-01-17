package io.empowerhack.hub.service;

import io.empowerhack.hub.domain.Member;
import io.empowerhack.hub.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        memberRepository.findAll().forEach(item->{
            // @ToDo: check against GitHub members
            members.add(item);
        });

        return members;
    }

    public Member save(Member member) {

        Member existingMember = this.findByUsername(member.getUsername());
        if (existingMember != null) {
            existingMember.setName(member.getName());
            existingMember.setEmail(member.getEmail());
            existingMember.setAvailability(member.getAvailability());
            existingMember.setKeepPrivate(member.isKeepPrivate());
            existingMember.setAvatarUrl(member.getAvatarUrl());
            existingMember.setUpdatedOn(new Date());

            return memberRepository.save(existingMember);
        }

        member.setCreatedOn(new Date());

        return memberRepository.save(member);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
