package tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.HomePage;
import pages.TopNavBar;

public class CareersTests extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(CareersTests.class);

    @Test(description = "Home'da Company>Careers, sonra Careers sayfasında tekrar Company>Careers ve 3 blok doğrulaması")
    public void should_open_careers_from_home_then_verify_blocks() {
        log.info("Ana sayfa açılıyor...");
        new HomePage(driver).open();
        log.info("Home'dan Company>Careers adımı...");
        CareersPage careers = new TopNavBar(driver).goToCareers();
        Assert.assertTrue(careers.isOpen(), "Careers sayfası Home'dan açılamadı.");
        log.info("Careers sayfasında tekrar Company>Careers adımı (dropdown davranışını doğrula)...");
        careers = new TopNavBar(driver).goToCareers();
        Assert.assertTrue(careers.isOpen(), "Careers sayfasında Company>Careers tekrar açılmadı.");
        log.info("Blok doğrulamaları başlıyor...");
        Assert.assertTrue(careers.isLocationsVisible(), "'Our Locations' bloğu görünür değil.");
        Assert.assertTrue(careers.isTeamsVisible(), "'Teams' (See all teams) bloğu görünür değil.");
        Assert.assertTrue(careers.isLifeAtInsiderVisible(), "'Life at Insider' bloğu görünür değil.");
        log.info("Blok doğrulamaları tamam.");
    }
}
