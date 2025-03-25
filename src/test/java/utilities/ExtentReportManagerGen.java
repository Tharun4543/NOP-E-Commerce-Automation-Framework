package utilities;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import testBase.BaseTest;

public class ExtentReportManagerGen implements ITestListener {
    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();
    private String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/" + repName);
        sparkReporter.config().setDocumentTitle("NOP E-Commerce Automation Report");
        sparkReporter.config().setReportName("NOP Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "NOP E-commerce");
        extent.setSystemInfo("Module", "Users Creation");
        extent.setSystemInfo("Sub Module", "Register/Login");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getTestClass().getName() + " - " + result.getName());
        test.assignCategory(result.getMethod().getGroups());
        testThreadLocal.set(test);
    }

    public void onTestSuccess(ITestResult result) {
        ExtentTest test = testThreadLocal.get();
        if (test != null) {
            test.log(Status.PASS, result.getName() + " executed successfully");
        }
    }

    public void onTestFailure(ITestResult result) {
        ExtentTest test = testThreadLocal.get();
        if (test != null) {
            test.log(Status.FAIL, result.getName() + " failed");
            test.log(Status.INFO, result.getThrowable().getMessage());

            try {
                String imgPath = ((BaseTest) result.getInstance()).takeScreenshot(result.getName());
                test.addScreenCaptureFromPath(imgPath);
            } catch (Exception e) {
                test.log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
            }
        }
    }

    public void onTestSkipped(ITestResult result) {
        ExtentTest test = testThreadLocal.get();
        if (test != null) {
            test.log(Status.SKIP, result.getName() + " skipped");
            test.log(Status.INFO, result.getThrowable().getMessage());
        }
    }
    public void onFinish(ITestContext testContext) {
        if (extent != null) {
            extent.flush();
        }
        
        File reportFile = new File(System.getProperty("user.dir") + "/reports/" + repName);
        if (reportFile.exists()) {
            try {
                Desktop.getDesktop().browse(reportFile.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Report file not found: " + reportFile.getAbsolutePath());
        }
    }
}

