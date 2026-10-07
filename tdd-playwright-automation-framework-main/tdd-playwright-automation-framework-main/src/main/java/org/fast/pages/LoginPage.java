package org.fast.pages;

import com.microsoft.playwright.Page;
import org.fast.utils.WebHandler;

public class LoginPage {
    private Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void loginIntoApp(String userName, String password) {
        page.locator("input[placeholder='Username']").fill(userName);
        page.locator("input[placeholder='Password']").fill(password);
        page.locator("text=Login").click();
    }
}
