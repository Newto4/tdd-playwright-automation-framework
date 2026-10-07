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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class TestVerifyEndToEndOrderFlow extends PlaywrightAgent {
    private ProductPage productPage;
    private NavigationPage navigationPage;
    private CartPage cartPage;
    private Page page;
    private LoginPage loginPage;
    private Logger logger;
    private Map<String, Object> getData;
    private String username;
    private String password;
    private String productName;
    private String firstName;
    private String lastName;
    private String zipCode;
    private Browser browser;
    private BrowserContext browserContext;

    @BeforeEach
    public void init() {
        getData = JsonDataReader.loadTestData(this.getClass());
        browser = WebAgent.getBrowser(PlaywrightAgent.getEngine());
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page = browserContext.newPage();
        productPage = new ProductPage(page);
        navigationPage = new NavigationPage(page);
        loginPage = new LoginPage(page);
        cartPage = new CartPage(page);
        logger = LoggerFactory.getLogger(VerifyLoginTest.class);

        username = getData.get("userName").toString();
        password = getData.get("password").toString();
        productName = getData.get("productName").toString();
        firstName = getData.get("firstName").toString();
        lastName = getData.get("lastName").toString();
        zipCode = getData.get("zipCode").toString();

    }

    @Test
    public void verifyProductDescriptionInCart() throws NoSuchProductException, InterruptedException, IOException {
        logger.info("Launch the app");
        navigationPage.launchTheApp();

        logger.info("Login into app");
        loginPage.loginIntoApp(username, password);

        logger.info("Add product into cart");
        productPage.addProductToCart(productName);

        logger.info("Navigate to cart");
        navigationPage.navigateToCart();
        CartPage.CaptureScreenShot(page);

        logger.info("Checkout the order");
        cartPage.doCheckout(firstName, lastName, zipCode);

        Assertions.assertTrue(cartPage.isOrderPlaced(), "Issue with ordering the product");

    }

    @AfterEach
    public void tearDown() {
        page.close();
    }
}
