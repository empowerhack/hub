package io.empowerhack.hub.definitions.helpers;

import cucumber.api.java.en.Then;
import io.empowerhack.hub.HubApplicationTests;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DisplayDefinition extends HubApplicationTests {

    @Then("^I see (\\\\d+) elements of (.*)$")
    public void I_see_elements_of(Integer size, String element) {
        List<WebElement> elements = driver.findElements(By.className(element));

        Assert.assertEquals(elements.size(), size.intValue());
    }

    @Then("^I see element (.*)$")
    public void I_see_element(String element) {
        Boolean isDisplayed = driver.findElement(By.id(element)).isDisplayed();

        Assert.assertEquals(true, isDisplayed);
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
