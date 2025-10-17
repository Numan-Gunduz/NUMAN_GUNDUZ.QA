package pages;// LeverJobPage.java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeverJobPage extends BasePage {
    private static final Logger LOG = LoggerFactory.getLogger(LeverJobPage.class);
    private static final By APPLY_BTN = By.xpath(
            "//a[normalize-space()='APPLY FOR THIS JOB' or normalize-space()='Apply for this job' or contains(@href,'apply')]"
    );

    public LeverJobPage(WebDriver driver) { super(driver); }

    public boolean isOpen() {
        boolean urlOk = waitFor().until(d ->
                d.getCurrentUrl().contains("jobs.lever.co/useinsider")
                        || d.getCurrentUrl().contains("lever.co/useinsider"));
        boolean btnOk = waitFor().until(ExpectedConditions.visibilityOfElementLocated(APPLY_BTN)).isDisplayed();

        if (urlOk && btnOk)
            LOG.info("✅ Lever Application sayfası başarıyla açıldı: {}", driver.getCurrentUrl());
        else
            LOG.warn("⚠️ Lever sayfası doğrulanamadı. URL: {}", driver.getCurrentUrl());

        return urlOk && btnOk;
    }


}
