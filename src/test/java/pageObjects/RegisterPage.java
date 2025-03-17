package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {

	public RegisterPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id = "input-firstname")
	private WebElement firstNameInp;
	
	@FindBy(id = "input-lastname")
	private WebElement lastNameInp;
	
	
	@FindBy(id = "input-email")
	private WebElement emailInp;
	
	@FindBy(id = "input-telephone")
	private WebElement telphoneInp;
	
	@FindBy(id = "input-password")
	private WebElement passwordinp;
	
	@FindBy(id = "input-confirm") 
	private WebElement confirmPassInp;
	
	@FindBy(xpath = "//input[@name='agree']")
	private WebElement privacyCheckBox;
	
	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement continueBtn;
	
	public Boolean VerifyUserRegister(MyAccountHomePage myAccountPage, 
			AccountSuccessPage asp, 
			String firstName, 
			String lastName,
			String email,
			String telephone,
			String password,
			String conPassword
			) {
		clickElement(myAccountPage.myAccountBtnEle);
		clickElement(myAccountPage.registerBtn);
		waitUntilEleDisplayed(firstNameInp);
		enterText(firstNameInp, firstName);
		enterText(lastNameInp, lastName);
		enterText(emailInp, email);
		enterText(telphoneInp, telephone);
		enterText(passwordinp, password);
		enterText(confirmPassInp, conPassword);
		clickElement(privacyCheckBox);
		clickElement(continueBtn);
		waitUntilEleDisplayed(asp.registerSucMsg);
		return isElementDisplayed(asp.registerSucMsg);
	}
}
