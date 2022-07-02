import com.shaft.driver.DriverFactory;
import com.shaft.gui.browser.BrowserActions;
import com.shaft.tools.io.JSONFileManager;

import com.shaft.validation.Validations;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Google_Page;
import pages.SearchResults_Page;
import pages.W3school_Page;

import java.io.IOException;

public class Google_Test {

    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final ThreadLocal<JSONFileManager> jsonFileManager = new ThreadLocal<>();

    @BeforeClass
    public void serTestData() {
        jsonFileManager.set(new JSONFileManager(System.getProperty("googleJson")));
    }

    @BeforeMethod(onlyForGroups = "Firefox")
    public void setUp_FireFox() {
        driver.set(DriverFactory.getDriver(DriverFactory.DriverType.DESKTOP_FIREFOX));

    }
    @BeforeMethod(onlyForGroups = "FromProperties")
    public void setUp_BeforeMethods() {
        driver.set(DriverFactory.getDriver());
    }

    @AfterMethod(enabled = false)
    public void tearDown(ITestResult result) {
        BrowserActions.closeCurrentWindow(driver.get());
    }


    @Test(groups = "FromProperties")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.google.com/ncr")
    @TmsLink("Tc_001")
    @Description("""
            Open Google Chrome
            Navigate to [https://www.google.com/ncr]
            Assert that the page title is [Google]
            """)
    public void checkPageTitle() {
        String expectedResult_pageTitle = "Google";

        new Google_Page(driver.get()).navigateTo_googlePage();
        Google_Page.getCurrentPage_Url();
        Assert.assertTrue(Google_Page.getTitle_Page().equals(expectedResult_pageTitle));
        Assert.assertEquals(Google_Page.getTitle_Page(), expectedResult_pageTitle);
    }

    @Test(groups = "FromProperties")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.google.com/ncr")
    @TmsLink("Tc_002")
    public void checkGoogleLogoIsDisplayed() throws IOException {
        new Google_Page(driver.get()).navigateTo_googlePage();
        Assert.assertTrue(Google_Page.isGoogleLogoDisplayed("googleLogo"));
    }

    @Test(groups = "FromProperties")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.google.com/ncr")
    @TmsLink("Tc_003")
    @Description("""
            Open Google Chrome
            Navigate to [https://www.google.com/ncr]
            Search for [Selenium WebDriver]
            Assert that the text of the first result is [Selenium - Web Browser Automation]
            Close Google Chrome      
            """)
    public void searchAndGetFirstResult() {
        // Test Data
        String searchKeyword = jsonFileManager.get().getTestData("query");
        String indexInList = jsonFileManager.get().getTestData("indexList");
        String indexInPage = jsonFileManager.get().getTestData("indexPage");
        String expectedResult_searchResult = jsonFileManager.get().getTestData("expectedResult_searchResult");
        // Test Steps
        new Google_Page(driver.get()).navigateTo_googlePage()
                .searchByTextAndIndexList(searchKeyword, indexInList);
        // Assertions
        Validations.assertThat().element(
                        driver.get(), SearchResults_Page.getSearchResultsNumber(indexInPage)).text()
                .contains(expectedResult_searchResult)
                .perform();
    }

    @Test(groups = "Firefox")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.google.com/ncr")
    @TmsLink("Tc_004")
    @Issue("Bug_004")
    @Description("""
            Open Mozilla Firefox
            Navigate to [https://www.google.com/]
            Search for [TestNG]
            Assert that the text of the fourth result is [TestNG Tutorial]
            Close Mozilla Firefox        
            """)
    public void searchForFourthResult() {
        // driver.set(BrowserFactory.getBrowser(BrowserFactory.ExecutionType.LOCAL, BrowserFactory.OperatingSystemType.WINDOWS, BrowserFactory.BrowserType.MOZILLA_FIREFOX));
        String searchKeyword = "TestNG";
        String indexInList = "1";
        String indexInPage = "4";
        String expectedResult_searchResult = "TestNG Tutorial";

        new Google_Page(driver.get()).navigateTo_googlePage()
                .searchByTextAndIndexList(searchKeyword, indexInList);
        Validations.assertThat().element(driver.get(),
                        SearchResults_Page.getSearchResultsNumber(indexInPage)).text()
                .contains(expectedResult_searchResult)
                .perform();
    }

    @Test(groups = "FromProperties")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.google.com/ncr")
    @TmsLink("Tc_005")
    @Issue("Bug_005")
    public void searchForSecondResultAndOpen() {
        String searchKeyword = "Selenium";
        String indexInPage = "1";
        String expectedResult_searchResult = "test";
        //driver.getDriver(); get selenium webdriver native
        String actualResult_currentUrl =
                new Google_Page(driver.get()).navigateTo_googlePage()
                        .searchByTextAndIndexList(searchKeyword)
                        .navigateTo_cucumberSearchResult(indexInPage)
                        .getCurrentPage_Url();
        Assert.assertEquals(actualResult_currentUrl, expectedResult_searchResult);
        System.out.println("Actual Result: " + actualResult_currentUrl + " == " + "Expected Result: "
                + expectedResult_searchResult);
    }


    @Test(groups = "FromProperties")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://the-internet.herokuapp.com/checkboxes")
    @TmsLink("Tc_006")
    @Issue("Bug_006")
    public void verifyCountryIsEqual() {
        String countryName = "Austria";

        String actualResult_countryName =
                new W3school_Page(driver.get()).navigateTo_HomePage()
                        .getCountryName(countryName);
        Assert.assertTrue(actualResult_countryName.contains("Austria"));
    }

    @Test(groups = "FromProperties")
    public void verifySearchResults() {
        new Google_Page(driver.get()).navigateTo_googlePage()
                .searchByTextAndIndexList("Selenium WebDriver");
        By searchResult_txt = By.xpath("//div[@id='result-stats']");
        var getSearchResults = driver.get().findElement(searchResult_txt).getText();
        System.out.println("Search results --> " + getSearchResults);
        Assert.assertNotEquals(getSearchResults, "");
    }

    @Test(groups = "FromProperties")
    public void searchBy_text_and_index_list() {
        String searchKeyword = "Selenium";
        String indexInList = "2";
        new Google_Page(driver.get()).navigateTo_googlePage()
                .searchByTestAndIndexList_autoSuggest(searchKeyword, indexInList);

    }
    @Test(groups = "FromProperties")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.google.com/ncr")
    @TmsLink("Tc")
    @Issue("Bug")
    public void SearchAndGetResultByText() {
        String searchKeyword = "Selenium WebDriver";
        new Google_Page(driver.get()).navigateTo_googlePage()
                .searchByTextAndIndexList(searchKeyword);
    }


}
