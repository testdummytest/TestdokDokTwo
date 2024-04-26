package PageObjects;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
// import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import Framework.Generate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PatientHomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(PatientHomePage.class);
    public PatientHomePage(WebDriver driver) {
        super(driver);
    }

    public void verifyThatTheUserLoggedInSuccessfully() {
        agreeTermsAndConditions();
        try {
            driver.findElement(By.className("chat-content"));
            driver.findElement(By.id("chat-input"));
            driver.findElement(By.className("events"));
            waitFewSeconds(5000);
            logoutFromUser();
        } catch (Exception e) {
            Assert.fail("There is an issue in the patient home page, please check! ");
        }
    }

    public void respectiveScreenForPateint() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='Home'])[1]")));
        driver.findElement(By.xpath("(//span[text()=\"Home\"])[1]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()=\"Patients\"])[2]")));
        driver.findElement(By.xpath("(//span[text()=\"Patients\"])[2]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4//span[text()=\"Patients\"]")));
        assert driver.findElement(By.xpath("//h4//span[text()=\"Patients\"]")).isDisplayed();
        driver.findElement(By.xpath("(//span[text()=\"Home\"])[1]")).click();
        logger.debug("Checked Patient Page");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Messages\"])[2]")));
        driver.findElement(By.xpath("(//span[text()=\"Messages\"])[2]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()=\"Messages\"])[2]")));
        assert driver.findElement(By.xpath("(//span[text()=\"Messages\"])[2]")).isDisplayed();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Home\"])[1]")));
        driver.findElement(By.xpath("(//span[text()=\"Home\"])[1]")).click();
        logger.debug("Checked Messages Page");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Profile\"])[2]")));
        driver.findElement(By.xpath("(//span[text()=\"Profile\"])[2]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4//span[contains(text(),\"Welcome\")]")));
        assert driver.findElement(By.xpath("//h4//span[contains(text(),\"Welcome\")]")).isDisplayed();
        // waitFewSeconds(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Home\"])"))).click();
        // driver.findElement(By.xpath("(//span[text()=\"Home\"])[1]")).click();
        logger.debug("Checked Profile Page");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Appointments\"])[1]"))).click();
        // driver.findElement(By.xpath("(//span[text()=\"Appointments\"])[2]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Time']")));
        assert driver.findElement(By.xpath("//span[text()='Time']")).isDisplayed();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Home\"])[1]")));
        driver.findElement(By.xpath("(//span[text()=\"Home\"])[1]")).click();
        logger.debug("Checked Appointments Page");

    }

    public void RecentConversations() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()=\"Home\"])[1]")));
        driver.findElement(By.xpath("(//span[text()=\"Home\"])[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=\"Recent Conversations\"]")));
        assert driver.findElement(By.xpath("//span[text()=\"Recent Conversations\"]")).isDisplayed();
        logger.debug("Displayed Recent Conversations");

    }

    public void forgotpasswordbyPatient(Patient patient, Admin admin, Doctor doctor, String email) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//a[text()=\"Forgot Password?\"]//parent::span")));
        driver.findElement(By.xpath("//a[text()=\"Forgot Password?\"]//parent::span")).click();
        logger.info("Clicked on Forgot password link");
        driver.findElement(By.id("username")).sendKeys(email);
        driver.findElement(By.xpath("//button[text()=\"Submit\"]")).click();
        logger.debug("Click on Submit");
        loginAsUser(admin.getEmail(), admin.getPassword());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()=\"Patients\"])[1]")));
        driver.findElement(By.xpath("(//span[text()=\"Patients\"])[1]")).click();
        WebElement searchButton = driver.findElement(By.id("patient-search-bar-search-button"));
        click(searchButton);
        logger.debug("Click on search button");
        waitFewSeconds(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder,'Search')]")));
        driver.findElement(By.xpath("//input[contains(@placeholder,'Search')]")).sendKeys(email);
        waitFewSeconds(5000);
        driver.findElement(By.xpath("((//button[@data-test='rowMenuButton'])[1]//span)[1]")).click();
        logger.info("Click on Hamburgun button");
        driver.findElement(By.xpath("//span[text()=\"Show Keycloak/Docdok events\"]")).click();
        logger.info("Click on Show Keycloak/Docdok events button");
        waitFewSeconds(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()=\"SEND_RESET_PASSWORD\"])[1]")));
        assert driver.findElement(By.xpath("(//span[text()=\"SEND_RESET_PASSWORD\"])[1]")).isDisplayed();
        logger.info("Send reset password is displayed");

    }

    public void verifyPatientChatDisabled(Patient patient) {

        assert driver.findElement(By.xpath("//*[text()='This clinic has not enabled patient messaging']")).isDisplayed();
        logger.info("Patient's chat has been successfully disabled!!");
        logoutFromUser();
    }

    public void verifyPatientChatEnabled(Patient patient) {

        assert driver.findElement(By.xpath("(//*[text()='Conversation is read write for the patient'])[last()]")).isDisplayed();
        sendMessageToPatient();
    }

    public void sendMessageToPatient() {
        String sample_text = Generate.todayDate();
        fillTextById(sample_text, "chat-input");
        WebElement sendButton = driver.findElement(By.id("send-button"));
        click(sendButton);
        logger.info("sent msg to the doctor, Patient's chat has been successfully enabled!!");
        assert driver.findElement(By.xpath("//span//div[text()='"+sample_text+"']")).isDisplayed();
        logoutFromUser();
    }

}
