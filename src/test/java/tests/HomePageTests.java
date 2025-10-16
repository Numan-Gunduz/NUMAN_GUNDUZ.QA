package tests;




import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomePageTests extends BaseTest {

    @Test(description = "Insider ana sayfası açılıyor mu?")
    public void should_open_homepage_and_validate_title() {
        boolean ok = new HomePage(driver)
                .open()
                .isOpen();

        Assert.assertTrue(ok, "Ana sayfa açılmadı veya title beklenen formatta değil.");
    }
}
