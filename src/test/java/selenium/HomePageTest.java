package selenium;

import config.ConfProperties;
import exceptions.InvalidChromeException;
import exceptions.InvalidFirefoxException;
import exceptions.InvalidPropertiesException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.HomePage;
import pages.SearchResultPage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest {
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
    void testValidSearchService() {
        driverList.forEach(driver -> {
            homePage = new HomePage(driver);
            homePage.inputSearchBar(ConfProperties.getProperty("search.string"));
            SearchResultPage searchResultPage = homePage.search();
            assertTrue(searchResultPage.isResultDisplayed());
            driver.quit();
        });
    }

    @Test
    void testInvalidSearchService() {
        driverList.forEach(driver -> {
            homePage = new HomePage(driver);
            homePage.inputSearchBar("xxxxxxx");
            SearchResultPage searchResultPage = homePage.search();
            assertTrue(searchResultPage.isErrorPageDisplayed());
            driver.quit();
        });
    }

    @Test
    void testSlider() {
        driverList.forEach(driver -> {
            homePage = new HomePage(driver);
            List<WebElement> sliderElements = homePage.getSlidersElements().stream().limit(4).collect(Collectors.toList());
            WebElement nextButton = homePage.getSlidersNextButtons().stream().findFirst().orElse(null);
            if (nextButton == null) Assertions.fail();
            nextButton.click();
            List<WebElement> newSliderElements = homePage.getSlidersElements().stream().limit(4).collect(Collectors.toList());
            assertNotEquals(newSliderElements, sliderElements);
            driver.quit();
        });
    }
}
