package pages;
import core.utlis.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor js;
    protected final Actions actions;


    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js     = (JavascriptExecutor) driver;
        this.actions= new Actions(driver);

    }
    protected WebElement waitClickable(By by){ return wait.until(ExpectedConditions.elementToBeClickable(by)); }
    protected WebElement waitVisible(By by){ return wait.until(ExpectedConditions.visibilityOfElementLocated(by)); }
    protected void scrollIntoView(org.openqa.selenium.WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    protected void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    protected org.openqa.selenium.support.ui.WebDriverWait waitFor() {
        return Waits.waitFor();
    }


    protected void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
    }

    protected void safeClick(By by) {
        WebElement el = waitFor().until(ExpectedConditions.elementToBeClickable(by));
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    protected void jsClick(WebElement el){ js.executeScript("arguments[0].click();", el); }
    protected void scrollIntoViewCenter(WebElement el) {
        js.executeScript("arguments[0].scrollIntoView({block:'center',inline:'center'});", el);
    }

}
