package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MessageWindowPage extends BasePage{
    public MessageWindowPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    /*
    //div[@role="dialog"]//form/div[2]
     */
    String stringToWhomGet = "//div[@role=\"dialog\"]//form//div[@tabindex=\"1\"]/div[1]/span";
    String stringToWhomSet = "//div[contains(text(),\"Новое сообщение\")]/ancestor::div[@role=\"dialog\"]//span[contains(text(),\"Кому\")]/../../..//textarea";

    //   @FindBy(xpath = "//div[contains(text(),\"Новое сообщение\")]/ancestor::div[@role=\"dialog\"]//span[contains(text(),\"Кому\")]/../../..//textarea")
    WebElement toWhom;

    @FindBy(xpath = "//*[contains(text(),\"Новое сообщение\")]/ancestor::div[@role=\"dialog\"]//input[@placeholder=\"Тема\"]")
    WebElement themeOfMess;

  //  @FindBy(xpath = "//*[contains(text(),\"Новое сообщение\")]/ancestor::div[@role=\"dialog\"]//textarea[@aria-label=\"Тело письма\"]")
    @FindBy(xpath = "//div[@role=\"textbox\"]")
    WebElement textOfMessage;

    @FindBy(xpath = "//img[contains(@alt, \"Закрыть\")]")
    WebElement closeMessageWindow;

    @FindBy(xpath = "//div[contains(@data-tooltip,\"Отправить\")]")
    WebElement sendMessage;

    public MessageWindowPage setToWhom(String recipient)
    {
        toWhom = driver.findElement(By.xpath(stringToWhomSet));
        toWhom.sendKeys(recipient);
        return this;
    }

    public String getToWhom()
    {
       // String value = driver.findElement(By.xpath("//div[@role=\"dialog\"]//form/input[3]/@value")).toString();
        String value;
        try {
            value = driver.findElement(By.xpath(stringToWhomGet)).getText();
        }catch(StaleElementReferenceException ex)
        {
            fileLogger.debug("getToWhom " + ex.getStackTrace());
            value = driver.findElement(By.xpath(stringToWhomGet)).getText();
        }
        return value;
    }

    public MessageWindowPage setTheme(String theme)
    {
        themeOfMess.sendKeys(theme);
        return this;
    }

    public String getTheme()
    {
   //     themeOfMess.getText();
        String str = driver.findElement(By.xpath("//div[@role=\"dialog\"]//form/input[3]")).getAttribute("value");
        return str;
    }

    public MessageWindowPage setTextOfMessage(String text)
    {
        textOfMessage.sendKeys(text);
        return this;
    }

    public String getTextOfMessage()
    {
        return textOfMessage.getText();
    }

    public MessageWindowPage closeMsgWindow()
    {
        closeMessageWindow.click();
        return this;
    }

    public MessageWindowPage sendMessageButton() {
        sendMessage.click();
        return this;
    }
}
