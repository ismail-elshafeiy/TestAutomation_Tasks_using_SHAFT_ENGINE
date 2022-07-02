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

    @AfterMethod()
    public void tearDown(ITestResult result) {
        BrowserActions.closeCurrentWindow(driver.get());
    }


    @Test(groups = "FromProperties")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.google.com/ncr")
    @TmsLink("Tc_001")
    @Description("""
            - Open Google Chrome
            - Navigate to [https://www.google.com/ncr]
            - Assert that the page title is [Google]
            """)
    public void checkPageTitle() {
        // test data
        String expectedResult_pageTitle = jsonFileManager.get().getTestData("task1.expectedResult_pageTitle");
        //test steps
        new Google_Page(driver.get()).navigateTo_googlePage();
        Google_Page.getCurrentPage_Url();
        // Verifications
        Validations.verifyThat()
                .browser(driver.get()).title().isEqualTo(expectedResult_pageTitle)
                .withCustomReportMessage("Assert that the page title is [Google]")
                .perform();
    }

    @Test(groups = "FromProperties")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.google.com/ncr")
    @TmsLink("Tc_002")
    @Description("""
            - Open Google Chrome
            - Navigate to [https://www.google.com/ncr]
            - Assert that the Google logo is displayed
            """)
    public void checkGoogleLogoIsDisplayed() {
        new Google_Page(driver.get()).navigateTo_googlePage();
        // Verifications
        Validations.verifyThat()
                .element(driver.get(), Google_Page.google_logo())
                .matchesReferenceImage()
                .withCustomReportMessage("Assert that the Google logo is displayed")
                .perform();

    }

    @Test(groups = "FromProperties")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.google.com/ncr")
    @TmsLink("Tc_003")
    @Description("""
            - Open Google Chrome
            - Navigate to [https://www.google.com/ncr]
            - Search for [Selenium WebDriver]
            - Assert that the text of the first result is [Selenium - Web Browser Automation] 
            """)
    public void searchAndGetFirstResult() {
        // Test Data
        String searchKeyword = jsonFileManager.get().getTestData("task3.query");
        String indexInList = jsonFileManager.get().getTestData("task3.indexList");
        String indexInPage = jsonFileManager.get().getTestData("task3.indexPage");
        String expectedResult_searchResult = jsonFileManager.get().getTestData("task3.expectedResult_searchResult");
        // Test Steps
        new Google_Page(driver.get()).navigateTo_googlePage()
                .searchByTextAndIndexList(searchKeyword, indexInList);
        // Verifications
        Validations.assertThat()
                .element(driver.get(), SearchResults_Page.getSearchResultsNumber(indexInPage)).text()
                .contains(expectedResult_searchResult)
                .perform();
    }

    @Test(groups = "Firefox")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.google.com/ncr")
    @TmsLink("Tc_004")
    @Description("""
            - Open Mozilla Firefox
            - Navigate to [https://www.google.com/]
            - Search for [TestNG]
            - Assert that the text of the fourth result is [TestNG Tutorial]
            - Close Mozilla Firefox        
            """)
    public void searchForFourthResult() {
        // Test Data
        String searchKeyword = jsonFileManager.get().getTestData("task4.query");
        String indexInList = jsonFileManager.get().getTestData("task4.indexList");
        String indexInPage = jsonFileManager.get().getTestData("task4.indexPage");
        String expectedResult_searchResult = jsonFileManager.get().getTestData("task4.expectedResult_searchResult");
        // Test Steps
        new Google_Page(driver.get()).navigateTo_googlePage()
                .searchByTextAndIndexList(searchKeyword, indexInList);
        // Verifications
        Validations.assertThat()
                .element(driver.get(), SearchResults_Page.getSearchResultsNumber(indexInPage)).text()
                .contains(expectedResult_searchResult)
                .perform();
    }

    @Test(groups = "FromProperties")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.google.com/ncr")
    @TmsLink("Tc_005")
    @Issue("Bug_005")
    @Description("""
            - Open Google Chrome
            - Navigate to [https://www.google.com/ncr]
            - Search for [Cucumber IO]
            - Navigate to the second results page
            - Assert that the link of the second result contains [https://www.linkedin.com]        
                """)
    public void searchForSecondResultAndOpen() {
        // Test Data
        String searchKeyword = jsonFileManager.get().getTestData("task5.query");
        String indexInPage = jsonFileManager.get().getTestData("task5.indexPage");
        String expectedResult_searchResult = jsonFileManager.get().getTestData("task5.expectedResult_searchResult");
        // Test Steps
        new Google_Page(driver.get()).navigateTo_googlePage()
                .searchByTextAndIndexList(searchKeyword)
                .navigateTo_cucumberSearchResult(indexInPage);
        // Verifications
        Validations.verifyThat()
                .browser(driver.get()).url().contains(expectedResult_searchResult)
                .withCustomReportMessage("Assert that the link of the second result contains [https://www.linkedin.com]")
                .perform();
    }

}
