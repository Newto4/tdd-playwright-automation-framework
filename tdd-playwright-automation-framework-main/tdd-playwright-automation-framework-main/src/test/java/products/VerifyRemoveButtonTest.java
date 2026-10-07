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

@Tag("@sanity")
public class VerifyRemoveButtonTest extends PlaywrightAgent {
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
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
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
    public void verifyRemoveButtonTest() throws NoSuchProductException {
        logger.info("Launch the app");
        navigationPage.launchTheApp();

        logger.info("Login into app");
        loginPage.loginIntoApp(username, password);

        logger.info("Verify remove button is not displaying for all product");
        Assertions.assertFalse(productPage.isRemoveButtonDisplays(productName), "Remove button for product is present");

        logger.info("Add product into cart");
        productPage.addProductToCart(productName);
        Assertions.assertTrue(productPage.isRemoveButtonDisplays(productName), "Remove button for product is not present");
    }

    @Test
    public void verifyRemoveButtonForProductInCartTest() throws NoSuchProductException, InterruptedException {
        logger.info("Launch the app");
        navigationPage.launchTheApp();

        logger.info("Login into app");
        loginPage.loginIntoApp(username, password);

        logger.info("Add product into cart");
        productPage.addProductToCart(productName);

        Assertions.assertTrue(productPage.isRemoveButtonDisplays(productName), "Remove button for product is not present");

        logger.info("Navigate to cart");
        navigationPage.navigateToCart();

        Assertions.assertTrue(cartPage.isRemoveButtonDisplayForProduct(productName), "Remove button for product is not present in cart");

    }

    @Test
    public void verifyProductRemoveFunctionalityFromCart() throws NoSuchProductException, InterruptedException {
        logger.info("Launch the app");
        navigationPage.launchTheApp();

        logger.info("Login into app");
        loginPage.loginIntoApp(username, password);

        logger.info("Add product into cart");
        productPage.addProductToCart(productName);

        logger.info("Navigate to cart");
        navigationPage.navigateToCart();

        Assertions.assertTrue(cartPage.isRemoveButtonDisplayForProduct(productName),
                "Remove button for product is not present in cart");

        logger.info("Remove product from cart");
        cartPage.removeProductFromCart(productName);

        logger.info("Navigate to product page");
        cartPage.navigateToProductPage();

        Assertions.assertFalse(productPage.isRemoveButtonDisplays(productName),
                "Product is not removed from cart");

    }


}
