package login;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import hooks.PlaywrightAgent;
import org.fast.browserconfig.WebAgent;
import org.fast.pages.LoginPage;
import org.fast.pages.NavigationPage;
import org.fast.pages.ProductPage;
import org.fast.utils.JsonDataReader;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Tag("@integration")
public class VerifyLoginTest extends PlaywrightAgent {
    private ProductPage productPage;
    private NavigationPage navigationPage;
    private Page page;
    private LoginPage loginPage;
    private Logger logger;
    private Map<String, Object> getData;
    private String username;
    private String password;
    private Browser browser;
    private BrowserContext browserContext;

    @BeforeEach
    public void init() {
        getData = JsonDataReader.loadTestData(this.getClass());
        browser = WebAgent.getBrowser(PlaywrightAgent.getEngine());
        browserContext = browser.newContext();
        page = browserContext.newPage();
        productPage = new ProductPage(page);
        navigationPage = new NavigationPage(page);
        loginPage = new LoginPage(page);
        logger = LoggerFactory.getLogger(VerifyLoginTest.class);

        username = getData.get("username").toString();
        password = getData.get("password").toString();

    }

    @Test
    public void loginTest() {
        logger.info("Launch the app");
        navigationPage.launchTheApp();

        logger.info("Login into app");
        loginPage.loginIntoApp(username, password);

        Assertions.assertEquals(productPage.fetchAllProductsElement().size(), productPage.fetchAllPriceElemets().size(),
                "All product does not contains price tag");
    }

    @AfterEach
    public void tearDown() {
        page.close();
    }
}
