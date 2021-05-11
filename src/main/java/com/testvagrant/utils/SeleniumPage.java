package com.testvagrant.utils;


import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

/**
 * @author Chandan
 */

public class SeleniumPage extends DriverInit {
    public static SeleniumTest test;
    private WebElement element;
    private WebDriverWait wait;
    private Logger logger = Logger.getLogger("");

    /**
     * Method to wait execution until the element is clickable (enabled)
     *
     * @param element - WebElement
     */
    public void waitUntilElementClickable(WebElement element) {
        wait = new WebDriverWait(driver, 20);
        element = wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Method to wait execution until the element is visible on the webpage with log function
     *
     * @param element          - WebElement
     * @param timeOutInSeconds - wait timeout for the element in Seconds
     */
    public void waitUntilElementIsVisible(WebElement element, int timeOutInSeconds) {
        try {
            wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    /**
     * Method to click on element with log function
     *
     * @param element - WebElement to be clicked
     */
    public void click(WebElement element) {
        try {
            waitUntilElementIsVisible(element, 20);
            logger.info("Clicking on Element: " + element);
            element.click();
        } catch (NoSuchElementException exception) {
            test.captureScreenShots("Click_on_" + element);
            logger.error("Element Not Found, Could not click:" + element);
            throw exception;
        }
    }

    /**
     * Method to send input text to text field with log function
     *
     * @param element - WebElement
     * @param keys    - text to be entered into text field
     */
    public void sendKeys(WebElement element, String keys) {
        try {
            waitUntilElementIsVisible(element, 20);
            logger.info("Sending Keys to: " + element);
            element.clear();
            element.sendKeys(keys);
        } catch (NoSuchElementException exception) {
            logger.error("Element Not Found, Could not send Keys: " + element);
            throw exception;
        }
    }

    /**
     * Method to get text from the WebElement with log function
     *
     * @param element - WebElement
     * @return returns text captured from the WebElement
     */
    public String getText(WebElement element) {
        waitUntilElementIsVisible(element, 20);
        logger.info("Get Text from: " + element);
        return element.getText().trim();
    }

    /**
     * Method to accept alerts
     */
    public void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    /**
     * Method to dismiss alerts
     */
    public void dismissAlert() {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    /**
     * Method pause execution flow with specific time
     *
     * @param timeInMilliSeconds - time in milliseconds to pause the execution flow
     */
    public void sleep(long timeInMilliSeconds) {
        try {
            Thread.sleep(timeInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}