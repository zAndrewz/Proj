package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

abstract class BasePage {
    WebDriver driver;
    Logger fileLogger = LogManager.getLogger("File");

    BasePage(WebDriver driver) {
        this.driver = driver;
    }
}
