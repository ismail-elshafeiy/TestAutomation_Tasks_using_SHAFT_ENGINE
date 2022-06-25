package pages;

import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.element.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class TestAutomationU_Page {

    private static WebDriver driver;

    public TestAutomationU_Page(WebDriver driver) {
        this.driver = driver;
    }

    private final String taUUrl = System.getProperty("properties/project.properties", "taUUrl");


    //////////////////////////// Elements Locators ////////////////////////////

    private By checkBoxes(int index) {
        return By.xpath("//input[@type='checkbox'][" + index + "]");
    }

    public static By isCheckBoxes = By.xpath("//form[@id='checkboxes']/input");
    public By checkBoxesText = By.id("checkboxes");

    private By inputField = By.id("file-upload");
    private By upload_Button = By.id("file-submit");
    public static By uploadedFiles_text = By.id("uploaded-files");

    public static By fileUploader_dragDrop = By.id("drag-drop-upload");


    //////////////////////////// Business Actions ////////////////////////////

    /**
     * Navigate to Home Page
     *
     * @return self reference
     */
    @Step("Navigate to Home Page")
    public TestAutomationU_Page navigateTo_HomePage() {
        BrowserActions.navigateToURL(driver, taUUrl);
        return this;
    }

    @Step("Navigate to Home Page")
    public TestAutomationU_Page navigateTo_HomePage(String serviceName) {
        String url = taUUrl + "/" + serviceName;
        BrowserActions.navigateToURL(driver, url);
        return this;
    }

    public TestAutomationU_Page selectCheckBox(int index) {
        ElementActions.click(driver, checkBoxes(index));
        ElementActions.getText(driver, checkBoxesText);
        return this;
    }


    public TestAutomationU_Page clickUpload_dragDropArea() {
        ElementActions.click(driver, fileUploader_dragDrop);
        return this;
    }

    /**
     * Provides path of file to the form then clicks Upload button
     * Using sendKeys to send a path of specific file or image to be uploaded
     * but we use this method only if the TagName of the element is Input
     * if its not we should use the robot class to perform the keyboard actions
     * since the popup window is not HTML element and needed to be handled with the robot class
     *
     * @param absolutePathOfFile The complete path of the file to upload
     */
    // TODO check what is the problem with my framework when using type instead of "sendKeys"
    public TestAutomationU_Page uploadFileBy_inputFile(String absolutePathOfFile) throws InterruptedException {
        driver.findElement(inputField).sendKeys(absolutePathOfFile);
        return new TestAutomationU_Page(driver);
    }

    public TestAutomationU_Page clickUploadButton() {
        ElementActions.click(driver, upload_Button);
        return this;
    }

    public TestAutomationU_Page uploadFileBy_robot(String absolutePathOfFile) throws InterruptedException, AWTException {
        Robot robot = new Robot();

        // Copy your file’s absolute path to the clipboard
        StringSelection filePath = new StringSelection(absolutePathOfFile);
        // get the latest value is already store in the path
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(filePath, null);

        // Paste the file’s absolute path into the File name field of the File Upload dialog box
        // native key strokes for CTRL, V and ENTER keys
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(2000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(2000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(3000);
        return this;
    }

    public static String getUploadedFiles_text() {

        return ElementActions.getText(driver, uploadedFiles_text);
    }
}
