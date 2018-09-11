package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SendedMessagesPage extends BasePage{
    public SendedMessagesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    //div[@role="menuitem"]/img/.. - стрелочка меню
    //span[contains(text(), "Кому:")]/../../td[2] - кому ( взять текст)
    //span[contains(text(), "тема:")]/../../td[2] - тема (взять текст)
    //div[@role="listitem"]//div[@style="display:"]/div[2]/div[3]/div[3]/div/div[1] - сообщение (взять текст)
    @FindBy(xpath = "//div[@role=\"menuitem\"]/img/..")
    WebElement popupMenu;

    @FindBy(xpath = "//span[contains(text(), \"Кому:\")]/../../td[2]")
    WebElement sendedToWhom;

    @FindBy(xpath = "//span[contains(text(), \"тема:\")]/../../td[2]")
    WebElement subjectToWhom;

    @FindBy(xpath = "//div[@role=\"listitem\"]//div[@style=\"display:\"]/div[2]/div[3]/div[3]/div/div[1]")
    WebElement messageToWhom;


    public void pushMenu()
    {
        popupMenu.click();
    }

    public String getToWhomFromSended() {
        return sendedToWhom.getText();
    }

    public String getSubjectFromSended() {
        return subjectToWhom.getText();
    }

    public String getMessageFromSended() {
        return messageToWhom.getText();
    }



}
