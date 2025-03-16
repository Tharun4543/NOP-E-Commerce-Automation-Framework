package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountSuccessPage extends BasePage{

	public AccountSuccessPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	public WebElement registerSucMsg;
	
	

}
