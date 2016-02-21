package io.empowerhack.hub.definitions.helpers;

import cucumber.api.java.en.Given;
import io.empowerhack.hub.HubApplicationTests;
import io.empowerhack.hub.domain.User;
import io.empowerhack.hub.repository.UserRepository;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationDefinitions extends HubApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Given("^I am logged in$")
    public void I_am_logged_in() {
        driver.get(withBaseUrl("/login"));
        driver.findElement(By.name("submit")).click();
        String logout = driver.findElement(By.id("logout")).getAttribute("value");

        Assert.assertEquals("Logout", logout);
    }

    @Given("^I am not logged in$")
    public void I_am_not_logged_in() {
        driver.get(withBaseUrl("/"));

        try {
            driver.findElement(By.id("logout")).submit();
        } catch (NoSuchElementException e) {

        }
    }

    @Given("^I have not logged in before")
    public void I_have_not_logged_in_before() {
        User user = userRepository.findByUid(getUser().getUid());

        if (user != null) {
            userRepository.delete(user);
        }
    }
}
