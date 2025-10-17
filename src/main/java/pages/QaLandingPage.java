// src/main/java/pages/QaLandingPage.java
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QaLandingPage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(QaLandingPage.class);

    private static final String URL = "https://useinsider.com/careers/quality-assurance/";
    private static final By SEE_ALL_QA_JOBS_BTN = By.xpath("//a[contains(.,'See all QA jobs')]");

    public QaLandingPage(WebDriver driver) { super(driver); }

    public QaLandingPage open() {
        driver.get(URL);
        return this;
    }

    public boolean isOpen() {
        return waitFor().until(d -> d.getCurrentUrl().contains("/careers/quality-assurance"));
    }

    public QaJobsListPage clickSeeAllQaJobs() {
        log.info("QA Landing: 'See all QA jobs' butonuna tıklanıyor...");
        waitFor().until(ExpectedConditions.elementToBeClickable(SEE_ALL_QA_JOBS_BTN)).click();


        // Sayfa yönlendirmesi tamamlanmadan önce 15 saniye bekle
 sleep(15000);
        System.out.println("15 saniye dinamik olarak beklendir");

        waitFor().until(ExpectedConditions.urlContains("/open-positions"));
        log.info("'Open Positions' sayfası açıldı, filtreler uygulanmaya hazır.");
        return new QaJobsListPage(driver);
    }
}
