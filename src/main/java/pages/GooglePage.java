package pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GooglePage extends BasePage{

    public GooglePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(text(), \"Написать\")]")
    WebElement writeButton;

    @FindBy(xpath = "//div/h2/div[contains(text(), \"Написать:\")]/../..")
    WebElement activateMessageWindow;

   // @FindBy(xpath = "//a[contains(@href, \"Logout\")]")
    @FindBy(xpath = "//a[contains(text(), \"Выйти\")]/..")
    WebElement logoutButton;

//    @FindBy(xpath = "//img[contains(@src, \"logo_gmail\")]")
//    WebElement gmailLogo;

    @FindBy(xpath = "//input[@placeholder=\"Поиск в почте\"]")
    WebElement lookUpSearchInMail;

  //  @FindBy(xpath = "//span/a[contains(@title, \"Черновики\")]")
  //  WebElement draftMessage;

//    @FindBy(xpath = "//div[@class=\"aim\"]//a[contains(text(), \"Черновики\")]")
//    WebElement draftMessage;

    @FindBy(xpath = "//img[@aria-label=\"Свернуть\"]")
    WebElement messageWindow;

    @FindBy(xpath = "//a[@title=\"Отправленные\"]/../../../../..")
    WebElement sendedMessages;

   // @FindBy(xpath = "//div[@role=\"tabpanel\"]//table/tbody/tr[1]")
  //  WebElement firstSendedMessage;

    public GooglePage writeNewMessageAndActivateWindow()
    {
        writeButton.click();
 //       activateMessageWindow.click();
        return this;
    }



    public GooglePage logoutMeth()
    {
        driver.get(driver.findElement(By.xpath("//a[contains(text(), \"Выйти\")]")).getAttribute("href"));
//        logoutButton.click();
        return this;
    }

    public boolean isItGoogleMail()
    {
        return lookUpSearchInMail.isDisplayed();
    }

    public GooglePage clickDraftMessagePageIfExists()
    {
        try{
            try {
                driver.findElement(By.xpath("//div[@class=\"aim\"]//a[contains(text(), \"Черновики\")]")).click();
     //           driver.findElement(By.xpath("//a[contains(text(), \"Черновики\")]/../..")).click();
      //          driver.findElement(By.xpath("//a[contains(text(), \"Черновики\")]")).click();
     //           driver.findElement(By.xpath("//a[contains(text(), \"Черновики\")]/..")).click();
        //        driver.findElement(By.xpath("//a[contains(text(), \"Черновики\")]/../../..")).click();
     //           driver.findElement(By.xpath("//a[contains(text(), \"Черновики\")]/../../../..")).click();
            }catch(StaleElementReferenceException ex)
            {
                fileLogger.debug("clickDraftMessagePageIfExists " + ex.getStackTrace());
                driver.findElement(By.xpath("//div[@class=\"aim\"]//a[contains(text(), \"Черновики\")]")).click();
    //            driver.findElement(By.xpath("//a[contains(text(), \"Черновики\")]/../..")).click();
    //            driver.findElement(By.xpath("//a[contains(text(), \"Черновики\")]")).click();
//                driver.findElement(By.xpath("//a[contains(text(), \"Черновики\")]/..")).click();
         //       driver.findElement(By.xpath("//a[contains(text(), \"Черновики\")]/../../..")).click();
      //          driver.findElement(By.xpath("//a[contains(text(), \"Черновики\")]/../../../..")).click();
            }
        }catch (NoSuchElementException e)
        {
            fileLogger.debug("clickDraftMessagePageIfExists " + e.getStackTrace());
            Assert.fail("Черновиков нет");
        }
        return this;
    }

    public GooglePage clickDraftMessage(int index)
    {
      //  driver.
        boolean flag = true;
        while (flag) {
            try{
                if(driver.findElement(By.xpath("//span[contains(text(), \"Черновик\")]")).isEnabled())
                    flag = false;
            }catch (StaleElementReferenceException ex)
            {
                fileLogger.debug("clickDraftMessage " + ex.getStackTrace());
            }
        }


        //String xPath = "//h2[contains(text(), \"Цепочки писем\")]/../..//table/tbody/tr[" + index + "]";
        String xPath = "//span[contains(text(), \"Черновик\")]/../../../..";
        try{
            driver.findElement(By.xpath(xPath)).click();
        }catch (StaleElementReferenceException ex)
        {
            fileLogger.debug("clickDraftMessagePageIfExists " + ex.getStackTrace());
            driver.findElement(By.xpath(xPath)).click();
        }
        return this;
    }

    public GooglePage isMessageWindowOpened() {
        try{
            messageWindow.isEnabled();
        }catch (NoSuchElementException e)
        {
            fileLogger.debug("isMessageWindowOpened " + e.getStackTrace());
            Assert.fail("Окно сообщения не было открыто");
        }
        return this;
    }

    public GooglePage goToSendedMessages() {
        sendedMessages.click();
        return this;
    }

    public GooglePage openFirstSendedMessage(){
       // WebElement firstSendedMessage = driver.findElement(By.xpath("//div[@role=\"main\"]//table//tr[1]"));
       // firstSendedMessage.click();
        try {
            driver.findElement(By.xpath("//tbody/tr[1]//div[contains(text(), \"Кому:\")]")).click();

        }catch(Exception ex) {
            driver.findElement(By.xpath("//div[@role=\"main\"]//table//tr[1]")).click();
        }
//        boolean flag = true;
//        while(flag)
//        {try {
            driver.findElement(By.xpath("//span[contains(text(),\"кому\")]")).isEnabled();
//            flag = false;
//        }catch(NoSuchElementException ex) {fileLogger.debug("openFirstSendedMessage: " + ex.getMessage());}
//        }

        return this;
    }
}
