package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ListsPage extends Page {
    public ListsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public int getServicesCount() {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        String spanText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class, 'stats')]"))).getText();
        return Integer.parseInt(spanText.substring(spanText.indexOf('(') + 1, spanText.indexOf(')')));
    }

    public void deleteList() {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'action-icon')]")));
        driver.findElement(By.xpath("//button[contains(@class, 'action-icon')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Delete']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Delete')]"))).click();
    }
}
