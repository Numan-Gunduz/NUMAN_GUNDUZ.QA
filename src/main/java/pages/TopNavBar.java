package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopNavBar extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(TopNavBar.class);

    // <li class="nav-item dropdown"> ... <a ...>Company</a> ... </li>
    private static final By COMPANY_LI =
            By.xpath("//li[contains(@class,'nav-item')][.//a[@id='navbarDropdownMenuLink' and normalize-space()='Company']]");

    private static final By COMPANY_ANCHOR =
            By.xpath("//li[contains(@class,'nav-item')][.//a[@id='navbarDropdownMenuLink' and normalize-space()='Company']]//a[@id='navbarDropdownMenuLink']");

    // Bu dropdown SADECE Company <li>'sinin içindeki menü
    private static final By COMPANY_DROPDOWN =
            By.xpath("//li[contains(@class,'nav-item')][.//a[@id='navbarDropdownMenuLink' and normalize-space()='Company']]//div[contains(@class,'dropdown-menu') and contains(@class,'new-menu-dropdown-layout-6')]");

    private static final By COMPANY_DROPDOWN_SHOW =
            By.xpath("//li[contains(@class,'nav-item')][.//a[@id='navbarDropdownMenuLink' and normalize-space()='Company']]//div[contains(@class,'new-menu-dropdown-layout-6') and contains(@class,'show')]");

    private static final By CAREERS_IN_COMPANY =
            By.xpath("//li[contains(@class,'nav-item')][.//a[@id='navbarDropdownMenuLink' and normalize-space()='Company']]//div[contains(@class,'new-menu-dropdown-layout-6')]//a[normalize-space()='Careers']");

    public TopNavBar(WebDriver driver) { super(driver); }

    public CareersPage goToCareers() {
        // header'a çık
        scrollToTop();

        // 1) Company anchor'ı görünür/clickable
        WebElement company = waitFor().until(ExpectedConditions.elementToBeClickable(COMPANY_ANCHOR));
        log.info("Company menüsüne tıklanıyor (bağlamsal)...");
        company.click();

        // 2) Dropdown açıldı mı? (aria-expanded/show/link)
        if (!waitDropdownOpened()) {
            log.info("Dropdown açılmadı, hover + tekrar click deneniyor...");
            new Actions(driver).moveToElement(company).pause(200).click(company).perform();
            if (!waitDropdownOpened()) {
                log.info("JS click fallback uygulanıyor...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", company);
                if (!waitDropdownOpened()) {
                    throw new TimeoutException("Company dropdown açılamadı.");
                }
            }
        }

        // 3) Dropdown açıkken “Careers”a tıkla (aynı <li> context'inde)
        log.info("'Careers' linkine tıklanıyor...");
        WebElement careers = waitFor().until(ExpectedConditions.elementToBeClickable(CAREERS_IN_COMPANY));
        try {
            careers.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", careers);
        }

        // Menünün kapanmaması için kısa bir hover dropdown üzerinde tutalım
        try {
            WebElement dd = driver.findElement(COMPANY_DROPDOWN);
            new Actions(driver).moveToElement(dd).pause(150).perform();
        } catch (Exception ignore) {}

        return new CareersPage(driver);
    }

    private boolean waitDropdownOpened() {
        try {
            // aria-expanded sinyali + .show sınıfı + Careers link visibility
            waitFor().until(ExpectedConditions.or(
                    ExpectedConditions.attributeContains(COMPANY_ANCHOR, "aria-expanded", "true"),
                    ExpectedConditions.visibilityOfElementLocated(COMPANY_DROPDOWN_SHOW),
                    ExpectedConditions.visibilityOfElementLocated(CAREERS_IN_COMPANY)
            ));
            log.info("Company dropdown açık (expanded/show/link).");
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
