
package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import pageObjects.MyAccountHomePage;
import pageObjects.MyDashboardPage;
import testBase.BaseTest;
import utilities.DataProvidersTest;

public class LoginDataDriven extends BaseTest{
	
	
	@Test(dataProvider = "LoginData", 
		  dataProviderClass = DataProvidersTest.class, groups = {"sanity", "regression"})
	public void loginDDT(String email, String password, String accountStatus) {
		try {
			LoginPage lpDTT = new LoginPage(driver);
			logger.info("*** LoginDDT Test execution got started ***");
			Boolean loginStatus = lpDTT.verifyUserLoginDTT(new MyAccountHomePage(driver), 
									 new MyDashboardPage(driver),
									 email,
									 password,
									 accountStatus);
			if(loginStatus) {
				logger.info("*** LoginDDT Test execution got passed ***");
				Assert.assertTrue(true);
			}
			else {
				logger.info("*** LoginDDT Test execution got failed ***");
				Assert.fail();
			}
		}
		catch(Exception e) {
			logger.info("*** Login DDT execution got failed" + e.getMessage());
			e.getStackTrace();
		}
	}
	
}
