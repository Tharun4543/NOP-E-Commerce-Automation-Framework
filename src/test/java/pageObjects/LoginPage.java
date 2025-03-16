package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id = "input-email")
	public WebElement userEmail;
	
	@FindBy(id = "input-password")
	public WebElement userPassword;
	
	@FindBy(xpath = "//input[@value='Login']")
	public WebElement loginBtn;
	
	public boolean verifyUserLogin(MyAccountHomePage mah, 
			                       MyDashboardPage mdp,
			                       String email,
			                       String password) {
		clickElement(mah.myAccountBtnEle);
		clickElement(mah.myAccLoginBtn);
		waitUntilEleDisplayed(userEmail);
		enterText(userEmail, email);
		enterText(userPassword, password);
		clickElement(loginBtn);
		waitUntilEleDisplayed(mdp.myAccountHeader);
		return isElementDisplayed(mdp.myAccountHeader);
	}
	
	public boolean verifyUserLoginDTT(MyAccountHomePage mah, 
            MyDashboardPage mdp,
            String emailLogin,
            String passwordLogin,
            String accountStatusLogin) {
		boolean currentAccStatus = false;  // Initialize with default value
		clickElement(mah.myAccountBtnEle);
		clickElement(mah.myAccLoginBtn);
		waitUntilEleDisplayed(userEmail);
		enterText(userEmail, emailLogin);
		enterText(userPassword, passwordLogin);
		clickElement(loginBtn);
		boolean isLoggedIn = isElementDisplayed(mdp.myAccountHeader); // Store login status
		if (isLoggedIn && accountStatusLogin.equals("valid")) {
			currentAccStatus = true;
			logOut(mah);
			
		}
		else if (!isLoggedIn && accountStatusLogin.equals("invalid")) {
			currentAccStatus = true;
		} 
		else if(isLoggedIn && accountStatusLogin.equals("invalid")){
			currentAccStatus = false;
			logOut(mah);
			
		}
		else if(!isLoggedIn && accountStatusLogin.equals("valid")){
			currentAccStatus = false;
		}
		return currentAccStatus;
	}
	
	public void logOut(MyAccountHomePage mah) {
		waitUntilEleDisplayed(mah.myAccountBtnEle);
		clickElement(mah.myAccountBtnEle);
		clickElement(mah.logoutBtn);
	}
}
