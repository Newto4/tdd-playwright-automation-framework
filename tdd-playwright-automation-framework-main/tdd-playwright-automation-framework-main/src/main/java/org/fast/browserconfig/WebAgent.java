package org.fast.browserconfig;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.fast.configreader.ConfigReaderAgent;

import java.util.List;

public class WebAgent {
    /**
     * Using this method user can fetch a specific browser instance based on an execution type
     * @return Browser - instance of browser
     */
    public static Browser getBrowser(Playwright playwright) {
        String browserType = System.getProperty("BROWSER") != null ? System.getProperty("BROWSER") : ConfigReaderAgent.getProperties("browser");
        boolean isHeadless = System.getProperty("HEADLESS") != null ? Boolean.TRUE : Boolean.FALSE;

        return (Browser) switch (browserType) {
            case "Chrome" -> playwright.chromium()
                    .launch(new BrowserType.LaunchOptions().setHeadless(isHeadless).setArgs(List.of("--start-maximized")));

            case "Firefox" -> playwright.firefox()
                    .launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));

            default -> new IllegalArgumentException("Browser name is not correct");
        };
    }


}
