package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Entities.Admin;
import Framework.DataProviderClass;

public class TestingUtilPage extends BasePage {

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

}
