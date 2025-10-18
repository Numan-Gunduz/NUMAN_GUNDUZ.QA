
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final String URL = "https://useinsider.com/";
    private static final By HEADER = By.tagName("header");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public HomePage open() {
        driver.get(URL);
        return this;
    }

    public boolean isOpen() {
        try {
            wait.until(ExpectedConditions.urlContains("useinsider.com"));
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.titleContains("Insider"),
                    ExpectedConditions.visibilityOfElementLocated(HEADER)
            ));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
