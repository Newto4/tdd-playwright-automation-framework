package org.fast.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.fast.customexception.ProductNotFoundException;
import org.fast.sauceconstants.GlobalConstants;
import org.fast.utils.WebHandler;

import java.util.List;

public class CartPage extends BasePage {
    private static final String CART_ITEMS_CSS = "div.cart_item";
    private static final String PRODUCT_NAME_CSS = "div.inventory_item_name ";
    private static final String PROD_DESCRIPTION_TEXT = "div.inventory_item_desc";
    private static final String ORDER_COMPLETE_MESSAGE = "h2.complete-header";

    private Page page;

    public CartPage(Page page) {
        this.page = page;
    }

    /**
     * @return
     */
    public List<Locator> fetchAllCartItems() {
        return page.locator(CART_ITEMS_CSS).all();
    }

    /**
     * @param productName
     * @return
     */
    public boolean isProductPresentInCart(String productName) {
        return fetchAllCartItems().stream()
                .filter(w -> w.locator(PRODUCT_NAME_CSS).innerText().trim().equals(productName))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(productName + " not present in cart")).isVisible();
    }

    /**
     * @param productNames
     * @return
     */
    public boolean isProductPresentInCart(List<String> productNames) {
        return productNames.stream().allMatch(productName -> {
            return fetchAllCartItems().stream()
                    .filter(w -> w.locator(PRODUCT_NAME_CSS).innerText().trim().equals(productName))
                    .findFirst().orElseThrow(() -> new ProductNotFoundException(productName + " not present in cart")).isVisible();
        });
    }

    /**
     * @param productName
     * @return
     * @throws ProductNotFoundException
     */
    public boolean isRemoveButtonDisplayForProduct(String productName) throws ProductNotFoundException {
        Locator removeButtonLocator = fetchAllCartItems().stream()
                .filter(product -> product.locator(PRODUCT_NAME_CSS).innerText().trim().equals(productName))
                .findFirst().orElseThrow(() -> new ProductNotFoundException(productName + " not present in cart"));
        return WebHandler.isButtonDisplaying(removeButtonLocator, GlobalConstants.REMOVE_BUTTON);
    }

    /**
     * @param productNames
     * @return
     */
    public boolean isRemoveButtonDisplayForProduct(List<String> productNames) {
        return productNames.stream().allMatch(product -> {
            Locator buttonLocator = fetchAllCartItems()
                    .stream().filter(producParentLocator -> producParentLocator
                            .locator(PRODUCT_NAME_CSS).innerText().trim().equals(product))
                    .findFirst().orElseThrow(() -> new ProductNotFoundException(product + " not present in cart"));
            return WebHandler.isButtonDisplaying(buttonLocator, GlobalConstants.REMOVE_BUTTON);
        });
    }

    /**
     * @param productNames
     */
    public void removeProductFromCart(List<String> productNames) {
        productNames.forEach(productName -> {
            Locator removeButtonLocator = fetchAllCartItems().stream()
                    .filter(w -> w.locator(PRODUCT_NAME_CSS).innerText().trim().equals(productName)).findFirst()
                    .orElseThrow(() -> new ProductNotFoundException(productName + " not present in cart"));
            WebHandler.clickOnButton(removeButtonLocator, GlobalConstants.REMOVE_BUTTON);
        });
    }

    /**
     * Navigate back to the product page using continue shopping button
     */
    public void navigateToProductPage() {
        WebHandler.clickOnButton(page, GlobalConstants.CONTINUE_SHOPPING_BUTTON);
    }

    /**
     * @param productName
     * @return
     */
    public String fetchProductDescription(String productName) {
        return fetchAllCartItems().stream()
                .filter(w -> w.locator(PRODUCT_NAME_CSS).innerText().trim().equals(productName))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(productName + " not present in cart"))
                .locator(PROD_DESCRIPTION_TEXT).innerText();
    }

    /**
     * Click on cancel button to cancel the checkout
     */
    public void cancelTheCheckout() {
        WebHandler.clickOnButton(page, GlobalConstants.CANCEL_BUTTON);
    }

    public void fillInformation(String firstName, String lastName, String zipCode) {
        WebHandler.fillTheInputField(page, GlobalConstants.FIRST_NAME, firstName);
        WebHandler.fillTheInputField(page, GlobalConstants.LAST_NAME, lastName);
        WebHandler.fillTheInputField(page, GlobalConstants.ZIP_CODE, zipCode);
    }


    public void doCheckout(String firstName, String lastName, String zipCode) {
        navigateToCheckoutPage();
        fillInformation(firstName,lastName,zipCode);
        WebHandler.clickOnButton(page, GlobalConstants.CONTINUE_BUTTON);
        WebHandler.clickOnButton(page, GlobalConstants.FINISH_BUTTON);
    }

    public void navigateToCheckoutPage() {
        WebHandler.clickOnButton(page, GlobalConstants.CHECKOUT);
    }

    /**
     * @return
     */
    public boolean isOrderPlaced() {
        return page.locator(ORDER_COMPLETE_MESSAGE).isVisible();
    }


}
