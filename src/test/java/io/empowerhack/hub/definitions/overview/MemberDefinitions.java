package io.empowerhack.hub.definitions.overview;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.empowerhack.hub.HubApplicationTests;
import io.empowerhack.hub.domain.User;
import io.empowerhack.hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.UUID;

public class MemberDefinitions extends HubApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Given("^There are (\\S+) Members in the application$")
    public void there_are_members_in_the_application(String isPrivate) {

        Random rand = new Random();
        Integer size = rand.nextInt(10) + 1;

        for(int i=1; i<=size; i++){
            User user = new User(UUID.randomUUID().toString());
            user.setName(faker.name().fullName());
            user.setEmail(faker.name().firstName() + "@" + faker.name().lastName() + ".com");
            if (isPrivate.equals("public")) {
                user.setPrivate(false);
            }
            if (isPrivate.equals("private")) {
                user.setPrivate(true);
            }

            userRepository.save(user);
        }
    }
}
