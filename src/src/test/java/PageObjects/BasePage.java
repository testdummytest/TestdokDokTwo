package PageObjects;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import UiRegressionTests.loggersetup;
import java.util.logging.Logger;

public class BasePage {
    String tmpPassword;

    private static final Logger logger = loggersetup.getLogger();
    WebDriver driver;

    public BasePage(WebDriver driver) {
        super();
        this.driver = driver;
    }

    public void click(WebElement el) {
        Actions actions = new Actions(driver);
        actions.moveToElement(el);
        actions.perform();
        el.click();
        waitFewSeconds(2000);
    }

    public void moveToElement(WebElement el) {
        Actions actions = new Actions(driver);
        actions.moveToElement(el);
        actions.perform();
    }

    public void takesScreenshot() {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File("C:\\Projects\\headless_screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillTextByName(String text, String el) {
        WebElement element = driver.findElement(By.name(el));
        click(element);
        element.clear();
        element.sendKeys(text);
    }
    public void fillTextByNameForChild(String text, String el) {
        WebElement element = driver.findElement(By.name(el));
        click(element);
        element.clear();
        element.sendKeys(text);
    }

    public void fillTextById(String text, String el) {
        WebElement element = driver.findElement(By.id(el));
        click(element);
        element.clear();
        waitFewSeconds(1);
        element.sendKeys(text);
        waitFewSeconds(2);
    }

    public void fillTextByClassName(String text, String el) {
        WebElement element = driver.findElement(By.className(el));
        click(element);
        element.clear();
        element.sendKeys(text);
    }

    public String getText(WebElement el) {
        return el.getText();
    }

    public void clearFieldByName(String el) {
        WebElement element = driver.findElement(By.name(el));
        element.click();
        element.clear();
    }

    public void refreshPage() {
        driver.navigate().refresh();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void scrollToTheTopOfThePage() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, -document.body.scrollHeight)");
    }

    public void scrollToTheBottomOfThePage() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scroll)");
    }

    public void waitFewSeconds(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void openUrlInTheSameTab(String url) {
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t"); // Open tab 2 using CTRL + T keys.
        driver.get(url); // Open URL In 2nd tab
        waitFewSeconds(2000);
    }

    public void openUrlInNewTab(String url, int tabNumber) {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabNumber)); // switches to new tab
        driver.get(url);
        waitFewSeconds(3000);
    }

    public void moveToTab(int tabNumber) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabNumber));
    }

    public void logoutFromUser() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            logger.info("Sleep didn't work. ");
        }
        WebElement avatarButton = driver.findElement(By.id("avatar-button"));
        click(avatarButton);
        WebElement logoutButton = driver.findElement(By.id("logout-button"));
        click(logoutButton);
    }

    public void loginAsUser(String userName, String password) {
        fillTextById(userName, "username");
        fillTextById(password, "password");
        WebElement signInButton = driver.findElement(By.id("kc-login"));
        click(signInButton);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=\"Ok\"]")));
            WebElement OkButton = driver.findElement(By.xpath("//button[text()=\"Ok\"]"));
            click(OkButton);

        } catch (Exception e) {
            logger.info("Welcome not shown ");

        }
    }

    public void setNewPassword(String newPassword, String conformPassword) {
        fillTextById(newPassword, "password-new");
        fillTextById(conformPassword, "password-confirm");
        WebElement signInButton = driver.findElement(By.id("kc-login"));
        click(signInButton);
    }

    public String getSMSCodefromlist(){
        waitFewSeconds(3000);
        WebElement smscodeis = driver.findElement(By.xpath("//*[contains(text(),'get SMS')]"));
        click(smscodeis);
        logger.info("Clicked on get sms btn");
        waitFewSeconds(2000);
        driver.findElement(By.xpath("(//li[contains(text(),'for ')])[last()]")).click();
        logger.info("Clicked on last sms");
        WebElement iframe2 = driver.findElement(By.xpath("(//iframe[@class='smsPreview'])[last()]"));
        driver.switchTo().frame(iframe2);
        logger.info("Frame switched");
        String smsText = driver.findElement(By.xpath("(//body//div)[last()]")).getText();
        logger.info(smsText);
        String[] passwordTmp = smsText.split(":");
        tmpPassword = passwordTmp[passwordTmp.length -1].replaceAll("\\s", "");
        logger.info(tmpPassword);
        return tmpPassword;

    }

    public void setCodeReceivedToTheNewUser(String password) {
        logger.info(password);
        fillTextById(password, "password");
        WebElement signInButton = driver.findElement(By.id("kc-login"));
        click(signInButton);
    }

    public void agreeTermsAndConditions() {
        waitFewSeconds(3000);
        WebElement termsCheckbox = driver.findElement(By.id("terms-checkbox"));
        click(termsCheckbox);
        WebElement termsAcceptButton = driver.findElement(By.id("terms-accept-button"));
        click(termsAcceptButton);
        logger.info("Successfully Checked and accepted the Terms and conditions.");
        waitFewSeconds(2000);
    }

}
