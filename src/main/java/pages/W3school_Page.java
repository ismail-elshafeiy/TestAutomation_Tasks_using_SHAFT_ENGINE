package pages;

import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.element.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class W3school_Page {


    // driver
    private static WebDriver driver;
    private final String w3schoolUrl = System.getProperty("w3schoolUrl");

    // Constructor
    public W3school_Page(WebDriver driver) {
        this.driver = driver;
    }

    //////////////////////////// Elements Locators ////////////////////////////


    private By checkBoxes(int index) {
        return By.xpath("//input[@type='checkbox'][" + index + "]");
    }


    public By countryName_txt(String countryName) {
        return By.xpath("//table[@id='customers']//child::tr[4]//td[contains(text(),'" + countryName + "')]");
    }

    //////////////////////////// Business Actions ////////////////////////////

    /**
     * Navigate to Home Page
     *
     * @return self reference
     */
    @Step("Navigate to Home Page")
    public W3school_Page navigateTo_HomePage() {
        BrowserActions.navigateToURL(driver, w3schoolUrl);
        return this;
    }

    public String getCountryName(String countryName) {
        return ElementActions.getText(driver, countryName_txt(countryName));
    }


}
