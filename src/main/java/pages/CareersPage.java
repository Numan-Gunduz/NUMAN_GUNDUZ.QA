package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CareersPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(CareersPage.class);

    private static final By LOCATIONS_HEADING = By.cssSelector("h3.category-title-media.ml-0");
    private static final By TEAMS_SEE_ALL_BTN = By.xpath("//a[contains(normalize-space(.),'See all teams')]");
    private static final By LIFE_AT_INSIDER_HEADING = By.cssSelector("h2.elementor-heading-title.elementor-size-default");

    public CareersPage(WebDriver driver) { super(driver); }

    public boolean isOpen() {
        boolean urlOk = driver.getCurrentUrl().contains("/careers");
        log.info("Careers URL kontrolü: {} -> {}", driver.getCurrentUrl(), urlOk);
        try {
            waitFor().until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(LOCATIONS_HEADING),
                    ExpectedConditions.presenceOfElementLocated(LIFE_AT_INSIDER_HEADING),
                    ExpectedConditions.presenceOfElementLocated(TEAMS_SEE_ALL_BTN)
            ));
            log.info("Careers ana bloklardan en az biri göründü.");
            return true;
        } catch (TimeoutException e) {
            log.warn("Careers ana bloklar beklenen sürede görünmedi.");
            return urlOk;
        }
    }

    public boolean isLocationsVisible() {
        try {
            WebElement el = waitFor().until(ExpectedConditions.visibilityOfElementLocated(LOCATIONS_HEADING));
            scrollIntoView(el);
            log.info("'Our Locations' başlığı göründü.");
            return true;
        } catch (TimeoutException e) {
            log.warn("'Our Locations' başlığı görünmedi.");
            return false;
        }
    }

    public boolean isTeamsVisible() {
        try {
            WebElement el = waitFor().until(ExpectedConditions.visibilityOfElementLocated(TEAMS_SEE_ALL_BTN));
            scrollIntoView(el);
            log.info("'See all teams' elementi göründü (Teams bloğu).");
            return true;
        } catch (TimeoutException e) {
            log.warn("Teams bloğu görünmedi.");
            return false;
        }
    }

    public boolean isLifeAtInsiderVisible() {
        try {
            WebElement el = waitFor().until(ExpectedConditions.visibilityOfElementLocated(LIFE_AT_INSIDER_HEADING));
            scrollIntoView(el);
            log.info("'Life at Insider' başlığı göründü.");
            return true;
        } catch (TimeoutException e) {
            log.warn("'Life at Insider' başlığı görünmedi.");
            return false;
        }
    }
}
