package core.utlis;
import core.config.ConfigLoader;
import core.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class Waits {
    private Waits(){}

    public static WebDriverWait waitFor() {
        WebDriver driver = DriverFactory.get();
        int sec = ConfigLoader.get().explicitWait();
        return new WebDriverWait(driver, Duration.ofSeconds(sec));
    }
}
