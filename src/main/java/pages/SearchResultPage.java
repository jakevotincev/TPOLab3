package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchResultPage extends Page {
    public SearchResultPage(WebDriver driver, String searchStr) {
        super(driver);
        this.searchString = searchStr;
        this.driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//label[text()='Newest Arrivals']")
    private WebElement sortLabel;

    private final String searchString;

    public boolean isResultDisplayed() {
        return driver.findElement(By.xpath("//a[contains(text(), '" + searchString + "')]")).isDisplayed();
    }

    public boolean isErrorPageDisplayed() {
        return driver.findElement(By.xpath("//div[contains(@class, 'error')]")).isDisplayed();
    }

    public List<WebElement> getFoundElements() {
        return driver.findElements(By.xpath("//h3//a[contains(text(), '" + searchString + "')]"));
    }

    public List<WebElement> getSellersInfoOfElements() {
        return driver.findElements(By.xpath("//div[contains(@class, 'seller-info')]"));
    }

    public List<WebElement> getProductPriceElements() {
        return driver.findElements(By.xpath("//a[@class='price']//span"));
    }

    public void changeCategory(String categoryName) {
        WebDriverWait wait = new WebDriverWait(driver, 100);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Category']"))).click();
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Category']"))).click();
        }

        WebElement selectHref = null;

        try {
            selectHref = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='item']//a[text()='" + categoryName + "']")));
            selectHref.click();
        } catch (Exception e) {
            selectHref = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='item']//a[text()='" + categoryName + "']")));
            selectHref.click();
        }
    }

    public void selectOptions(String option, String optionName) {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        WebElement optionButton = null;

        try {
            optionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='" + option + "']")));
            optionButton.click();
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='" + option + "']"))).click();
        }

        WebElement selectHref = null;

        try {
            selectHref = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + optionName + "']")));
            selectHref.click();
        } catch (Exception e) {
            selectHref = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + optionName + "']")));
            selectHref.click();
        }


        driver.findElement(By.xpath("//a[text()='Apply']")).click();
    }

    public void filterByBudget(int from, int to) {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        WebElement optionButton = null;

        try {
            optionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Budget']")));
            optionButton.click();
        } catch (Exception e) {
            optionButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Budget']")));
            optionButton.click();
        }
        WebElement input = null;

        try {
            input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='min']")));
            input.sendKeys(String.valueOf(from));
            input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='max']")));
            input.sendKeys(String.valueOf(to));
        } catch (Exception e) {
            input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='min']")));
            input.sendKeys(String.valueOf(from));
            input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='max']")));
            input.sendKeys(String.valueOf(to));
        }

        driver.findElement(By.xpath("//a[text()='Apply']")).click();
    }

    public void filterByDeliveryTime(String time) {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        WebElement optionButton = null;

        try {
            optionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Delivery Time']")));
            optionButton.click();
        } catch (Exception e) {
            optionButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Delivery Time']")));
            optionButton.click();
        }

        WebElement selectHref = null;

        try {
            selectHref = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[contains(text(), '" + time + "')]")));
            selectHref.click();
        } catch (Exception e) {
            selectHref = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[contains(text(), '" + time + "')]")));
            selectHref.click();
        }

        driver.findElement(By.xpath("//a[text()='Apply']")).click();
    }

    public void clickSortLabel() {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Relevance']")));
        driver.findElement(By.xpath("//div[text()='Relevance']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Newest Arrivals']")));
        sortLabel.click();
    }

    public void addToList() {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='icn-heart']"))).click();

    }
}
