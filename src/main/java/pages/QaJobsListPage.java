
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static core.utlis.Waits.waitFor;

public class QaJobsListPage extends BasePage {


    private static final By LOCATION_BOX = By.id("select2-filter-by-location-container");
    private static final By DEPT_BOX     = By.id("select2-filter-by-department-container");
    private static final By FIRST_JOB_CARD = By.cssSelector("#jobs-list .position-list-item");
    private static final By JOB_CARDS = By.cssSelector(
            "[class*='position'], .position-list > *, .job-item, a[href*='positions']"
    );
    public QaJobsListPage(WebDriver driver) { super(driver); }


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


    public LeverJobPage clickFirstViewRole() {

        waitFor().until(ExpectedConditions.presenceOfElementLocated(By.id("jobs-list")));

        By cardBy = FIRST_JOB_CARD;
        By btnInCardBy = By.xpath(".//a[contains(@href,'lever.co') or " +
                "normalize-space(.)='View Role' or .//span[normalize-space(.)='View Role']]");

        WebElement card;
        WebElement btn;

        for (int i = 0; i < 3; i++) {
            try {
                card = waitFor().until(ExpectedConditions.visibilityOfElementLocated(cardBy));
                scrollIntoViewCenter(card);
                new Actions(driver).moveToElement(card).pause(java.time.Duration.ofMillis(250)).perform();
                btn = card.findElement(btnInCardBy);
                try {
                    waitFor().until(ExpectedConditions.elementToBeClickable(btn)).click();
                } catch (Exception clickEx) {
                    String href = btn.getAttribute("href");
                    ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", href);
                }
                break;
            } catch (StaleElementReferenceException stale) {
                if (i == 2) throw stale;
            }
        }

        String current = driver.getWindowHandle();
        waitFor().until(d -> d.getWindowHandles().size() > 1);
        for (String h : driver.getWindowHandles()) {
            if (!h.equals(current)) { driver.switchTo().window(h); break; }
        }
        sleep(1200);
        return new LeverJobPage(driver);
    }


    public boolean isJobListVisible() {
        return !driver.findElements(JOB_CARDS).isEmpty();
    }
}
