package org.fast.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.fast.customexception.NoSuchProductException;
import org.fast.sauceconstants.GlobalConstants;
import org.fast.utils.WebHandler;

import java.util.List;

public class ProductPage {
    private final Page page;

    private static final String PRICE_XPATH = "//div[@class='inventory_list']/div[@class='inventory_item']//div[@class='inventory_item_price']";
    private static final String PRODUCTS = "div[class='inventory_list']>div[class='inventory_item']";
    private static final String PRODUCT_NAME = "text=%s";
    private static final String PROD_DESCRIPTION_TEXT = "div.inventory_item_desc";
    private static final String PRODUCT_NAME_CSS = "div.inventory_item_name ";
    private static final String ADD_TO_CART_BUTTON = "button:has-text('Add to cart')";

    public ProductPage(Page page) {
        this.page = page;
    }

    public List<Locator> fetchAllProductsElement() {
        return page.locator(PRODUCTS).all();
    }

    public List<Locator> fetchAllPriceElemets() {
        return page.locator(PRICE_XPATH).all();
    }

    /**
     * @param productName
     */
    public void addProductToCart(String productName) {
        Locator productLocator = fetchAllProductsElement().stream()
                .filter(w -> w.locator(PRODUCT_NAME_CSS)
                        .innerText().trim().equals(productName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(productName + " product name is invalid"));

        WebHandler.clickOnButton(productLocator, GlobalConstants.ADD_TO_CART_BUTTON);
    }

    /**
     * Add multiple product into cart
     *
     * @param productNames
     */
    public void addProductToCart(List<String> productNames) {
        productNames.forEach(this::addProductToCart);
    }

    /**
     * To check if the remove button is present for a specific product or not
     *
     * @param productName - product name to check remove button for
     * @return
     */
    public boolean isRemoveButtonDisplayed(String productName) {
        Locator productLocator = fetchAllProductsElement().stream()
                .filter(w -> w.locator(PRODUCT_NAME_CSS)
                        .innerText().trim().equals(productName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(productName + " product name is invalid"));
        return WebHandler.isButtonDisplaying(productLocator, "Remove");
    }


    /**
     * To check if the remove button is present for a all product or not
     *
     * @param productName - product name to check remove button for
     * @return
     */
    public boolean isRemoveButtonDisplays(List<String> productName) {
        return productName.stream().allMatch(product -> {
            Locator productLocator = fetchAllProductsElement().stream()
                    .filter(w -> w.locator(PRODUCT_NAME_CSS)
                            .innerText().trim().equals(product)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(product + " product name is invalid"));
            return WebHandler.isButtonDisplaying(productLocator, "Remove");
        });

    }

    /**
     * Using this method user can check if the product is present or not
     *
     * @param productName - for which description needs to check
     * @return boolean - true of description is presently else false
     */
    public boolean isDescriptionPresentForProduct(String productName) throws NoSuchProductException {
        return fetchAllProductsElement().stream()
                .filter(w -> w.locator(PRODUCT_NAME_CSS).innerText().trim().equals(productName))
                .findFirst().orElseThrow(() -> new NoSuchProductException(productName + " product name is invalid or product not present")).locator(PROD_DESCRIPTION_TEXT).isVisible();
    }

    /**
     * Using this method user can check if the product is present or not
     *
     * @param productNames - list of product names for which description needs to check
     * @return boolean - true of description is presently else false
     */
    public boolean isDescriptionPresentForProducts(List<String> productNames) throws NoSuchProductException {
        return productNames.stream().allMatch(product -> fetchAllProductsElement().stream()
                .filter(w -> w.locator(PRODUCT_NAME_CSS).innerText().trim().equals(product))
                .findFirst().orElseThrow(() -> new NoSuchProductException(product + " product name is invalid or product not present"))
                .locator(PROD_DESCRIPTION_TEXT).isVisible());
    }

    /**
     *
     * @param productNames
     * @return
     */
    public boolean isAddToCartButtonPresentForAllProduct(List<String> productNames) {
        return productNames.stream().allMatch(productName -> {
            return fetchAllProductsElement().stream()
                    .filter(w -> w.locator(PRODUCT_NAME_CSS).innerText().trim().equals(productName))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchProductException(productName + " product name is invalid or product not present"))
                    .locator(ADD_TO_CART_BUTTON).isVisible();
        });
    }

}
