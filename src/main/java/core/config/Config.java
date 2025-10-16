package core.config;

public record Config(
        String baseUrl,
        String browser,          // CHROME | EDGE
        boolean headless,
        int implicitWait,
        int explicitWait,
        int pageLoadTimeout,
        int debugPauseSec              // <-- eklendi

) { }
