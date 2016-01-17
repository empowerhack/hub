package io.empowerhack.hub;

import io.empowerhack.hub.domain.Member;
import io.empowerhack.hub.repository.MemberRepository;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.OrganizationService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;

import java.util.List;

@EnableOAuth2Sso
@SpringBootApplication
public class HubApplication {

    public static void main(String[] args) {
        SpringApplication.run(HubApplication.class, args);
    }

//    @Bean
//    public boolean getData(MemberRepository memberRepository) throws Exception {
//		System.out.println("----------------------");
//		RepositoryService repositoryService = new RepositoryService();
//
//		try {
////			System.out.println(repositoryService.getRepositories("empowerhack").get(1).getName());
//			System.out.println(repositoryService.getContributors(new RepositoryId("empowerhack", "Family-MemberService"), false));
//		} catch(Exception e) {
//			System.out.println(e);
//		}

//		System.out.println("----------------------");
//		UserService userService = new UserService();
//
//		try {
//			System.out.println(userService.getUser("eddiejaoude").getAvatarUrl());
//		} catch(Exception e) {
//			System.out.println(e);
//		}

//        System.out.println("----------------------");
//        OrganizationService organizationService = new OrganizationService();
//
//        List<User> users = organizationService.getMembers("empowerhack");
//        users.stream().forEach((user) -> {
//            Member member = new Member(user.getId(), user.getLogin());
//            member.setPrivate(false);
//            memberRepository.save(member);
//        });
//
//        System.out.println(memberRepository.findAll());

//        return true;
//    }
}
