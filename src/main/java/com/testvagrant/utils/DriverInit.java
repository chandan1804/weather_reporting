package com.testvagrant.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * @author Chandan
 */

public class DriverInit {
    public static WebDriver driver;

    public static void initDriver(String browser) throws Exception {
        if ("Chrome".equals(browser)) {
            driverConfig(browser, "webdriver.chrome.driver", "ExternalTools\\chromedriver.exe");
        } else if ("Firefox".equals(browser)) {
            driverConfig(browser, "webdriver.gecko.driver", "ExternalTools\\geckodriver.exe");
        }
    }

    public static void driverConfig(String browser, String driverProperty, String driverPath) throws Exception {
        System.setProperty(driverProperty, driverPath);
        switch(browser)
        {
            case "Chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                options.setAcceptInsecureCerts(true);
                driver = new ChromeDriver(options);
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                break;
            case "Firefox":
                driver = new FirefoxDriver();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    public void setBrowser(String browser) {
        try {
            initDriver(browser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
