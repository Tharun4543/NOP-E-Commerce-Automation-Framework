package testBase;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import pageObjects.MyAccountHomePage;

public class BaseTest {

    public static WebDriver driver;
    public MyAccountHomePage myAccountHomePage;
    public static Logger logger = LogManager.getLogger(BaseTest.class);
    public Properties pr;
    FileReader fr;
    DesiredCapabilities cap;
    // ✅ Load Properties File
    public void loadProperties() throws IOException {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
        fr = new FileReader(filePath);
        pr = new Properties();
        pr.load(fr);
    }
    
    
   

    // ✅ Launch Browser & Handle Initialization Issues
    @BeforeClass(groups = {"sanity", "smoke", "regression"})
    @Parameters({"os", "browser"})
    public void launchBrowser(String os, String browser) throws IOException {
        loadProperties();
        logger.info("Launching browser: " + browser + " on OS: " + os);
        
        if(pr.getProperty("exeEnv").equalsIgnoreCase("remote")) {
        	cap = new DesiredCapabilities();
        	//operating system configuration
        	if(os.equalsIgnoreCase("windows")) {
        		cap.setPlatform(Platform.WIN11);
        	}
        	else if(os.equalsIgnoreCase("linux")) {
        		cap.setPlatform(Platform.LINUX);
        	}
        	else if(os.equalsIgnoreCase("mac")) {
        		cap.setPlatform(Platform.MAC);
        	}
        	else {
        		logger.info("Invalid operating system");
        		return;
        	}
        	
        	//browser configuration
        	switch(browser.toLowerCase()) 
        	{
        		case "chrome":
        			cap.setBrowserName("chrome");
        			break;
        		case "edge":
        			cap.setBrowserName("MicrosoftEdge");
        			break;
        		case "firefox":
        			cap.setBrowserName("firefox");
        			break;
        		default:
        			logger.info("Invalid chrome");
            		return;
        	}
        	
        	driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
        }
        
        if(pr.getProperty("exeEnv").equalsIgnoreCase("local")) {
        	 switch (browser.toLowerCase()) {
             case "chrome":
                 driver = new ChromeDriver();
                 break;
             case "edge":
                 driver = new EdgeDriver();
                 break;
             case "firefox":
                 driver = new FirefoxDriver();
                 break;
             default:
                 throw new IllegalArgumentException("Invalid browser specified: " + browser);
         }
        }
       

        // ✅ Check if WebDriver is initialized
        if (driver == null) {
            throw new RuntimeException("WebDriver failed to initialize!");
        }

        // ✅ Browser Setup
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        // ✅ Navigate to Website
        String websiteURL = pr.getProperty("websiteURL");
        if (websiteURL == null || websiteURL.isEmpty()) {
            throw new RuntimeException("Website URL is missing in config.properties file!");
        }
        driver.get(websiteURL);
        logger.info("Navigated to: " + websiteURL);
    }

    // ✅ Close Browser After Test Execution
    @AfterClass(groups = {"sanity", "smoke", "regression"})
    public void quitBrowser() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed successfully.");
        }
    }
    
    public String takeScreenshot(String filename) {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        String targetFilePath = screenshotDir + filename + "_" + timeStamp + ".png";

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File targetFile = new File(targetFilePath);

        try {
            FileUtils.copyFile(srcFile, targetFile);
            return targetFilePath;
        } catch (IOException e) {
            System.out.println("Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }
}
