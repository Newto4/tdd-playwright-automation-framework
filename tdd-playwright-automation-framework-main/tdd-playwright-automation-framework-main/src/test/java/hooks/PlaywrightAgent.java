package hooks;

import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class PlaywrightAgent {
    private static Playwright playwright;

    /**
     * Start Playwright engine
     */
    private static void createEngine() {
        playwright = Playwright.create();
    }

    @BeforeAll
    public static void startEngine() {
         createEngine();
    }

    public static Playwright getEngine() {
        return playwright;
    }

    @AfterAll
    public static void tearDownAll() {
        playwright.close();
    }

}
