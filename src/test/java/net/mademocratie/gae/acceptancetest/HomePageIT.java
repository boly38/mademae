package net.mademocratie.gae.acceptancetest;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;

@Ignore
public class HomePageIT {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @Test
    public void home_page_should_be_displayed() {
        this.driver.get("http://localhost:9000");
        assertEquals("Ma Democratie", this.driver.getTitle());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
