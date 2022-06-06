package com.springer.quality.ui;

import com.springer.quality.ui.model.UiConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;

@Slf4j
public abstract class UiTestBase {
    private WebDriver driver;

    protected final WebDriver getWebDriver(String browser, String driverVersion) {
        log.info("Starting Web Driver instance.");
        WebDriverManager.chromedriver().driverVersion(driverVersion != null ? driverVersion : "100.0").setup();

        if (browser == null || browser.equalsIgnoreCase("chrome")) {
            log.info("Launching Chrome driver instance");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new ChromeDriver(options);
            driver.get(getConfig().getBaseUrl());
            log.info("Chrome driver instance launch successfully");
        } else {
            ArrayList<String> browserList = new ArrayList<>();
            browserList.add("Chrome");
            browserList.add("IE");
            browserList.add("Firefox");
            log.error("Browser Name \"" + browser + "\" is wrong, please provide proper name from list :" + browserList);
            throw new WebDriverException("No Driver found with given name");
        }
        driver.manage().window().maximize();
        return driver;
    }

    protected abstract UiConfig getConfig();
}
