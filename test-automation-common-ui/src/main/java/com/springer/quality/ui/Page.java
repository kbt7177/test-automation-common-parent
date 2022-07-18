package com.springer.quality.ui;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

@Slf4j
public abstract class Page {
    protected WebDriver driver;
    protected PageAction action;

    void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    void setPageAction(PageAction action) {
        this.action = action;
    }

    public static <T extends Page> T create(WebDriver driver, Class<T> cls) {
        T pageObj = null;
        try {
            pageObj = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new PageCreationException(e);
        }
        pageObj.setDriver(driver);
        pageObj.setPageAction(new PageAction(driver));
        pageObj.waitForLoad();
        return pageObj;
    }

    protected abstract void waitForLoad();

    public <T extends Page> T refresh() {
        this.driver.navigate().refresh();
        return (T) this;
    }

    public void maximizeWindow() {
        log.trace("Maximizing Window");
        this.driver.manage().window().maximize();
    }

    public void setWindowSize(int width, int height) {
        this.driver.manage().window().setSize(new Dimension(width, height));
    }

    public <T extends Page> T clickOnAlertAccept() {
        this.driver.switchTo().alert().accept();
        log.debug("Clicked on Alert Accept");
        this.driver.switchTo().defaultContent();
        log.trace("Move to Default content");
        return (T) this;
    }

    public String getAlertText() {
        String value = this.driver.switchTo().alert().getText();
        log.debug("Get alert Text : " + value);
        this.driver.switchTo().defaultContent();
        log.trace("Move to Default content");
        return value;
    }

    public void close() {
        driver.close();
        driver.switchTo().defaultContent();
    }

    public void quite() {
        driver.quit();
    }
}
