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
import pages.ProfilePage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class LoginTest {

    public static HomePage homePage;
    public static ProfilePage profilePage;
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
    void testValidLogin(){
        driverList.forEach(driver -> {
            homePage = new HomePage(driver);
            homePage.clickSignInBtn();
            homePage.inputLogin(ConfProperties.getProperty("login"));
            homePage.inputPassword(ConfProperties.getProperty("password"));
            profilePage = homePage.submitLogin();
            assertTrue(profilePage.getWelcomeTitle().contains("Evgenii"));
            driver.quit();
        });
    }

    @Test
    void testInvalidLogin(){
        driverList.forEach(driver -> {
            homePage = new HomePage(driver);
            homePage.clickSignInBtn();
            homePage.inputLogin("xxxxxxx");
            homePage.inputPassword("xxxxxxx");
            homePage.submitLogin();
            assertTrue(homePage.isErrorMsgDisplayed());
            driver.quit();
        });
    }
}
