package com.testvagrant.test;

import com.testvagrant.page.WeatherPage;
import com.testvagrant.utils.SeleniumTest;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * @author Chandan
 */

public class WeatherPageTest extends SeleniumTest {
    private WeatherPage weatherPage;

    @BeforeClass
    public void homePage() {
        weatherPage = PageFactory.initElements(driver, WeatherPage.class);
    }

    @Test(priority = 5)
    public void verifyWeatherDetailsPopup() {
        weatherPage.tempCompare();
    }
}
