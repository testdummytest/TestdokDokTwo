package PageObjects.Mobile;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Entities.Patient;
import Framework.Generate;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
// import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;

public class MobileHomePage extends MobileBasePage {
    public MobileHomePage(AndroidDriver driver) {
        super(driver);
    }

    public void setupForMobile() throws MalformedURLException {

        System.out.println("called beforemethod from homepage");
        DesiredCapabilities dc = new DesiredCapabilities();

        // dc.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        dc.setCapability("deviceName", "emulator-5554");
        dc.setCapability("platformName", "android");
        dc.setCapability("appPackage", "ch.health.docdok");
        dc.setCapability("appActivity", "ch.health.docdok.MainActivity");
        dc.setCapability("automationName", "UiAutomator2");
        // dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "2000");

        androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), dc);
        waitFewSeconds(6000);
    }

    public void fillUserNameAndPasswordForAnExistingPatient(Patient patient) {
        System.out.println("started app..");
        waitUntilVisibleByXpath("//android.widget.TextView[@text='GET STARTED']");
        WebElement getStarted = androidDriver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='GET STARTED']"));
        System.out.println("Find xpath of get started");
        click(getStarted);
        System.out.println("Clicked get started");
        waitUntilVisibleByClassName("android.widget.EditText");
        fillCredentialThenSignIn(patient);
    }

    private void fillCredentialThenSignIn(Patient patient) {
        System.out.println("called credentials");
        fillTextByClassNames(patient.getEmail(), "android.widget.EditText", 0);
        hideKeyboard();
        fillTextByClassNames(patient.getPassword(), "android.widget.EditText", 1);
        hideKeyboard();
        // clickByTouchAction(700,2417);
        // waitFewSeconds(4000);
        // MobileElement signIn = androidDriver.findElementByClassName("android.widget.Button");
        WebElement signIn = androidDriver.findElement(AppiumBy.className("android.widget.Button"));
        System.out.println("Getting path");
        click(signIn);
    }

    public void verifyThatTheUserLoginSuccessfully() {
        WebElement messagesTab = androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"main\"]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.widget.TextView"));
        String messageText = messagesTab.getText();
        Assert.assertEquals(messageText, "Hi amany!!!");
    }

    public void loginAsNewPatient(Patient patient) {
        insertUserNameAndPassword(patient);
        setNewPassword(patient);
        agreeTermsAndCondition();
    }

    private void insertUserNameAndPassword(Patient patient) {
        WebElement getStarted = androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"main\"]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup/android.widget.Button/android.widget.TextView"));
        click(getStarted);
        waitUntilVisibleByClassName("android.widget.EditText");
        fillTextByClassNames(patient.getEmail(), "android.widget.EditText", 0);
        hideKeyboard();
        fillTextByClassNames(patient.getPassword(), "android.widget.EditText", 1);
        hideKeyboard();
        WebElement signIn = androidDriver.findElement(AppiumBy.className("android.widget.Button"));
        click(signIn);
    }

    public void setNewPassword(Patient patient) {
        fillTextByXpath(patient.getPassword(), "//android.view.ViewGroup[@content-desc=\"main\"]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.widget.EditText");
        hideKeyboard();
        fillTextByXpath(patient.getConfirmPassword(), "//android.view.ViewGroup[@content-desc=\"main\"]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[3]/android.widget.EditText");
        hideKeyboard();
        WebElement confirmButton = androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"main\"]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[2]/android.widget.Button"));
        click(confirmButton);
    }

    private void agreeTermsAndCondition() {
        WebElement agreeCheckbox = androidDriver.findElement(AppiumBy.className("android.widget.CheckBox"));
        click(agreeCheckbox);
        WebElement confirmButton = androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"agree_btn\"]/android.view.ViewGroup/android.widget.Button/android.widget.TextView"));
        click(confirmButton);
    }

    public void goToTheMessagesAndClickOnJoinDiGA1() {
        clickOnMessagesBox();
        WebElement joinDiGA1Button = androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"last-bubble\"]/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.Button/android.widget.TextView"));
        click(joinDiGA1Button);
        waitFewSeconds(2000);
    }

    public void verifySecondMessage() {
        WebElement messagesTab = androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"chat\"]/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView[1]"));
        String messageText = messagesTab.getText();
        Assert.assertEquals(messageText, "Großartig! Wir haben Ihnen eine Einladung zur Teilnahme für DiGA1 geschickt, bitte prüfen Sie Ihre E-Mail.");
        clickOnBackButton();
    }

    private void clickOnBackButton() {
        WebElement backButton = androidDriver.findElement(AppiumBy.accessibilityId("dashboard, back"));
        click(backButton);
        waitFewSeconds(2000);
    }

    public void verifyDiGA1BoxIsAppearsInTheHomePage() {
        try {
            waitFewSeconds(2000);
            androidDriver.findElement(AppiumBy.accessibilityId("diga-box"));
        } catch (Exception e) {
            Assert.fail("DiGA1 box does not appear ");
        }
    }

    public void verifyThatAfterClickingOnTheDIGA1ButtonTheRequiredMessageIsAppeared() {
        clickOnDiGA1Box();
        Assert.assertEquals(androidDriver.findElements(AppiumBy.className("android.widget.TextView")).get(3).getText(), "Don't show me this anymore", "Message not as required");
        if (!androidDriver.findElements(AppiumBy.className("android.widget.TextView")).get(0).getText().contains("Clicking on the button below will navigate you to the DiGA1 application")) {
            Assert.fail("Message not as required");
        }
        try {
            androidDriver.findElement(AppiumBy.className("android.widget.Button"));
        } catch (Exception e) {
            Assert.fail("Message is not as required");
        }
    }

    public void verifyMessagesBadgeAppearsInTheHomePage() {
        try {
            androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"dashboard-nav-conversation\"]/android.view.ViewGroup[2]/android.widget.TextView"));
        } catch (Exception e) {
            Assert.fail("Messages badge do not appears");
        }
    }

    public void verifyMessagesBadgeIsDisappearsFromHomePage() {
        clickOnBackButton();
        try {
            androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"dashboard-nav-conversation\"]/android.view.ViewGroup[2]/android.widget.TextView"));
            Assert.fail("Messages badge should not appear");
        } catch (Exception e) {

        }
    }

    public void verifyMessageContent() {
        clickOnMessagesBox();
        String textContent = androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"chat\"]/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.TextView[1]")).getText();
        Assert.assertEquals(textContent, Generate.todayDate(), "Patient cannot see doctor's messages");
    }

    public void verifyTermsAndConditionsIsExistInTheProfileTab() {
        clickOnProfileTab();
        WebElement termsAndConditionsButton = androidDriver.findElement(AppiumBy.accessibilityId("term-button"));
        click(termsAndConditionsButton);
        try {
            WebElement closeButton = androidDriver.findElement(AppiumBy.accessibilityId("close-term-button"));
        } catch (Exception e) {
            Assert.fail("Close button does not appear");
        }
        WebElement titleText = androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"GenericModal\"]/android.view.ViewGroup/android.view.ViewGroup[1]/android.webkit.WebView/android.webkit.WebView/android.view.View[1]"));
        Assert.assertEquals(titleText.getText(), "Nutzungsbedingungen und Datenschutzerklärung");
    }

    private void clickOnProfileTab() {
        System.out.println("into profiletab");
        waitUntilVisibleByXpath("//android.widget.ImageView[@class='android.widget.ImageView' and @bounds='[961,155][1059,253]']");
        WebElement profileTab = androidDriver.findElement(AppiumBy.xpath("//android.widget.ImageView[@class='android.widget.ImageView' and @bounds='[961,155][1059,253]']"));
        System.out.println("got xpath profiletab");
        click(profileTab);
        System.out.println("cliked profiletab");
    }

    public void verifySurveysBadgeAppearsInTheHomePage() {
        try {
            androidDriver.findElement(AppiumBy.xpath("(//android.view.ViewGroup[@content-desc=\"dashboard-nav-dossier\"])[1]/android.view.ViewGroup[2]/android.widget.TextView"));
        } catch (Exception e) {
            Assert.fail("surveys badge do not appear");
        }
    }

    private void clickOnSurveysBox() {
        TouchAction action = new TouchAction(androidDriver);
        action.press(PointOption.point(555, 1430)).moveTo(PointOption.point(555, 1745)).release();
        action.perform();
        WebElement surveysBox = androidDriver.findElement(AppiumBy.accessibilityId("dashboard-nav-dossier"));
        click(surveysBox);
        waitFewSeconds(3000);
    }

    private void clickOnMessagesBox() {
        WebElement messagesBox = androidDriver.findElement(AppiumBy.accessibilityId("dashboard-nav-conversation"));
        click(messagesBox);
        waitFewSeconds(3000);
    }

    private void clickOnDiGA1Box() {
        WebElement DiGA1Box = androidDriver.findElement(AppiumBy.accessibilityId("diga-box"));
        click(DiGA1Box);
    }

    public void fillTheSurveyAndVerifyTheSurveyStatusIsChangedToComplete() {
        clickOnSurveysBox();
        WebElement firstSurvey = androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"main\"]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup"));
        click(firstSurvey);
        waitFewSeconds(5000);
        waitUntilVisibleByClassName("android.widget.Button");
        WebElement saveButton = androidDriver.findElements(AppiumBy.className("android.widget.Button")).get(1);
        scrollToTheBottomOfThePage();
        click(saveButton);
        waitUntilVisibleByXpath("//android.view.ViewGroup[@content-desc=\"main\"]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.widget.TextView[3]");
        waitFewSeconds(1000);
        WebElement surveyStatus = androidDriver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"main\"]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.widget.TextView[3]"));
        if (!surveyStatus.getText().contains("Status: Complete")) {
            Assert.fail("survey status is incorrect");
        }
    }

    public void verifySurveysBadgeIsDisappearsFromHomePage() {
        clickOnBackButton();
        try {
            androidDriver.findElement(AppiumBy.xpath("(//android.view.ViewGroup[@content-desc=\"dashboard-nav-dossier\"])[1]/android.view.ViewGroup[2]/android.widget.TextView"));
            Assert.fail("surveys badge should not appear");
        } catch (Exception e) {

        }
    }

    public void logoutFromTheApp() {
        clickOnProfileTab();
        waitUntilVisibleByXpath("//android.widget.Button[android.widget.TextView[@text='Logout']]");
        WebElement logoutButton = androidDriver.findElement(AppiumBy.xpath("//android.widget.Button[android.widget.TextView[@text='Logout']]"));
        System.out.println("find logout xpath");
        click(logoutButton);
        System.out.println("clicked on logout");
    }

    public void verifyThatCanLogoutSuccessfullyFromTheApp() {
        waitFewSeconds(2000);
        WebElement logoText = androidDriver.findElement(AppiumBy.className("android.widget.TextView"));
        if(!logoText.getText().equals("personal. health. digital")) {
            Assert.fail("The user should be logged out ");
        }
    }

}