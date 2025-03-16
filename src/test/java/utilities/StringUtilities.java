package utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtilities {

	public static String generateRandomString() {
		return RandomStringUtils.randomAlphabetic(6);
	}
	
   public static String generateMobileNum() {
	   return RandomStringUtils.randomNumeric(10);
   }
	
   public static String generateRandomMail() {
	   return RandomStringUtils.randomAlphanumeric(6) + "@gmail.com";
   }
   
   public static String generateRandomPass() {
	   return RandomStringUtils.randomAlphanumeric(7);
   }
   
   
}
