package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountSuccessPage;
import pageObjects.MyAccountHomePage;
import pageObjects.RegisterPage;
import testBase.BaseTest;
import utilities.StringUtilities;


public class RegisterTest extends BaseTest{
	@Test(description = "Register by user in the application", priority = 1, groups = {"smoke"})
	public void checkUserRegister() throws InterruptedException {
		try {
			RegisterPage rp = new RegisterPage(driver);
			String regPassword = StringUtilities.generateRandomPass();
			logger.info("*** RegisterTest execution got started");
			Boolean registerStatus = rp.VerifyUserRegister(new MyAccountHomePage(driver), 
								  new AccountSuccessPage(driver),
								  StringUtilities.generateRandomString(),
								  StringUtilities.generateRandomString(),
								  StringUtilities.generateRandomMail(),
								  StringUtilities.generateMobileNum(),
								  regPassword, regPassword);
			if(registerStatus) {
				Assert.assertEquals(true, registerStatus);
				logger.info("RegisterTest execution got passed");
			}
			else {
				logger.info("RegisterTest execution got failed");
				Assert.fail();
			}
			logger.info("RegisterTest execution got completed");
		}
		catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}


