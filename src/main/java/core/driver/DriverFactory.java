package core.driver;



import core.config.Config;
import core.config.ConfigLoader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();

    private DriverFactory(){}

    public static void start() {
        Config cfg = ConfigLoader.get();
        BrowserType type = BrowserType.valueOf(cfg.browser());
        boolean headless = cfg.headless();

        WebDriver driver;
        switch (type) {
            case EDGE -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions opts = new EdgeOptions();
                opts.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                if (headless) opts.addArguments("--headless=new", "--disable-gpu");
                opts.addArguments("--start-maximized");
                driver = new EdgeDriver(opts);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions opts = new ChromeOptions();
                opts.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                if (headless) opts.addArguments("--headless=new", "--disable-gpu");
                opts.addArguments("--start-maximized");
                driver = new ChromeDriver(opts);
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(cfg.implicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(cfg.pageLoadTimeout()));
        TL.set(driver);
    }

    public static WebDriver get() {
        WebDriver d = TL.get();
        if (d == null) throw new IllegalStateException("Driver not started");
        return d;
    }

    public static void stop() {
        WebDriver d = TL.get();
        if (d != null) {
            d.quit();
            TL.remove();
        }
    }
}
