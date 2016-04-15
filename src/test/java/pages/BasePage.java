package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import utils.Waiting;

import java.util.concurrent.TimeUnit;

public abstract class BasePage {
    //please note, project contains another impl
    protected WebDriver driver;
    protected JavascriptExecutor js;

    BasePage(final WebDriver driver){
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    //if no spec time - use default
    protected WebElement find(final By locator){
        return find(locator, Waiting.MIDDLE);
    }

    //using fluentWait - to ignore exception, also can customize waiting option, how long, how often
    protected WebElement find(final By locator, final int timeout){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(Waiting.Polling.OFFTEN, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class);

        setImplicitlyTimeOut(Waiting.NONE);

        final WebElement[] element = {null};

        try {
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try {
                        element[0] = driver.findElement(locator);
                        return element[0] != null;
                    } catch (NoSuchElementException ex) {
                        return Boolean.FALSE;
                    }
                }
            });
        } catch (TimeoutException ex){
            Assert.fail(String.format("Element by: %s was not found", locator));
        }

        setImplicitlyTimeOut(Waiting.MIDDLE);

        return element[0];
    }

    protected void sendKeys(final By locator, final String text){
        WebElement el = find(locator);
        el.clear();
        el.sendKeys(text);

        //need to add logger
        //Logger.info(String.format("%s was entered to element, by: %s", text, locator));
    }

    protected void click(final By locator){
        WebElement element = find(locator);

        try {
            element.click();
        } catch (WebDriverException exception){
            if (exception.getMessage().contains("Element is not clickable at point")) {
                js.executeScript("arguments[0].click();", element);
            }
        }

        //Logger.info(String.format("Click on element, by: %s", locator));
    }



    protected boolean isElementPresent(final By locator){
        return isElementPresent(locator, Waiting.MIDDLE);
    }

    protected boolean isElementPresent(final By locator, final int timeout){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(Waiting.Polling.OFFTEN, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class);

        setImplicitlyTimeOut(Waiting.NONE);

        final WebElement[] element = {null};

        try {
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try {
                        element[0] = driver.findElement(locator);
                        return element[0] != null;
                    } catch (NoSuchElementException ex) {
                        return Boolean.FALSE;
                    }
                }
            });
        } catch (TimeoutException ex){
            return Boolean.FALSE;
        }

        setImplicitlyTimeOut(Waiting.MIDDLE);
        return Boolean.TRUE;
    }

    protected void setImplicitlyTimeOut(int timeoutInSeconds){
        driver.manage().timeouts().implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
    }

    protected abstract void waitPageToLoad();
}
