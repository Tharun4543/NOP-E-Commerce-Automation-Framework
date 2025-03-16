package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountHomePage extends BasePage{

	public MyAccountHomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//span[text()='My Account']")
	public WebElement myAccountBtnEle;
	
	@FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Register']")
	public WebElement registerBtn;
	
	@FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Login']")
	public WebElement myAccLoginBtn;
	
	@FindBy(xpath = "//a[normalize-space()='Logout']")
	public WebElement logoutBtn;
	
	
	
	
	
	
	
	

	
	

}
