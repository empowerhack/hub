package io.empowerhack.hub.guest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.empowerhack.hub.HubApplicationTests;
import org.junit.Assert;

public class GuestDefinitions extends HubApplicationTests {

    @Given("^I am not logged in$")
    public void I_am_not_logged_in() {
//        driver.get(withBaseUrl("/logout")); // Only required when Authentication is done
    }

    @When("^I go to (.+)$")
    public void I_go_to_page(String uri) {
        driver.get(withBaseUrl(uri));
    }

    @Then("^I get redirected to (.+)$")
    public void I_get_redirected_to(String path) {
        Assert.assertEquals(this.getBaseUrl() + path, driver.getCurrentUrl());
    }

    @Then("^I see the text (.+)$")
    public void I_see_the_text(String text) {
        Assert.assertEquals(true, driver.getPageSource().contains(text));
    }

    @Then("^I close the browser$")
    public void I_close_the_browser() {
        driver.close();
    }
}
