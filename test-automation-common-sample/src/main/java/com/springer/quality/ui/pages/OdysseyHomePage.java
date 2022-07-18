package com.springer.quality.ui.pages;

import com.springer.quality.ui.Page;
import com.springer.quality.utilities.KeyboardActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import java.awt.*;
import java.awt.event.InputEvent;


@Slf4j
public class OdysseyHomePage extends Page {

    private static final By HOME_PAGE_TEXT = By.xpath("//h1[contains(text(),'Odyssey...')]");
    private static String XPATH_CONTENT_ACQUISITION_LINK = "//a[contains(text(),'Content Acquisition 2')]";
    private static boolean verifyLogin = false;

    @Override
    protected void waitForLoad() {
        log.debug("Wait for page load");
        driver.switchTo().frame("page");
        action.waitForPresence(HOME_PAGE_TEXT, 10);
        driver.switchTo().defaultContent();
        log.debug("Page loaded");
    }

    public OdysseyHomePage clickOnContentAcqLink() throws AWTException {
        driver.switchTo().frame("navbar");
        log.info("Changed to frame navbaar");
        driver.findElement(By.xpath(XPATH_CONTENT_ACQUISITION_LINK)).click();
        verifyLogin();
        return this;
    }

    public static boolean verifyLogin() throws AWTException {
        if (verifyLogin == false) {
            Robot robot = new Robot();
            robot.delay(1000);
            robot.mouseMove(0, 0);
            robot.delay(1000);
            robot.mouseMove(490, 195);
            robot.delay(1000);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(1000);

            KeyboardActions keyboard = new KeyboardActions();
            keyboard.type("kbt7177");
            robot.delay(500);
            keyboard.type("\t");
            robot.delay(500);
            keyboard.type("Kau@Kau@1234");
            robot.delay(1000);
            keyboard.type("\n");
            robot.delay(1000);
            verifyLogin = true;
        }
        return verifyLogin;
    }

    public String getHomePageTitle() {
        return driver.getTitle();
    }

}
