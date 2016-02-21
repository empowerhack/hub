package io.empowerhack.hub.definitions.overview;

import cucumber.api.java.en.Given;
import io.empowerhack.hub.HubApplicationTests;
import io.empowerhack.hub.domain.User;
import io.empowerhack.hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class MemberDefinitions extends HubApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Given("^There are public (\\\\d+) Members in the application$")
    public void there_are_public_members_in_the_application(Integer size) {
        for(int i=1; i<=size; i++){
            createUser(false);
        }
    }

    @Given("^There are private (\\\\d+) Members in the application$")
    public void there_are_private_members_in_the_application(Integer size) {
        for(int i=1; i<=size; i++){
            createUser(true);
        }
    }

    private void createUser(Boolean isPrivate) {
        User user = new User(UUID.randomUUID().toString());
        user.setName(faker.name().fullName());
        user.setEmail(faker.name().firstName() + "@" + faker.name().lastName() + ".com");
        user.setPrivate(isPrivate);

        userRepository.save(user);
    }

    @Given("^There are no members$")
    public void there_are_no_members() {
        userRepository.deleteAll();
    }
}
