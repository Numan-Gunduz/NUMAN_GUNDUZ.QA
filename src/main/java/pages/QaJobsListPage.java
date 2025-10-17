
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static core.utlis.Waits.waitFor;

public class QaJobsListPage extends BasePage {

    // --- Locators ---
    private static final By LOCATION_BOX = By.id("select2-filter-by-location-container");
    private static final By DEPT_BOX     = By.id("select2-filter-by-department-container");
    private static final By FIRST_VIEW_ROLE =
            By.xpath("(//*[@id='jobs-list']//a[contains(@href,'lever.co') or contains(normalize-space(.),'View Role')])[1]");
    private static final By FIRST_JOB_CARD = By.cssSelector("#jobs-list .position-list-item");
    private static final By VIEW_ROLE_IN_CARD =
            By.xpath(".//a[contains(@href,'lever.co') or contains(normalize-space(.),'View Role')]");

    private static final By JOB_CARDS = By.cssSelector(
            "[class*='position'], .position-list > *, .job-item, a[href*='positions']"
    );

    public QaJobsListPage(WebDriver driver) { super(driver); }

    // --- Helpers ---
    private void chooseFromOpenSelect2ByText(String exactText) {
        By option = By.xpath("//span[contains(@class,'select2-container--open')]//li[normalize-space(.)='" + exactText + "']");
        WebElement li = waitFor().until(ExpectedConditions.elementToBeClickable(option));
        scrollIntoViewCenter(li);
        li.click();
    }

    private void waitBoxText(By box, String... anyExpected) {
        waitFor().until(d -> {
            String cur = d.findElement(box).getText()
                    .replace('\u00A0', ' ')
                    .trim().toLowerCase();
            for (String exp : anyExpected) {
                String e = exp.replace('\u00A0',' ').trim().toLowerCase();
                if (cur.contains(e)) return true;
            }
            return false;
        });
    }

    // --- Actions ---
    public void filterLocationIstanbulTurkiye() {
        waitFor().until(ExpectedConditions.elementToBeClickable(LOCATION_BOX)).click();
        chooseFromOpenSelect2ByText("Istanbul, Turkiye");
        waitBoxText(LOCATION_BOX,
                "Istanbul, Turkiye", "İstanbul, Turkiye",
                "Istanbul, Turkey",  "İstanbul, Türkiye");
    }

    public void filterDepartmentQA() {
        waitFor().until(ExpectedConditions.elementToBeClickable(DEPT_BOX)).click();
        chooseFromOpenSelect2ByText("Quality Assurance");
        waitBoxText(DEPT_BOX, "Quality Assurance");
    }

    // QaJobsListPage.java
    public LeverJobPage clickFirstViewRole() {
        // Liste yüklensin
        waitFor().until(ExpectedConditions.presenceOfElementLocated(By.id("jobs-list")));

        By cardBy = FIRST_JOB_CARD;
        By btnInCardBy = By.xpath(".//a[contains(@href,'lever.co') or " +
                "normalize-space(.)='View Role' or .//span[normalize-space(.)='View Role']]");

        WebElement card;
        WebElement btn;

        // 1) Kartı bul + hover (stale olursa yeniden bul)
        for (int i = 0; i < 3; i++) {
            try {
                card = waitFor().until(ExpectedConditions.visibilityOfElementLocated(cardBy));
                scrollIntoViewCenter(card);
                new Actions(driver).moveToElement(card).pause(java.time.Duration.ofMillis(250)).perform();

                // 2) Butonu kart içinden tekrar locate et (stale’i azaltır)
                btn = card.findElement(btnInCardBy);

                // 3) Tıklamayı dene; değilse JS fallback
                try {
                    waitFor().until(ExpectedConditions.elementToBeClickable(btn)).click();
                } catch (Exception clickEx) {
                    // görünürlük/overlay sorunlarında en sağlam yol: href’i al ve JS ile yeni sekme aç
                    String href = btn.getAttribute("href");
                    ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", href);
                }
                // başarılıysa döngüden çık
                break;

            } catch (StaleElementReferenceException stale) {
                // kart/btn yeniden renderlanmış; döngü bir daha denesin
                if (i == 2) throw stale;
            }
        }

        // 4) Yeni sekmeye geç
        String current = driver.getWindowHandle();
        waitFor().until(d -> d.getWindowHandles().size() > 1);
        for (String h : driver.getWindowHandles()) {
            if (!h.equals(current)) { driver.switchTo().window(h); break; }
        }

        // BasePage.sleep ile çok kısa bekleme
        sleep(1200);
        return new LeverJobPage(driver);
    }


    public boolean isJobListVisible() {
        return !driver.findElements(JOB_CARDS).isEmpty();
    }
}
