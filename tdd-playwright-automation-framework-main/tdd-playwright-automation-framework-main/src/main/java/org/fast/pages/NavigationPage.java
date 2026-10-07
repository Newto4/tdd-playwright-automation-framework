package org.fast.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.fast.configreader.ConfigReaderAgent;

public class NavigationPage {
    private static final String CART_LINK = "div#shopping_cart_container";

    private Page page;
    public NavigationPage(Page page) {
        this.page = page;
    }

    public void launchTheApp() {
        page.navigate(ConfigReaderAgent.getProperties("base.uri"));
    }

    /**
     * Navigate to shopping cart
     */
    public void navigateToCart() throws InterruptedException {
        page.locator(CART_LINK).click();
    }
}
