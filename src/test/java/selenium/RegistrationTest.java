package selenium;

import config.ConfProperties;
import exceptions.InvalidChromeException;
import exceptions.InvalidFirefoxException;
import exceptions.InvalidPropertiesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.HomePage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest {
    public static HomePage homePage;
    public List<WebDriver> driverList;

    @BeforeEach
    void setUp() throws InvalidChromeException {
        driverList = new ArrayList<>();

        if (Boolean.parseBoolean(ConfProperties.getProperty("run.chrome")))
            if (ConfProperties.getProperty("webdriver.chrome.driver") != null && ConfProperties.getProperty("chromedriver") != null) {
                System.setProperty(ConfProperties.getProperty("webdriver.chrome.driver"), ConfProperties.getProperty("chromedriver"));
                driverList.add(new ChromeDriver());
            } else throw new InvalidChromeException();

        if (Boolean.parseBoolean(ConfProperties.getProperty("run.firefox")))
            if (ConfProperties.getProperty("webdriver.firefox.driver") != null && ConfProperties.getProperty("firefoxdriver") != null) {
                System.setProperty(ConfProperties.getProperty("webdriver.firefox.driver"), ConfProperties.getProperty("firefoxdriver"));
                driverList.add(new FirefoxDriver());
            } else throw new InvalidFirefoxException();

        if (driverList.isEmpty()) throw new InvalidPropertiesException();
    }

    @Test
    void testInvalidRegistration(){
        driverList.forEach(driver -> {
            homePage = new HomePage(driver);
            homePage.clickJoinBtn();
            homePage.inputEmail(ConfProperties.getProperty("login"));
            homePage.clickSubmitButton();
            assertTrue(homePage.isErrorMsgDisplayed());
            driver.quit();
        });

    }

}
