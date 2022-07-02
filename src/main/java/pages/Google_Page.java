package pages;

import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.element.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
;

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

    public static By google_logo() {
        return By.xpath("//img[@alt='Google']");
    }

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
        BrowserActions.navigateToURL(driver, googleUrl + "/ncr", googleUrl);
        return this;
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
     * search By text
     *
     * @param searchKeyWord *
     * @return self reference
     */

    public SearchResults_Page searchByTextAndIndexList(String searchKeyWord) {
        (new ElementActions(driver))
                .type(search_textBx, searchKeyWord)
                .keyPress(search_textBx, Keys.ENTER);
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
        (new ElementActions(driver))
                .type(search_textBx, searchKeyword)
                .click(inputOrdinalNumber_SearchList(indexList));
        return new SearchResults_Page(driver);
    }

    public SearchResults_Page searchByTestAndIndexList_autoSuggest(String searchKeyword, String indexList) {
        (new ElementActions(driver))
                .type(search_textBx, searchKeyword)
                .click(findByTextAndIndexList(searchKeyword, indexList));
        return new SearchResults_Page(driver);
    }

}
