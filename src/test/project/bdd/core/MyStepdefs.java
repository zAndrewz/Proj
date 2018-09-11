package project.bdd.core;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import pages.GooglePage;
import pages.LoginPage;
import pages.MessageWindowPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.SendedMessagesPage;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyStepdefs {

    private WebDriver driver;
    private Logger logger = LogManager.getLogger("Console");
    private LoginPage loginPage;
    private GooglePage googlePage;
    private MessageWindowPage mwPage;
    private SendedMessagesPage smPage;

    @Before
    public void init()
    {
        logger.info("Starting of test application\n");
        System.setProperty("webdriver.chrome.driver", "D:\\IntelliJProj\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().fullscreen();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        driver.get("https://www.google.com/gmail/");
    }

    @After
    public void shutdown()
    {
        driver.quit();
    }

    @Дано("^пользователь авторизуется на почте как \"([^\"]*)\" \"([^\"]*)\"$")
    public void auth(String arg0, String arg1) throws Throwable {
        logger.info("Заходим на почту с логином/паролем " + arg0 + "/" + arg1 + "\n");
        loginPage = new LoginPage(driver);
        loginPage.login(arg0, arg1);
        logger.info("Вход выполнен\n");
    }

    @Когда("^загружается страница Mail Google")
    public void googleMainDownloaded() throws Throwable {
        logger.info("Подтверждаем, что это Mail Google\n");
        googlePage = new GooglePage(driver);
        if(!googlePage.isItGoogleMail())
        Assert.fail("This is incorrect website");

        logger.info("Подтверждено.\n");
    }

    @Тогда("^нажимаем кнопку Написать")
    public void pushTheButton() throws Throwable {
        logger.info("Нажимаем кнопку Написать\n");
        googlePage.writeNewMessageAndActivateWindow();
    }

    @Если("^появляется окно Новое сообщение")
    public void isWindowOpened() throws Throwable {
        logger.info("Проверяем, появилось ли окно\n");
        googlePage.isMessageWindowOpened();
    }


    @Тогда("^заполняем поля Адресат Тема Текст$")
    public void fillFields(DataTable dt) throws Throwable {
        Map<String, String> map = dt.asMap(String.class, String.class);

        logger.info("Заполняем поля данными: \n" +
                    "Адресат: " + map.get("Адресат") + "\n" +
                    "Тема" + map.get("Тема") + "\n" +
                    "Текст" + map.get("Текст") + "\n");

        mwPage = new MessageWindowPage(driver);
        mwPage.setToWhom(map.get("Адресат"))
                .setTheme(map.get("Тема"))
                .setTextOfMessage(map.get("Текст"));
    }

    @Когда("^закрыто окно Новое сообщение")
    public void closeWindow() throws Throwable {
        mwPage.closeMsgWindow();
        logger.info("Окно сообщения закрыто\n");
    }

    @Тогда("^проверяем Черновики")
    public void checkDraft() throws Throwable {
        logger.info("Проверяем черновики\n");
        googlePage.clickDraftMessagePageIfExists();
    }

    @Если("^Черновик открывается$")
    public void draftMessagesIsOpened() throws Throwable {
        googlePage.clickDraftMessage(1);
      //  googlePage.isMessageWindowOpened();
    }

    @Тогда("^проверяем данные Адресат Тема Текст$")
    public void checkData(DataTable dt) throws Throwable {
        Map<String, String> map = dt.asMap(String.class, String.class);
        logger.info("Проверяем данные: " + "\n" +
                        "Адресат: " + map.get("Адресат") + "\n" +
                        "Тема" + map.get("Тема") + "\n" +
                        "Текст" + map.get("Текст")+"\n");
        if(!map.get("Адресат").equals(mwPage.getToWhom())
                || !map.get("Тема").equals(mwPage.getTheme())
                || !map.get("Текст").equals(mwPage.getTextOfMessage()))
            Assert.fail("Адресат, Тема или Текст не совпадают!");
    }

    @Тогда("^нажимаем кнопку Отправить$")
    public void sendMessage() throws Throwable {
        logger.info("Нажимаем кнопку Отправить\n");
        mwPage.sendMessageButton();
    }


    @Когда("^письмо удаляется из Черновики$")
    public void isMessageWereDeletedFromDraft() throws Throwable {
        try {
            logger.info("Проверяем, что письмо удалилось из черновиков\n");
            googlePage.clickDraftMessage(1);
            mwPage.getToWhom();
            logger.info("Удалено\n");
        }catch (NoSuchElementException ex)
        {

        }
    }

    @Тогда("^письмо добавляется в список Отправленные$")
    public void messageMovedToSended(DataTable dt) throws Throwable {
        logger.info("Проверяем, что письмо добавилось в отправленные\n");
        smPage = new SendedMessagesPage(driver);
        googlePage.goToSendedMessages();
        googlePage.openFirstSendedMessage();
        Map<String, String> map = dt.asMap(String.class, String.class);
        logger.info("Проверяем данные: " + "\n" +
                "Адресат: " + map.get("Адресат") + "\n" +
                "Тема" + map.get("Тема") + "\n" +
                "Текст" + map.get("Текст")+"\n");
        smPage.pushMenu();
        if(!map.get("Адресат").equals(smPage.getToWhomFromSended())
                || !map.get("Тема").equals(smPage.getSubjectFromSended())
                || !map.get("Текст").equals(smPage.getMessageFromSended()))
        Assert.fail();
    }


    @Когда("^нажимаем Выход$")
    public void exit() throws Throwable {
        logger.info("Нажимаем кнопку Выход\n");
        googlePage.logoutMeth();
    }


    @Тогда("^пользователь выходит на страницу авторизации$")
    public void openedAuthPage() throws Throwable {
        logger.info("Проверяем, что выход осуществлен\n");
        loginPage.ifPresentFP();
    }
}
