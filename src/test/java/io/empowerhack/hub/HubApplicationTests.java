package io.empowerhack.hub;

import com.github.javafaker.Faker;
import io.empowerhack.hub.domain.User;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HubApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles("test")
@Ignore
public abstract class HubApplicationTests {

    @Value("${local.server.port}")
    protected int port;

    protected String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Autowired
    protected WebDriver driver;

    protected Faker faker = new Faker();

    protected String withBaseUrl(String path) {
        return getBaseUrl() + path;
    }

    protected User getUser() {
        return new User("TestUser");
    }
}
