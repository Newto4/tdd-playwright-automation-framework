package org.fast.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BasePage {

    /**
     *
     * @param page
     * @throws IOException
     */
    public static void CaptureScreenShot(Page page) throws IOException {
        File file = new File(Paths.get(System.getProperty("user.dir")).resolve("Screenshots").toString());
        if (!file.isDirectory()) {
            Files.createDirectory(Paths.get(System.getProperty("user.dir")).resolve("Screenshots"));
        }

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        Path path = Paths.get(System.getProperty("user.dir")).resolve("Screenshots")
                .resolve(stackTraceElements[2].getClassName().split("\\.")[1] + "#" + stackTraceElements[2].getMethodName() + ".png");
        page.screenshot(new Page.ScreenshotOptions().setPath(path).setFullPage(true));

    }
}
