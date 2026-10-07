package products;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import hooks.PlaywrightAgent;
import login.VerifyLoginTest;
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
public class VerifyAddProductTest extends PlaywrightAgent {

    private ProductPage productPage;
    private Page page;
    private LoginPage loginPage;
    private NavigationPage navigationPage;
    private Logger logger;
    private String productName;
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
        productPage = new ProductPage(page);
        loginPage = new LoginPage(page);
        navigationPage = new NavigationPage(page);
        logger = LoggerFactory.getLogger(VerifyLoginTest.class);

        productName = getData.get("productName").toString();
        username = getData.get("username").toString();
        password = getData.get("password").toString();
    }

    @Test
    public void testAddProductToCart() {
        logger.info("Launch the app");
        navigationPage.launchTheApp();

        logger.info("Login into app");
        loginPage.loginIntoApp(username, password);

        logger.info("Add product to cart");
        productPage.addProductToCart(productName);
        Assertions.assertTrue(productPage.isRemoveButtonDisplayed(productName), "Product is not added in cart");
    }

    @AfterEach
    public void tearDown() {
        page.close();
        browserContext.close();
        browser.close();
    }

}
