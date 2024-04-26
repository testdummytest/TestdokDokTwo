package UiRegressionTests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import Framework.DataProviderClass;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ChLoginBaseTest {

    
    public WebDriver driver;
    public AndroidDriver androidDriver;
    private static final Logger logger = LogManager.getLogger(ChLoginBaseTest.class);

    @BeforeMethod
    public void setup() throws InterruptedException {
        System.setProperty("webdriver.chrome.verboseLogging", "true");
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        DataProviderClass.getEnvUrl();
        String EnvUrl = DataProviderClass.EnvUrl; 
        logger.info("URL is: " + EnvUrl);
        driver.get(EnvUrl);
        // driver.get("https://qa.dev.docdok.ch");
        Thread.sleep(1000);
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        String methodName = result.getName().toString().trim();
        // Take screenshot
        if (result.getStatus() == ITestResult.FAILURE)
            takeScreenshot(methodName);
        // Quit the WebDriver
        if (driver != null) {
            driver.quit();
        }
    }

    private void takeScreenshot(String methodNametestName) {

        // Take screenshot and save it to a file
        if (driver instanceof TakesScreenshot) {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                // Create a directory to store screenshots if it doesn't exist
                String directoryPath = System.getProperty("user.dir") + "/Screenshot/";
                Path directory = Paths.get(directoryPath);
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                }

                // Define the screenshot file name with timestamp
                String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
                String screenshotFileName = methodNametestName + timestamp + ".png";

                // Save the screenshot to the specified path
                Path screenshotPath = Paths.get(directoryPath + screenshotFileName);
                Files.copy(screenshotFile.toPath(), screenshotPath);
                logger.info("Screenshot saved: " + screenshotPath.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Driver does not support taking screenshots");
        }
    }

}




