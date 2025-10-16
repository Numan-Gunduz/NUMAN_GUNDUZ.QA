package core.config;


import java.io.InputStream;
import java.util.Properties;

public final class ConfigLoader {
    private static Config cached;

    private ConfigLoader(){}

    public static Config get() {
        if (cached == null) cached = load();
        return cached;
    }

    private static Config load() {
        try (InputStream is = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            Properties p = new Properties();
            if (is != null) p.load(is);

            String baseUrl = sysOr(p, "baseUrl", "https://useinsider.com/");
            String browser = sysOr(p, "browser", "CHROME").toUpperCase();
            boolean headless = Boolean.parseBoolean(sysOr(p, "headless","false"));
            int implicit = Integer.parseInt(sysOr(p, "implicitWait","5"));
            int explicit = Integer.parseInt(sysOr(p, "explicitWait","15"));
            int plt = Integer.parseInt(sysOr(p, "pageLoadTimeout","30"));

            return new Config(baseUrl, browser, headless, implicit, explicit, plt);
        } catch (Exception e) {
            throw new RuntimeException("Config load failed", e);
        }
    }

    private static String sysOr(Properties p, String key, String def) {
        return System.getProperty(key, p.getProperty(key, def));
    }
}
