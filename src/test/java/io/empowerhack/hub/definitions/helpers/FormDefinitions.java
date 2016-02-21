package io.empowerhack.hub.definitions.helpers;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.empowerhack.hub.HubApplicationTests;
import org.junit.Assert;
import org.openqa.selenium.By;

public class FormDefinitions extends HubApplicationTests {

    @When("^I fill in the field (.*) with (.*)$")
    public void I_fill_in_the_field(String field, String value) {
        driver.findElement(By.id(field)).clear();
        driver.findElement(By.id(field)).sendKeys(value);
    }

    @When("^I check the field (.*)$")
    public void I_check_the_field(String field) {
        if (!driver.findElement(By.id(field)).isSelected()) {
            driver.findElement(By.id(field)).click();
        }
    }

    @When("^I uncheck the field (.*)$")
    public void I_uncheck_the_field(String field) {
        if (driver.findElement(By.id(field)).isSelected()) {
            driver.findElement(By.id(field)).click();
        }
    }

    @Then("^Submit the form (.*)$")
    public void I_submit_form(String id) {
        driver.findElement(By.id(id)).submit();
    }

    @Then("^the field (.*) contains (.*)$")
    public void I_submit_form(String id, String expected) {
        String actual = driver.findElement(By.id(id)).getAttribute("value");

        Assert.assertEquals(expected, actual);
    }
}
