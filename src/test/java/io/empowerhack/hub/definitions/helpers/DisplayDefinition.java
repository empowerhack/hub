package io.empowerhack.hub.definitions.helpers;

import cucumber.api.java.en.Then;
import io.empowerhack.hub.HubApplicationTests;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DisplayDefinition extends HubApplicationTests {

    @Then("^I see (\\d+) elements of (\\S+)$")
    public void I_see_elements_of(Integer size, String element) {
        List<WebElement> elements = driver.findElements(By.className(element));

        Assert.assertEquals(size.intValue(), elements.size());
    }

    @Then("^I see element (.*)$")
    public void I_see_element(String element) {
        Boolean isDisplayed = driver.findElement(By.id(element)).isDisplayed();

        Assert.assertEquals(true, isDisplayed);
    }

    @Then("^I see elements (.*)$")
    public void I_see_elements(String element) {
        List<WebElement> elements = driver.findElements(By.className(element));

        Assert.assertEquals(true, elements.size() > 0);
    }

    @Then("^I can not see elements (.*)$")
    public void I_can_not_see_elements(String element) {
        List<WebElement> elements = driver.findElements(By.className(element));

        Assert.assertEquals(true, elements.size() == 0);
    }

    @Then("^I can not see element (.*)$")
    public void I_can_not_see_the_element(String id) {
        Boolean found;
        try {
            driver.findElement(By.id(id));
            found = true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            found = false;
        }

        Assert.assertEquals(false, found);
    }

    @Then("^I see the text (.+)$")
    public void I_see_the_text(String text) {
        Assert.assertEquals(true, driver.getPageSource().contains(text));
    }
}
