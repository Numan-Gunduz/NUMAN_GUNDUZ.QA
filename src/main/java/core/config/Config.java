package core.config;

public record Config(
        String baseUrl,
        String browser,
        boolean headless,
        int implicitWait,
        int explicitWait,
        int pageLoadTimeout,
        int debugPauseSec

) { }
