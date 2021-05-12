package selenium;

import config.ConfProperties;
import exceptions.InvalidChromeException;
import exceptions.InvalidFirefoxException;
import exceptions.InvalidPropertiesException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.HomePage;
import pages.ListsPage;
import pages.ProfilePage;
import pages.SearchResultPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


public class SearchResultPageTest {
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
    void testSortServices() {
        driverList.forEach(driver -> {
            SearchResultPage searchResultPage = search(driver);
            List<WebElement> foundList = searchResultPage.getFoundElements();
            searchResultPage.clickSortLabel();
            List<WebElement> sortedList = searchResultPage.getFoundElements();
            assertNotEquals(foundList, sortedList);
            driver.quit();
        });
    }

    @Test
    void testCategoryFilter() {
        driverList.forEach(driver -> {
            SearchResultPage searchResultPage = search(driver);
            List<WebElement> foundList = searchResultPage.getFoundElements();
            searchResultPage.changeCategory("Logo Design");
            List<WebElement> sortedList = searchResultPage.getFoundElements();
            assertNotEquals(foundList, sortedList);
            sortedList.stream().limit(3).forEach(item -> {
                System.out.println(item.getText());
                assertTrue(item.getText().toLowerCase().contains("logo"));
            });
            driver.quit();
        });
    }

    @Test
    void testServiceOptionsFilter() {
        driverList.forEach(driver -> {
            SearchResultPage searchResultPage = search(driver);
            List<WebElement> foundList = searchResultPage.getFoundElements();
            searchResultPage.selectOptions("Service Options", "T-shirts");
            List<WebElement> sortedList = searchResultPage.getFoundElements();
            assertNotEquals(foundList, sortedList);
            assertTrue(sortedList.stream().anyMatch(item -> item.getText().contains("tshirt")));
            driver.quit();
        });
    }

    @Test
    void testSellerDetailsFilter() {
        driverList.forEach(driver -> {
            SearchResultPage searchResultPage = search(driver);
            List<WebElement> foundList = searchResultPage.getSellersInfoOfElements();
            searchResultPage.selectOptions("Seller Details", "Top Rated Seller");
            List<WebElement> sortedList = searchResultPage.getSellersInfoOfElements();
            assertNotEquals(foundList, sortedList);
            sortedList.stream().limit(2).forEach(webElement -> assertTrue(webElement.getText().contains("Top Rated Seller")));
            driver.quit();
        });
    }

    @Test
    void testBudgetFilter() {
        driverList.forEach(driver -> {
            SearchResultPage searchResultPage = search(driver);
            List<WebElement> foundList = searchResultPage.getProductPriceElements();
            searchResultPage.filterByBudget(25, 100);
            List<WebElement> sortedList = searchResultPage.getProductPriceElements();
            assertNotEquals(foundList, sortedList);
            sortedList.stream().limit(5).forEach(webElement -> {
                int price = Integer.parseInt(webElement.getText().replace("$", ""));
                assertTrue(price >= 25 && price <= 100);
            });
            driver.quit();
        });
    }

    @Test
    void testDeliveryTimeFilter(){
        driverList.forEach(driver -> {
            SearchResultPage searchResultPage = search(driver);
            searchResultPage.filterByDeliveryTime("24H");
            WebElement foundElement = searchResultPage.getFoundElements().stream().findFirst().orElse(null);
            if(foundElement!=null) foundElement.click();
            else Assertions.fail();
            driver.switchTo().window(driver.getWindowHandles().stream().skip(1).findFirst().orElse(null));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            assertFalse(driver.findElements(By.xpath("//b[text()='1 Day Delivery']")).isEmpty());
            driver.quit();
        });
    }


    @Test
    void testSaveAndDeleteService() {
        driverList.forEach(driver -> {
            driver.manage().window().maximize();
            homePage = new HomePage(driver);
            homePage.clickSignInBtn();
            homePage.inputLogin(ConfProperties.getProperty("login"));
            homePage.inputPassword(ConfProperties.getProperty("password"));
            profilePage = homePage.submitLogin();
            SearchResultPage searchResultPage = profilePage.search(ConfProperties.getProperty("search.string"));
            searchResultPage.addToList();
            ListsPage listsPage = profilePage.clickListsBtn();
            assertEquals(1, listsPage.getServicesCount());
            listsPage.deleteList();
            driver.quit();
        });
    }

    private SearchResultPage search(WebDriver driver) {
        homePage = new HomePage(driver);
        homePage.inputSearchBar(ConfProperties.getProperty("search.string"));
        return homePage.search();
    }
}
