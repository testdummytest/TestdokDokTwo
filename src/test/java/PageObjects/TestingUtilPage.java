package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Entities.Admin;
import Framework.DataProviderClass;
import UiRegressionTests.loggersetup;
import java.util.logging.Logger;

public class TestingUtilPage extends BasePage {
    private static final Logger logger = loggersetup.getLogger();

    static String activationUrl;
    static String tmpPassword;

    public TestingUtilPage(WebDriver driver) {
        super(driver);
    }

    public String openActivationUrlByTestingUtil(Admin admin) {
        openTestingUtilPage();
        loginAsAdminInTestingUtil(admin);
        goToTheEmailsAndClickOnTheLastEmail();
        openUrlInNewTab(getActivationUrlForTheLastEmail() , 1);
        logoutFromUser();
        openUrlInTheSameTab(activationUrl);
        return tmpPassword;
    }

    public void openActivationUrlByTestingUtilForSMSAndEmail(Admin admin, String mobileNumber) {
        openTestingUtilPage();
        loginAsAdminInTestingUtil(admin);
        goToTheEmailsAndClickOnTheLastEmail();
        getActivationUrlForTheLastEmail_for_SMS();
        goToSMSAndClickOnLastSMS(mobileNumber);
        goToTheEmailsAndClickOnTheLastEmail();
        openUrlInNewTab(getActivationUrlForTheLastEmail() , 1);
        logoutFromUser();
        openUrlInTheSameTab(activationUrl);
    }

    public void openTestingUtilPage() {
        openUrlInTheSameTab("https://docdokutil.s3.eu-central-1.amazonaws.com/utilityTests.html");
    }

    private void loginAsAdminInTestingUtil(Admin admin) {
        DataProviderClass.getutitlityPageRadiobtnVal();
        String radioBtnId = DataProviderClass.radioBtnId; 
        WebElement envQA = driver.findElement(By.id(radioBtnId));
        click(envQA);
        loginAsUser(admin.getEmail(), admin.getPassword());
    }

    private void goToTheEmailsAndClickOnTheLastEmail() {
        waitFewSeconds(2000);
        WebElement getEmails = driver.findElement(By.xpath("//button[contains(text(), 'emails')]"));
        click(getEmails);
        waitFewSeconds(2000);
        WebElement emails = driver.findElement(By.id("emails"));
        Integer emailsSize = emails.findElements(By.tagName("li")).size();
        WebElement lastEmail = emails.findElements(By.tagName("li")).get(emailsSize - 1);
        click(lastEmail);

    }
    private void goToSMSAndClickOnLastSMS(String mobileNumber) {
        driver.navigate().refresh();
        waitFewSeconds(2000);
        WebElement getSMS = driver.findElement(By.xpath("//button[contains(text(), 'get SMS')]"));
        click(getSMS);
        logger.info(mobileNumber);
        driver.findElement(By.xpath("//li[contains(text(),'"+mobileNumber+"')]")).click();
        driver.navigate().refresh();

    }

    private String getActivationUrlForTheLastEmail() {
        WebElement emails = driver.findElement(By.id("emails"));
        Integer emailsSize = emails.findElements(By.tagName("li")).size();
        WebElement lastEmail = emails.findElements(By.tagName("li")).get(emailsSize - 1);
        WebElement iframe = lastEmail.findElement(By.tagName("iframe"));
        driver.switchTo().frame(iframe);
        DataProviderClass.getEnvUrl();
        String activateurl = DataProviderClass.activationUrlUtilityPage;
        activationUrl = driver.findElement(By.xpath(activateurl)).getAttribute("href");
        // activationUrl = driver.findElement(By.xpath("//a[contains(@href, 'https://qa.dev.docdok.ch/rest/user/api/users/onboarding/?token')]")).getAttribute("href");
        // driver.findElement(By.xpath("//a[contains(@href, 'https://qa.dev.docdok.ch/rest/user/api/users/onboarding/?token')]")).click();
        WebElement acceptBtn;
        try {
            acceptBtn = driver.findElement(By.xpath("//p[text()='Go to docdok']"));
        } catch (Exception e) {
            acceptBtn = driver.findElement(By.xpath("//span[text()='Accept Invitation']"));
        }
        acceptBtn.click();
        driver.switchTo().defaultContent();
        tmpPassword = getSMSCodefromlist();
        return activationUrl;
    }

    private String getActivationUrlForTheLastEmail_for_SMS() {
        WebElement emails = driver.findElement(By.id("emails"));
        Integer emailsSize = emails.findElements(By.tagName("li")).size();
        WebElement lastEmail = emails.findElements(By.tagName("li")).get(emailsSize - 1);
        WebElement iframe = lastEmail.findElement(By.tagName("iframe"));
        driver.switchTo().frame(iframe);
        DataProviderClass.getEnvUrl();
        String activateurl = DataProviderClass.activationUrlUtilityPage;
        activationUrl = driver.findElement(By.xpath(activateurl)).getAttribute("href");
        // activationUrl = driver.findElement(By.xpath("//a[contains(@href, 'https://qa.dev.docdok.ch/rest/user/api/users/onboarding/?token')]")).getAttribute("href");
        // driver.findElement(By.xpath("//a[contains(@href, 'https://qa.dev.docdok.ch/rest/user/api/users/onboarding/?token')]")).click();
        return activationUrl;
    }

}
