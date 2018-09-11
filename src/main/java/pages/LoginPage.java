package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage{
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // String passwordLineXPath = "//input[@type=\"password\"]";
    String passwordLineXPath = "//*[@id=\"password\"]/div[1]/div/div[1]/input";

    @FindBy(xpath = "//input[@id=\"identifierId\"]")
    private WebElement loginLine;

    @FindBy(xpath = "//div[@id=\"identifierNext\"]/content/span")
    private WebElement loginNext;

//    @FindBy(xpath = "//input[@type=\"password\"]")
//    private WebElement passwordLine;

  // @FindBy(xpath = "//div[@id=\"passwordNext\"]/content/span")
    @FindBy(xpath = "//div[@id=\"passwordNext\"]")
    private WebElement passNext;

    @FindBy(xpath = "//span[contains(text(), \"Забыли пароль?\")]")
    private WebElement forgotPassword;


    public LoginPage login(String log, String password)
    {

            loginLine.sendKeys(log);
            loginNext.click();
            WebElement pass = driver.findElement(By.xpath(passwordLineXPath));
//            pass.click();

            pass.sendKeys(password);
            passNext.click();
        return this;
    }

    public LoginPage ifPresentFP()
    {
        if (!forgotPassword.isDisplayed()) {
            fileLogger.debug("ifPresentFP: Пользователь не вышел" );
            Assert.fail("Пользователь не вышел");
        }
        return this;
    }
}
