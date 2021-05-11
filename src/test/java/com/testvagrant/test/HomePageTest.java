package com.testvagrant.test;

import com.testvagrant.page.HomePage;
import com.testvagrant.utils.SeleniumTest;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


/**
 * @author Chandan
 */

public class HomePageTest extends SeleniumTest {
    private HomePage homePage;

    @BeforeClass
    public HomePage homePage() {
        homePage = PageFactory.initElements(driver, HomePage.class);
        return homePage;
    }

    @Test(priority = 1)
    @Parameters("url")
    public void verifyNavigateUrl(String url) {
        verifyCondition(homePage.navigateToUrl(url), "Verify that the Page Navigation is successful");
    }

    @Test(priority = 2)
    public void verifySubMenuButton() {
        verifyCondition(homePage.subMenuExpandButton(), "Verify that the Sub Menu button functionality");
    }

    @Test(priority = 3)
    public void verifyWeatherSectionButton() {
        verifyStringMatch(homePage.weatherSection(), "Current weather conditions in your city.", "Verify Weather section button functionality");
    }
}
