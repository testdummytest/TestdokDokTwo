package UiRegressionTests;

import java.net.MalformedURLException;
import java.net.URL;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.android.AndroidDriver;
// import io.appium.java_client.remote.MobileCapabilityType;

public class MobileBaseTest {

    public AndroidDriver androidDriver ;

    @BeforeMethod
    public void setup() throws MalformedURLException, InterruptedException {

        System.out.println("called beforemethod from mobilebasetest");
        DesiredCapabilities dc = new DesiredCapabilities();

        // dc.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        dc.setCapability("deviceName", "emulator-5554");
        dc.setCapability("platformName", "android");
        dc.setCapability("appPackage", "ch.health.docdok");
        dc.setCapability("appActivity", "ch.health.docdok.MainActivity");
        // dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "2000");

        androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),dc);
        Thread.sleep(4000);
    }

    // @AfterMethod
    // public void tearDown() {
    //     androidDriver.closeApp();
    // }
}
