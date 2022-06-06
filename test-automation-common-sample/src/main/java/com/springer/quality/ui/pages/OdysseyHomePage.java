package com.springer.quality.ui.pages;

import com.springer.quality.ui.Page;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;


@Slf4j
public class OdysseyHomePage extends Page {

    private static final By HOME_PAGE_TEXT = By.xpath("//h1[contains(text(),'Odyssey...')]");
    private static String XPATH_CONTENT_ACQUISITION_LINK = "//a[contains(text(),'Content Acquisition 2')]";

    @Override
    protected void waitForLoad() {
        log.debug("Wait for page load");
        driver.switchTo().frame("page");
        action.waitForPresence(HOME_PAGE_TEXT, 10);
        driver.switchTo().defaultContent();
        log.debug("Page loaded");
    }

    public OdysseyHomePage clickOnContentAcqLink(){
        driver.switchTo().frame("navbar");
        log.info("Changed to frame navbaar");
        driver.findElement(By.xpath(XPATH_CONTENT_ACQUISITION_LINK)).click();
        return this;
    }

    public String getHomePageTitle(){
        return driver.getTitle();
    }

}
