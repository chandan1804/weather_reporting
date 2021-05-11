package com.testvagrant.utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Chandan
 */

public class SeleniumTest extends SeleniumPage {
    private static String folderName;
    private Logger logger = Logger.getLogger("");

    /**
     * Method to Get current date to create a folder name with date format "YYYY-MM-dd-HH.mm" for saving failure screen shots
     */
    public void getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd-HH.mm");
        LocalDateTime now = LocalDateTime.now();
        folderName = dtf.format(now);
    }

    /**
     * Method to log test PASS ot FAIL based on the return value of the test
     *
     * @param condition            test result
     * @param conditionDescription provides test case description
     */
    public void verifyCondition(boolean condition, String conditionDescription) {
        if (condition) {
            logger.info("==> *** PASSED ASSERTION *** - " + conditionDescription + "\n");
        } else {
            captureScreenShots(conditionDescription);
            logger.error("==> !!! FAILED ASSERTION !!! - " + conditionDescription + "\n");
        }
    }

    /**
     * Method to assert string, to validate expected string with actual string
     *
     * @param actual      - actual string from the UI
     * @param expected    - expected string from the Input data (test data)
     * @param description - provides test case description
     */
    public void verifyStringMatch(String actual, String expected, String description) {
        try {
            Assert.assertEquals(expected, actual);
            logger.info("==> *** PASSED ASSERTION *** - " + description + "\n");
        } catch (AssertionError error) {
            String errorMsg = "Expected Text: " + "\"" + expected + "\"" + " | Actual Text: " + "\"" + actual + "\"" + "\n";
            logger.error("==> !!! FAILED ASSERTION !!! - " + description);
            captureScreenShots(description);
            logger.error(errorMsg);
            throw error;
        }
    }

    /**
     * Method to capture screen shot of FAILED test cases
     *
     * @param conditionDesc - test case description to save the screenshot with the test case name to differentiate easily
     */
    public void captureScreenShots(String conditionDesc) {
        getDate();
        File file = null;
        try {
            file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            assert file != null;
            FileUtils.copyFile(file, new File("logs/" + folderName + "/" + conditionDesc + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}