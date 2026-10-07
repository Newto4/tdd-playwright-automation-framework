package org.fast.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;

public class WebHandler {
    private static final String BUTTON_GENERIC_XPATH = "button:has-text('%s')";
    private static final String BUTTON_GENERIC_INPUT_XPATH = "input:has-text('%s')";
    private static final String INPUT_FIELD = "input[placeholder='%s']";

    /**
     * @param page
     * @param buttonName
     */
    public static void clickOnButton(Page page, String buttonName) {
        try {
            String formattedButtonSelector = String.format(BUTTON_GENERIC_XPATH, buttonName);
            Locator scopeLocator = page.locator(formattedButtonSelector);
            scopeLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            scopeLocator.click();
        } catch (PlaywrightException e) {
            String formattedButtonSelector = String.format(BUTTON_GENERIC_INPUT_XPATH, buttonName);
            Locator scopeLocator = page.locator(formattedButtonSelector);
            scopeLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            scopeLocator.click();
        }
    }


    public static boolean isButtonDisplaying(Page page, String buttonName) {
        String formattedButtonSelector = String.format(BUTTON_GENERIC_XPATH, buttonName);
        return page.locator(formattedButtonSelector).isVisible();
    }

    /**
     * @param scopedLocator
     * @param buttonName
     */
    public static void clickOnButton(Locator scopedLocator, String buttonName) {
        String formattedButtonSelector = String.format(BUTTON_GENERIC_XPATH, buttonName);
        Locator targetLocator = scopedLocator.locator(formattedButtonSelector);
        targetLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        targetLocator.click();
    }


    /**
     * @param scopedLocator
     * @param buttonName
     * @return
     */
    public static boolean isButtonDisplaying(Locator scopedLocator, String buttonName) {
        String formattedButtonSelector = String.format(BUTTON_GENERIC_XPATH, buttonName);
        try {
            return scopedLocator.locator(formattedButtonSelector).isVisible();
        } catch (PlaywrightException e) {
            return false;
        }
    }

    /**
     * @param page
     * @param fieldName
     * @param value
     */
    public static void fillTheInputField(Page page, String fieldName, String value) {
        page.locator(String.format(INPUT_FIELD, fieldName)).fill(value);
    }

    /**
     *
     * @param page
     * @param fieldName
     * @return
     */
    public static boolean isInputFieldDisplaying(Page page, String fieldName) {
        return page.locator(String.format("input[placeholder='%s']", fieldName)).isVisible();
    }


}
