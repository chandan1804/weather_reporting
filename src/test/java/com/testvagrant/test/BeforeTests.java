package com.testvagrant.test;

import com.testvagrant.utils.DriverInit;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

/**
 * @author Chandan
 */

public class BeforeTests extends DriverInit {

    @BeforeTest
    @Parameters("browser")
    public void beforeTestSuite(String browser) {
        setBrowser(browser);
    }

    @AfterSuite
    public void closeBrowser() {
        driver.quit();
    }
}
