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
    protected Logger log; // <-- EK


    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        log = LoggerFactory.getLogger(this.getClass()); // <-- EK

        cfg = ConfigLoader.get();
        DriverFactory.start();
        driver = DriverFactory.get();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.stop();
    }
}
