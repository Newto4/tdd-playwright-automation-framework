package products;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import hooks.PlaywrightAgent;
import login.VerifyLoginTest;
import org.fast.browserconfig.WebAgent;
import org.fast.customexception.NoSuchProductException;
import org.fast.pages.CartPage;
import org.fast.pages.LoginPage;
import org.fast.pages.NavigationPage;
import org.fast.pages.ProductPage;
import org.fast.utils.JsonDataReader;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Tag("@integration")
public class VerifyDescriptionPresenceForAllProductTest extends PlaywrightAgent {
    private ProductPage productPage;
    private NavigationPage navigationPage;
    private CartPage cartPage;
    private Page page;
    private LoginPage loginPage;
    private Logger logger;
    private Map<String, Object> getData;
    private String username;
    private String password;
    private List<String> productName;
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
        cartPage = new CartPage(page);
        logger = LoggerFactory.getLogger(VerifyLoginTest.class);

        username = getData.get("username").toString();
        password = getData.get("password").toString();
        productName = (List<String>) getData.get("productName");

    }

    @Test
    public void productDescriptionTest() throws NoSuchProductException {
        logger.info("Launch the app");
        navigationPage.launchTheApp();

        logger.info("Login into app");
        loginPage.loginIntoApp(username, password);

        logger.info("Verify description for all product");
        Assertions.assertTrue(productPage.isDescriptionPresentForProducts(productName), "Description for all product is not present");
    }


    @AfterEach
    public void tearDown() {
        page.close();
    }

}
