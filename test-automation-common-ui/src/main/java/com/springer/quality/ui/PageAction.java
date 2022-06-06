package com.springer.quality.ui;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class PageAction {

    private static final int DEFAULT_TIMEOUT = 20;
    private static final long POLLING_INTERVAL = 1L;
    private final WebDriver driver;
    private By container;

    public PageAction(WebDriver driver) {
        this.driver = driver;
    }

    public void setContainer(By container) {
        this.container = container;
    }

    public WebElement scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    public void select(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    public FluentWait<WebDriver> fluentlyWait() {
        return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(POLLING_INTERVAL))
                .ignoring(NoSuchElementException.class);
    }

    public WebElement waitForPresence(By by, int timeout) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by)).get(0);
    }

    public WebElement waitForPresence(WebElement element, int timeout) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOfAllElements(element)).get(0);
    }


}
