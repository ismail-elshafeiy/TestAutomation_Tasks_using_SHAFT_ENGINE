package pages;

import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.element.ElementActions;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
;

import java.io.File;
import java.io.IOException;

public class Google_Page {
    // driver
    private static WebDriver driver;

    // Constructor
    public Google_Page(WebDriver driver) {
        Google_Page.driver = driver;
    }

    private final String googleUrl = System.getProperty("googleUrl");

    //////////////////////////// Elements Locators ////////////////////////////
//    public static By googleLogo_image = By.xpath("//img[@id='hplogo']");

    public static By googleLogo_image = By.xpath("//img[@alt='Google']");
    private static final By search_textBx = By.xpath("//input[@name='q']");

    public static By findByTextAndIndexList(String searchKeyword, String index) {
        return By.xpath("(//li[@role='presentation']//div/span[contains(text(),'" + searchKeyword + "')])[" + index + "]");
    }

    private By inputOrdinalNumber_SearchList(String index) {
        return By.xpath("(//li[@role='presentation'])[" + index + "]");
    }

    //////////////////////////// Business Actions ////////////////////////////

    /**
     * Navigate to Home Page
     *
     * @return self reference
     */
    @Step("Navigate to Home Page")
    public Google_Page navigateTo_googlePage() {
        BrowserActions.navigateToURL(driver, "https://www.google.com/");
        return this;
    }

    /**
     * Get title Page
     *
     * @return self reference
     */
    @Step("Get Title Page")
    public static String getTitle_Page() {
        return BrowserActions.getCurrentWindowTitle(driver);
    }

    /**
     * Get Current Page URl
     *
     * @return self reference
     */
    @Step("Get Current Page URL")
    public static String getCurrentPage_Url() {
        return BrowserActions.getCurrentURL(driver);
    }

    /**
     * check logo is displayed ?
     *
     * @return self reference
     */
    @Step("Check logo is displayed?")
    public static boolean isGoogleLogoDisplayed(String logoName) throws IOException {


        return ElementActions.isElementDisplayed(driver, googleLogo_image);
    }


    /**
     * search By text
     *
     * @param searchKeyWord *
     * @return self reference
     */

    public SearchResults_Page searchByTextAndIndexList(String searchKeyWord) {
        ElementActions.type(driver, search_textBx, searchKeyWord);
        ElementActions.keyPress(driver, search_textBx, Keys.ENTER);
        return new SearchResults_Page(driver);
    }

    /**
     * Search By text and indexList in a search list
     *
     * @param searchKeyword *
     * @param indexList*
     * @return self reference
     */

    public SearchResults_Page searchByTextAndIndexList(String searchKeyword, String indexList) {
        ElementActions.type(driver, search_textBx, searchKeyword);
        ElementActions.click(driver, inputOrdinalNumber_SearchList(indexList));
        return new SearchResults_Page(driver);
    }

    public SearchResults_Page searchByTestAndIndexList_autoSuggest(String searchKeyword, String indexList) {
        ElementActions.type(driver, search_textBx, searchKeyword);
        ElementActions.click(driver, findByTextAndIndexList(searchKeyword, indexList));
        return new SearchResults_Page(driver);
    }

}
