package com.testvagrant.page;

import com.testvagrant.utils.SeleniumPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Chandan
 */

public class HomePage extends SeleniumPage {

    @FindBy(css = ".ndtvlogo > a:nth-child(1)")
    WebElement homePageLogo;
    @FindBy(css = ".notnow")
    WebElement alertPopup;
    @FindBy(css = "#h_sub_menu")
    WebElement subMenuExpand;
    @FindBy(linkText = "WEATHER")
    WebElement weatherSectionButton;
    @FindBy(css = ".infoHolder")
    WebElement weatherPageHeader;
    @FindBy(css = "#loading")
    WebElement weatherReportLoading;

    /**
     * Method to navigate to specified URL
     * @param url an absolute URL giving the base location of the website
     * @return returns the result of the test
     */
    public boolean navigateToUrl(String url) {
        driver.navigate().to(url);
        return homePageLogo.isDisplayed();
    }

    /**
     * Method to test sub menu button functionality to expand the hidden tab
     * @return returns the result of the test
     */
    public boolean subMenuExpandButton() {
        if (alertPopup.isDisplayed()) {
            click(alertPopup);
        }
        click(subMenuExpand);
        return weatherSectionButton.isDisplayed();
    }

    /**
     * Method to test on clicking weather tab user is taken to weather page
     * @return return the test result
     */
    public String weatherSection() {
        click(weatherSectionButton);
        return getText(weatherPageHeader);
    }
}