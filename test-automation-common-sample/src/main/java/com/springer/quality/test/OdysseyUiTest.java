package com.springer.quality.test;


import com.springer.quality.ui.Page;
import com.springer.quality.ui.UiTestBase;
import com.springer.quality.ui.config.MyUiConfig;
import com.springer.quality.ui.item.UiItem;
import com.springer.quality.ui.model.UiConfig;
import com.springer.quality.ui.pages.OdysseyHomePage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Set;

@Slf4j
public class OdysseyUiTest extends UiTestBase {

    public WebDriver driver = getWebDriver("chrome", "");


    @Override
    protected UiConfig getConfig() {
        return UiItem.instance().getConfig(MyUiConfig.class).getOdysseyBackendUrl();
    }

    @Test
    public void SampleUiTest() {
        try {
            OdysseyHomePage odysseyHomePage = Page.create(driver, OdysseyHomePage.class);
            Assert.assertEquals(odysseyHomePage.getHomePageTitle(),"Odyssey");
            log.info("Test case Pass Successfully");
        } catch (Exception e) {
            log.error("Exception in execution : ", e);
            Assert.assertTrue(false, "Error with Message : " + e.getMessage());
        }
    }


}
