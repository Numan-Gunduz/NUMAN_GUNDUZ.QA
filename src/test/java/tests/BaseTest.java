package tests;

import core.config.Config;
import core.config.ConfigLoader;
import core.driver.DriverFactory;
import core.listeners.TestListener;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({ TestListener.class, AllureTestNg.class })   // <-- EK
public abstract class BaseTest {

    protected WebDriver driver;
    protected Config cfg;



    @BeforeMethod(alwaysRun = true)
    public void setup() {
        cfg = ConfigLoader.get();
        DriverFactory.start();
        driver = DriverFactory.get();
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.stop();
    }
}
