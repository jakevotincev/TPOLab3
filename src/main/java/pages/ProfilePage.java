package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage extends Page{
    public ProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//h4[@class='title']")
    private WebElement welcomeTitle;

    @FindBy(xpath = "//input[contains(@placeholder, 'Find')]")
    private WebElement searchBar;
    @FindBy(xpath = "//input[contains(@placeholder, 'Find')]/following-sibling::button")
    private WebElement searchButton;
    @FindBy(xpath = "//a[text()='Lists']")
    private WebElement listsButton;

    public String getWelcomeTitle(){
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class='title']")));
        return welcomeTitle.getText();
    }

    public SearchResultPage search(String str){
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder, 'Find')]")));
        searchBar.sendKeys(str);
        searchButton.submit();
        return new SearchResultPage(driver, str);
    }

    public ListsPage clickListsBtn(){
        listsButton.click();
        return new ListsPage(driver);
    }
}
