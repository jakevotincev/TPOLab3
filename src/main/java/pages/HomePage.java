package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePage extends Page {

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        this.driver.get("https://www.fiverr.com/");
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//input[contains(@placeholder, 'Try')]")
    private WebElement searchBar;
    @FindBy(xpath = "//input[contains(@placeholder, 'Try')]/following-sibling::button")
    private WebElement searchButton;

    @FindBy(xpath = "//*[contains(@class, 'js-open-popup-login nav-link')]")
    private WebElement signInButton;
    @FindBy(xpath = "//*[contains(@id, 'login')]")
    private WebElement loginInput;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement continueButton;

    @FindBy(xpath = "//a[text()='Join']")
    private WebElement joinButton;
    @FindBy(xpath = "//input[@id='email']")
    private WebElement inputEmail;
    @FindBy(xpath = "//input[@id='username']")
    private WebElement usernameInput;
    private String searchString = new String("");

    public void clickSignInBtn() {
        signInButton.click();
    }

    public void inputLogin(String login) {
        loginInput.sendKeys(login);
    }

    public void inputPassword(String password) {
        driver.findElement(By.xpath("//*[contains(@id, 'password')]")).sendKeys(password);
    }

    public ProfilePage submitLogin() {
        continueButton.submit();
        return new ProfilePage(driver);
    }

    public void clickSubmitButton(){
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
    }

    public void clickJoinBtn(){
        joinButton.click();
    }

    public void inputEmail(String email){
        inputEmail.sendKeys(email);
    }

    public boolean isErrorMsgDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, 50);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class, 'error')]"))).isDisplayed();
    }

    public void inputSearchBar(String str){
        searchBar.sendKeys(str);
        searchString = str;
    }

    public SearchResultPage search(){
        searchButton.submit();
        return new SearchResultPage(driver, searchString);

    }

    public List<WebElement> getSlidersElements(){
        return driver.findElements(By.xpath("//div[contains(@class, 'slick-slide slick-active')]"));
    }

    public List<WebElement> getSlidersNextButtons(){
        return driver.findElements(By.xpath("//button[contains(@class, 'slick-arrow slick-next')]"));
    }
}
