package pageObjects;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Alert;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class BasePage {
    protected WebDriver driver;
    WebDriverWait wait;
    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Default wait time
        PageFactory.initElements(driver, this);
    }

    // ✅ Click Element (Handles Click Issues)
    public void clickElement(WebElement ele) {
        try {
            waitUntilEleClickable(ele);
            ele.click();
        } catch (Exception e) {
            System.out.println("Click failed, trying JavaScript click");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", ele);
        }
    }

    // ✅ Enter Text (Clears and Sends Keys)
    public void enterText(WebElement ele, String text) {
        waitUntilEleDisplayed(ele);
        ele.clear();
        ele.sendKeys(text);
    }

    // ✅ Wait Until Element is Visible
    public void waitUntilEleDisplayed(WebElement ele) {
        wait.until(ExpectedConditions.visibilityOf(ele));
    }

    // ✅ Wait Until Element is Clickable
    public void waitUntilEleClickable(WebElement ele) {
        wait.until(ExpectedConditions.elementToBeClickable(ele));
    }

    // ✅ Select Dropdown by Visible Text
    public void selectDropdownByText(WebElement ele, String value) {
        waitUntilEleDisplayed(ele);
        Select select = new Select(ele);
        select.selectByVisibleText(value);
    }

    // ✅ Select Dropdown by Value
    public void selectDropdownByValue(WebElement ele, String value) {
        waitUntilEleDisplayed(ele);
        Select select = new Select(ele);
        select.selectByValue(value);
    }

    // ✅ Get Element Text
    public String getElementText(WebElement ele) {
        waitUntilEleDisplayed(ele);
        return ele.getText();
    }

    // ✅ Get Attribute Value
    public String getElementAttribute(WebElement ele, String attribute) {
        waitUntilEleDisplayed(ele);
        return ele.getDomAttribute(attribute);
    }

    // ✅ Scroll to Element
    public void scrollToElement(WebElement ele) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", ele);
    }
    
    public Boolean isElementDisplayed(WebElement ele) {
    	return ele.isDisplayed();
    }

    // ✅ Capture Screenshot
    

    // ✅ Accept Alert
    public void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    // ✅ Dismiss Alert
    public void dismissAlert() {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    // ✅ Get Alert Text
    public String getAlertText() {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }
}

