package core.utlis;




import core.driver.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Screenshots {
    private Screenshots(){}

    public static Path takePng(String nameHint) {
        try {
            byte[] png = ((TakesScreenshot) DriverFactory.get()).getScreenshotAs(OutputType.BYTES);
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path dir = Path.of("target","screenshots");
            Files.createDirectories(dir);
            Path file = dir.resolve(ts + "_" + sanitize(nameHint) + ".png");
            Files.write(file, png);
            return file;
        } catch (Exception e) {
            throw new RuntimeException("Screenshot failed", e);
        }
    }

    private static String sanitize(String s) {
        return s.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
