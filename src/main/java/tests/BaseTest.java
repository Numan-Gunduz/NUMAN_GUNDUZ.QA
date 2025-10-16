package tests;

import core.config.Config;
import core.config.ConfigLoader;
import core.driver.DriverFactory;
import core.listeners.TestListener;
import io.qameta.allure.testng.AllureTestNg;   // <-- EK

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

@org.testng.annotations.Listeners({ TestListener.class, AllureTestNg.class })   // <-- EK
public abstract class BaseTest {

    protected WebDriver driver;
    protected Config cfg;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        cfg = ConfigLoader.get();
        DriverFactory.start();
        driver = DriverFactory.get();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.stop();
    }
}
