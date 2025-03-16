package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import pageObjects.MyAccountHomePage;
import pageObjects.MyDashboardPage;
import testBase.BaseTest;


public class LoginTest extends BaseTest{
	
	@Test(groups = {"sanity"},description = "Verify user is able to login", priority = 1)
	public void checkUserLogin() {
		try {
			LoginPage lp = new LoginPage(driver);
			logger.info("*** LoginTest execution got started ***");
			Boolean loginStatus = lp.verifyUserLogin(new MyAccountHomePage(driver), 
								  new MyDashboardPage(driver),
								  pr.getProperty("userEmail"),
								  pr.getProperty("userPassword"));
			if(loginStatus) {
				Assert.assertEquals(true, loginStatus);
				logger.info("*** LoginTest execution got passed ***");
			}
			else {
				logger.info("*** LoginTest execution got failed ***");
				Assert.fail();
			}
			logger.info("*** LoginTest execution got completed ***");
		}
		catch(Exception e) {
			logger.info("*** Login failed due to exceptions ***" + e.getMessage());
			e.printStackTrace();
		}
	}

}
