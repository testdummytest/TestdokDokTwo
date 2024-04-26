package PageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.Alert;

import Entities.Patient;
import Framework.DataProviderClass;
import Framework.Generate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DoctorHomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(DoctorHomePage.class);
    WebDriver webDriver;
    String email_id_of_patient;
    public String mobileNumber;
    public DoctorHomePage(WebDriver driver) {
        super(driver);
    }

    public void selectPatient(Patient patient) {
        clickOnPatientsTab();
        WebElement searchButton = driver.findElement(By.id("patient-search-bar-search-button"));
        click(searchButton);
        fillTextById(patient.getEmail(), "patient-search-bar-search-field");
        WebElement myPatients = driver.findElement(By.id("myPatients"));
        WebElement myPatient = myPatients.findElement(By.xpath(("//a[contains(@href, '/private/app/patients/PAT')]")));
        click(myPatient);
        }
    

    private void clickOnPatientsTab() {
        WebElement patientsTab = driver.findElement(By.id("menu.patients"));
        click(patientsTab);
    }

    public void addAnEventForTodayDate() {
        WebElement addButton = driver.findElement(By.id("add-events-button"));
        click(addButton);
        WebElement addEventButton = driver.findElement(By.id("add-events-particular-button"));
        click(addEventButton);
        fillEventData();
        WebElement saveButton = driver.findElement(By.id("save"));
        click(saveButton);
    }

    private void fillEventData() {
        fillTextById("automationTest", "editEvent-name");
        fillTextById("my event " + Generate.todayDate(), "editEvent-notes");
    }

    public void verifyThatTheEventAddedSuccessfully() {
        WebElement closeSuccessfulMessageButton = driver.findElement(By.className("jss57"));
        click(closeSuccessfulMessageButton);
        clickOnTheLastEvent();
        String notes = driver.findElement(By.id("editEvent-notes")).getText();
        Assert.assertEquals(notes, "my event " + Generate.todayDate());
    }

    public void updateEvent() {
        WebElement startTime = driver.findElement(By.name("startTime"));
        click(startTime);
        startTime.sendKeys(Keys.ARROW_UP);
        fillTextByName("myLocation", "location");
        fillTextById("my event " + Generate.dateForAppointment(1), "editEvent-notes");
    }

    public void verifyThatTheEventUpdatedSuccessfully() {
        String getStartTime = driver.findElement(By.name("startTime")).getAttribute("value");
        String getLocation = driver.findElement(By.name("location")).getAttribute("value");
        String getNotes = driver.findElement(By.id("editEvent-notes")).getText();

        WebElement saveButton = driver.findElement(By.id("save"));
        click(saveButton);
        WebElement closeSuccessfulMessageButton = driver.findElement(By.className("jss57"));
        click(closeSuccessfulMessageButton);
        clickOnTheLastEvent();

        Assert.assertEquals(getStartTime, driver.findElement(By.name("startTime")).getAttribute("value"));
        Assert.assertEquals(getLocation, "myLocation");
        Assert.assertEquals(getNotes, "my event " + Generate.dateForAppointment(1));

        WebElement closeEventButton = driver.findElement(By.id("close"));
        click(closeEventButton);
    }

    private void clickOnTheLastEvent() {
        WebElement today = driver.findElements(By.className("fc-day-today")).get(2);
        List<WebElement> events = today.findElements(By.className("fc-timegrid-event-harness-inset"));
        WebElement theLastEvent = events.get(events.size() - 1);
        click(theLastEvent);
    }

    public void deleteEventAndVerifyThatTheEventIsDeleted() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("fc-timegrid-event-harness-inset")));
        List<WebElement> events = driver.findElements(By.className("fc-timegrid-event-harness-inset"));
        Integer eventsSize = events.size();
        logger.info("Before delete:" + eventsSize);
        WebElement theLastEvent = events.get(events.size() - 1);
        click(theLastEvent);
        deleteEvent();
        waitFewSeconds(40);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("fc-timegrid-event-harness-inset")));
        List<WebElement> eventsAfterDeleteAnEvent = driver
                .findElements(By.className("fc-timegrid-event-harness-inset"));
        Integer eventsSizeAfterDeleteAnEvent = eventsAfterDeleteAnEvent.size();
        
        logger.info("After delete:" + eventsSizeAfterDeleteAnEvent);

        Assert.assertEquals(eventsSizeAfterDeleteAnEvent, eventsSize - 1, "The event does not deleted ! ");
        logger.info("Event is successfully deleted");
    }

    public void deleteEvent() {

        // Click on the span element using JavaScript
        String script = "var xpath = \"//span[text()='Delete']\";\n" +
                        "var matchingElement = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\n" +
                        "if (matchingElement) {\n" +
                        "    matchingElement.click();\n" +
                        "} else {\n" +
                        "    console.error('Element not found');\n" +
                        "}";
        ((JavascriptExecutor)driver).executeScript(script);

        // Wait for the alert to be present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        wait.until(ExpectedConditions.alertIsPresent());

        // Switch to the alert and accept it
        Alert alert = driver.switchTo().alert();
        waitFewSeconds(2000);
        alert.accept();
        waitFewSeconds(1000);
        driver.navigate().refresh();
        logger.info("Got the delete alert for an event and accepted successfully");
    }

    public void addAnEventInPastTime() {
        WebElement addButton = driver.findElement(By.id("add-events-button"));
        click(addButton);
        WebElement addEventButton = driver.findElement(By.id("add-events-particular-button"));
        click(addEventButton);
        fillTextById("automationTest", "editEvent-name");
        WebElement startTime = driver.findElement(By.name("startTime"));
        click(startTime);
        startTime.sendKeys(Keys.ARROW_DOWN);
        fillTextById("status done " + Generate.dateForAppointment(10), "editEvent-notes");
        WebElement saveButton = driver.findElement(By.id("save"));
        click(saveButton);
    }

    public void verifyEventStatus() {
        WebElement closeSuccessfulMessageButton = driver.findElement(By.className("jss57"));
        click(closeSuccessfulMessageButton);
        String eventColor = driver.findElement(By.className("fc-event-today")).getAttribute("style");
        Assert.assertEquals(eventColor, "background-color: rgb(255, 191, 131);", "event color is not correct! ");
        clickOnTheFirstEvent();
        String notes = driver.findElement(By.id("editEvent-notes")).getText();
        Assert.assertEquals(notes, "status done " + Generate.dateForAppointment(10));
        WebElement done = driver.findElement(By.id("DONE"));
        String status = done.findElement(By.className("jss62")).getAttribute("class");
        Integer length = status.length();
        if (length < 40) {
            Assert.fail("The event status should be Done! ");
        }
        // deleteEvent();
    }

    private void clickOnTheFirstEvent() {
        WebElement today = driver.findElements(By.className("fc-day-today")).get(2);
        WebElement event = today.findElement(By.className("fc-timegrid-event-harness-inset"));
        click(event);
    }

    public void verifyTheTheWeeklyViewIsAsExpected() {
        Integer daysNumber = driver.findElements(By.className("fc-daygrid-day-bottom")).size();
        Assert.assertEquals(daysNumber, 7);
    }

    public void verifyTheTheDailyViewIsAsExpected() {
        WebElement dayButton = driver.findElement(By.className("fc-timeGridDay-button"));
        click(dayButton);
        Integer daysNumber = driver.findElements(By.className("fc-daygrid-day-bottom")).size();
        Assert.assertEquals(daysNumber, 1);
    }

    public void verifyTheTheMonthlyViewIsAsExpected() {
        WebElement dayButton = driver.findElement(By.className("fc-dayGridMonth-button"));
        click(dayButton);
        Integer daysNumber = driver.findElements(By.className("fc-daygrid-day-bottom")).size();
        Assert.assertEquals(daysNumber, 42);
    }

    public void sendSurveyToThePatientAndVerifyTheSurveyWasSentSuccessfully() {
        WebElement surveysButton = driver.findElements(By.className("tab")).get(2);
        click(surveysButton);
        APIsPage apIsPage = new APIsPage(driver);
        int SurveysNumberBeforeAddingSurveyByCallingAPI = apIsPage.getSurveysNumber();
        String SurveysNumberBeforeAddingSurvey = getNumberOfSurveys();
        logger.info(SurveysNumberBeforeAddingSurvey);
        sendSurvey();
        String SurveysNumberAfterAddedSurvey = getNumberOfSurveys();
        int SurveysNumberAfterAddedSurveyByCallingAPI = apIsPage.getSurveysNumber();
        logger.info(SurveysNumberAfterAddedSurvey);
        Assert.assertNotEquals(SurveysNumberAfterAddedSurvey, SurveysNumberBeforeAddingSurvey);
        Assert.assertEquals(SurveysNumberBeforeAddingSurveyByCallingAPI + 1, SurveysNumberAfterAddedSurveyByCallingAPI);
    }

    public void sendSurveyToThePatient() {
        WebElement surveysButton = driver.findElements(By.className("tab")).get(2);
        click(surveysButton);
        sendSurvey();
    }

    private String getNumberOfSurveys() {
        WebElement element = driver.findElements(By.className("sc-fjdhpX")).get(2);
        String element2 = element.findElements(By.tagName("span")).get(3).getText();
        String numberOfSurveys = element2.substring(element2.lastIndexOf(' ') + 1);
        return numberOfSurveys;
    }

    private void sendSurvey() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        DataProviderClass.getProperties();
        WebElement sendSurveyButton = driver.findElement(By.id("send-survey-button"));
        click(sendSurveyButton);
        fillTextById(DataProviderClass.SurveyName, "sendSurveyFromPatient-surveys-search-field");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='"+DataProviderClass.SurveyName+"']/../../../..//div//input/..//.."))).click();
        WebElement submitButton = driver.findElement(By.id("save"));
        click(submitButton);
    }

    public void createAndDeletePatientCallbackFunc(Patient patient) {
        clickOnPatientsTab();
        clickOnCreatePatientButton();
        fillMandatoryFields(patient);
        clickOnSaveButton();
        try {
            Thread.sleep(3000);
            WebElement closeButton = driver.findElement(By.xpath("(//button[@id=\"close\"])[2]"));
            click(closeButton);
        } catch (Exception e) {
            logger.info("Error in clicking close/No thanks button");
        }
        email_id_of_patient = patient.getEmail();
        logger.info(email_id_of_patient);
        searchAndDeleteNonActivepatient();
    }

    public void searchAndDeleteNonActivepatient(){
        
        clickOnPatientsTab();
        try{
            WebElement searchButton = driver.findElement(By.id("patient-search-bar-search-button"));
            click(searchButton);
            fillTextById(email_id_of_patient, "patient-search-bar-search-field");
            Thread.sleep(3000);

            driver.findElement(By.xpath("(//button[@aria-label='More'])[1]")).click();
            logger.info("Cliked on More svg menu opens");
            waitFewSeconds(3000);

            driver.findElement(By.xpath("//*[text()='Delete Patient']")).click();
            waitFewSeconds(2000);
            
            String actualMessage = driver.findElement(By.id("infopanel")).getText();
            String expectedMessage = "Patient deleted successfully";
            Assert.assertEquals(actualMessage, expectedMessage);
            logger.info("Sucessfully deleted the Non active patient !!");

        } catch(Exception e){
            logger.error("Error in deleting non active patient!!");
        }
        
    }

    public void createPatientByTheDoctor(Patient patient) {
        clickOnPatientsTab();
        clickOnCreatePatientButton();
        fillMandatoryFields(patient);
        clickOnSaveButton();
        try {
            Thread.sleep(3000);
            WebElement closeButton = driver.findElement(By.xpath("(//button[@id=\"close\"])[2]"));
            click(closeButton);
        } catch (Exception e) {
            logger.debug("Error in clicking close button or button no shows");
        }
        
        logoutFromUser();
    }

    public void createPatientByHyCareDoctor(Patient patient) {
        clickOnPatientsTab();
        clickOnCreatePatientButton();
        fillMandatoryHyCareFields(patient);
        // chooseIcdCode();
        clickOnSaveButton();
        try {
            Thread.sleep(3000);
            WebElement closeButton = driver.findElement(By.xpath("(//button[@id=\"close\"])[2]"));
            click(closeButton);
        } catch (Exception e) {
            logger.debug("Error in clicking close/No thanks button");
        }

        logoutFromUser();
    }

    public void createPatientByHyCareDoctorForMobile(Patient patient) {
        clickOnPatientsTab();
        clickOnCreatePatientButton();
        fillMandatoryHyCareFields(patient);
        chooseIcdCode();
        clickOnSaveButton();
    }

    private void chooseIcdCode() {
        WebElement createOnPEPButton = driver.findElement(By.id("createPatient-pepIntegration"));
        click(createOnPEPButton);
        WebElement sibling = driver.findElement(By.id("createPatient-disease"));
        WebElement selectDisease = sibling.findElement(By.xpath("preceding-sibling::div[1]"));
        click(selectDisease);
        WebElement icdCode = driver.findElement(By.tagName("li"));
        click(icdCode);
    }

    private void fillMandatoryHyCareFields(Patient patient) {
        fillTextById("Mrs.", "createPatient-salutation");
        setFirstName(patient.getFirstName());
        setLastName(patient.getLastName());
        setGender();
        setBirthdate(patient.getBirthDate());
        setMobileNumber(patient.getMobileNumber());
        setEmail(patient.getEmail());
        setLanguage();
    }

    private void clickOnSaveButton() {
        WebElement saveButton = driver.findElement(By.id("save"));
        click(saveButton);
        waitFewSeconds(2000);
    }

    private void clickOnCreatePatientButton() {
        waitFewSeconds(5000);
        WebElement dayButton = driver.findElement(By.id("createPatient-table"));
        click(dayButton);
    }

    public void fillMandatoryFields(Patient patient) {
        setFirstName(patient.getFirstName());
        setLastName(patient.getLastName());
        setGender();
        setBirthdate(patient.getBirthDate());
        mobileNumber = patient.getMobileNumber();
        logger.info(mobileNumber);
        setMobileNumber(mobileNumber);
        setEmail(patient.getEmail());
        setLanguage();
    }

    private void setLanguage() {
        WebElement languageButton = driver.findElement(By.id("createPatient-langKey"));
        WebElement language = languageButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(language);
        WebElement deButton = driver.findElement(By.id("en"));
        click(deButton);
    }

    private void setGender() {
        WebElement genderButton = driver.findElement(By.id("createPatient-gender"));
        WebElement gender = genderButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(gender);
        WebElement femaleButton = driver.findElement(By.id("FEMALE"));
        click(femaleButton);
    }

    private void setBirthdate(String birthdate) {
        fillTextById(birthdate, "createPatient-birthdate");
    }

    private void setMobileNumber(String mobileNumber) {
        fillTextById(mobileNumber, "createPatient-mobileNumber");
    }

    private void setEmail(String email) {
        fillTextById(email, "createPatient-email");
    }

    private void setLastName(String lastName) {
        fillTextByName(lastName, "lastName");
    }

    private void setFirstName(String firstName) {
        fillTextByName(firstName, "firstName");
    }

    public void sendMessageToPatient() {
        String sample_text = Generate.todayDate();
        fillTextById(sample_text, "chat-input");
        WebElement sendButton = driver.findElement(By.id("send-button"));
        click(sendButton);
        assert driver.findElement(By.xpath("//span//div[text()='"+sample_text+"']")).isDisplayed();

    }

    public void sendVasSurveyToThePatient() {
        WebElement surveysButton = driver.findElements(By.className("tab")).get(2);
        click(surveysButton);
        WebElement sendSurveyButton = driver.findElement(By.id("send-survey-button"));
        click(sendSurveyButton);
        fillTextById("vas 12 ", "sendSurveyFromPatient-surveys-search-field");
        waitFewSeconds(2000);
        WebElement agreeCheckbox = driver.findElement(By.cssSelector("input[type='checkbox']"));
        click(agreeCheckbox);
        WebElement submitButton = driver.findElement(By.id("save"));
        click(submitButton);
    }

    public void respectiveScreenForDoctor() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

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

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Catalog\"])")));
        driver.findElement(By.xpath("(//span[text()=\"Catalog\"])")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4//span[text()=\"Catalog\"]")));
        assert driver.findElement(By.xpath("//h4//span[text()=\"Catalog\"]")).isDisplayed();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Home\"])[1]")));
        driver.findElement(By.xpath("(//span[text()=\"Home\"])[1]")).click();
        logger.debug("Checked Catalog Page");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Surveys\"])")));
        driver.findElement(By.xpath("(//span[text()=\"Surveys\"])")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4//span[text()=\"Surveys\"]")));
        assert driver.findElement(By.xpath("//h4//span[text()=\"Surveys\"]")).isDisplayed();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Home\"])[1]")));
        driver.findElement(By.xpath("(//span[text()=\"Home\"])[1]")).click();
        logger.debug("Checked Surveys Page");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Profile\"])")));
        driver.findElement(By.xpath("(//span[text()=\"Profile\"])")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4//span[contains(text(),\"Welcome\")]")));
        assert driver.findElement(By.xpath("//h4//span[contains(text(),\"Welcome\")]")).isDisplayed();
        waitFewSeconds(3000);
        driver.findElement(By.xpath("(//span[text()=\"Home\"])[1]")).click();
        logger.debug("Checked Profile Page");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Team\"])")));
        driver.findElement(By.xpath("(//span[text()=\"Team\"])")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4//span[text()=\"Team\"]")));
        assert driver.findElement(By.xpath("//h4//span[text()=\"Team\"]")).isDisplayed();
        waitFewSeconds(3000);
        driver.findElement(By.xpath("(//span[text()=\"Home\"])[1]")).click();
        logger.debug("Checked Team Page");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Studies\"])")));
        driver.findElement(By.xpath("(//span[text()=\"Studies\"])")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4//span[text()=\"Studies\"]")));
        assert driver.findElement(By.xpath("//h4//span[text()=\"Studies\"]")).isDisplayed();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()=\"Home\"])[1]")));
        driver.findElement(By.xpath("(//span[text()=\"Home\"])[1]")).click();
        logger.debug("Checked Studies Page");

        logger.info("Tapping on each feature navigated to respective screens.");

    }

    public String createPatientByTheDoctorFetchEmailString(Patient patient) {
        clickOnPatientsTab();
        clickOnCreatePatientButton();
        String x = fillMandatoryFieldsFetchEmailString(patient);
        clickOnSaveButton();
        try {
            Thread.sleep(3000);
            WebElement closeButton = driver.findElement(By.xpath("(//button[@id=\"close\"])[2]"));
            click(closeButton);
        } catch (Exception e) {
            logger.error("Error in clicking close button or button no shows");
        }
        logoutFromUser();
        return x;
    }

    private String fillMandatoryFieldsFetchEmailString(Patient patient) {
        setFirstName(patient.getFirstName());
        setLastName(patient.getLastName());
        setGender();
        setBirthdate(patient.getBirthDate());
        setMobileNumber(patient.getMobileNumber());
        String x = patient.getEmail();
        setEmail(x);
        setLanguage();
        return x;
    }

    public String createPatientByTheDoctorFetchMobileNumberString(Patient patient) {
        clickOnPatientsTab();
        clickOnCreatePatientButton();
        String x = fillMandatoryFieldsFetchMobileNumberString(patient);
        clickOnSaveButton();
        try {
            Thread.sleep(3000);
            WebElement closeButton = driver.findElement(By.xpath("(//button[@id=\"close\"])[2]"));
            click(closeButton);
        } catch (Exception e) {
            logger.error("Error in clicking close button or button no shows");
        }
        logoutFromUser();
        return x;
    }

    private String fillMandatoryFieldsFetchMobileNumberString(Patient patient) {
        setFirstName(patient.getFirstName());
        setLastName(patient.getLastName());
        setGender();
        setBirthdate(patient.getBirthDate());
        String x = patient.getMobileNumber();
        setMobileNumber(x);
        setEmail(patient.getEmail());
        setLanguage();
        return x;
    }
    private void setChildCheckboxCheck() {
        //check the Checkbox
        WebElement checkingCheckbox = driver.findElement(By.xpath("//input[@id='createPatient-isDependent']"));
        checkingCheckbox.click();
    }
    private void setChildSalutation(String lastChildSal) {
        fillTextById(lastChildSal, "createPatient-proxySalutation");
    }

    private void setChildMobileNumber(String childMobbNo) {
        fillTextByNameForChild(childMobbNo, "proxyMobileNumber");
    }


    private void setChildEmail(String childemailis) {
        fillTextByNameForChild(childemailis, "proxyEmail");
    }

    private void setChildFirstName(String firstChildName) {
        fillTextByNameForChild(firstChildName, "proxyFirstName");
    }

    private void setChildLastName(String lastChildName) {
        fillTextByNameForChild(lastChildName, "proxyLastName");
    }
    private void setChildPatientGender() {
        WebElement genderButton = driver.findElement(By.id("createPatient-proxyGender"));
        WebElement gender = genderButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(gender);
        WebElement femaleButton = driver.findElement(By.id("FEMALE"));
        click(femaleButton);
    }
    private void setChildLanguage() {
        WebElement languageButton = driver.findElement(By.id("createPatient-langKey"));
        WebElement language = languageButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(language);
        WebElement deButton = driver.findElement(By.id("en"));
        click(deButton);
    }//end
    private void fillMandatoryFieldsForChildPatient(Patient patient) {

        setChildCheckboxCheck(); //for a checkbox check
        setFirstName(patient.getFirstName());
        setLastName(patient.getLastName());
        setGender();
        setBirthdate(patient.getBirthDate());

        setChildSalutation(patient.getChildSalutation());
        setChildFirstName(patient.getChildFirstName());
        setChildLastName(patient.getChildLastName());
        setChildMobileNumber(patient.getChildMobileNumber());
        setChildEmail(patient.getChildEmail());
        setChildPatientGender();
        setChildLanguage();
        logger.info("Modify docorhomepage complete.");

    }

     public void createChildPatientByTheDoctor(Patient patient) {
        clickOnPatientsTab();
        clickOnCreatePatientButton();
        fillMandatoryFieldsForChildPatient(patient);
        clickOnSaveButton();
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // wait.until(ExpectedConditions.elementToBeClickable(By.id("close")));
        try {
            Thread.sleep(3000);
            WebElement closeButton = driver.findElement(By.xpath("(//button[@id=\"close\"])[2]"));
            click(closeButton);
        } catch (Exception e) {
            logger.error("Error in clicking close/No thanks button");
        }
        logoutFromUser();
    }

    public void verifyThatThePhysicianDoctorLoggedInSuccessfully() {
        //agreeTermsAndConditions();
        logger.info("WELCOME TO PHYSSICIAN HOMEPAGE");
        waitFewSeconds(5000);
        try {
            driver.findElement(By.xpath("//*[text()='assignment']"));
            logger.info("Physician verification is successfull");
        } catch (Exception e) {
            Assert.fail("There is an issue in the physician doctor home page, please check! ");
        }
    }
    public void verifyThatTheDocAssistLoggedInSuccessfully() {
        logger.info("WELCOME TO Doc Assistant HOMEPAGE");
        waitFewSeconds(5000);

        try {
            // driver.findElement(By.xpath("//*[text()='assignment']"));
            driver.findElement(By.xpath("//*[text()='group_work_item']"));
        } catch (Exception e) {
            Assert.fail("There is an issue in the doctor home page, please check! ");
        }
    }

    public void sendAppInvitationToPatientByDoctor(Patient patient) {
        clickOnPatientsTab();
        sendInvitationToPatient();
        // logoutFromUser();
    }

    private void sendInvitationToPatient() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        driver.findElement(By.xpath("//*[text()='App Activated']")).click();
        logger.info("Clicked on App activated filter");
        waitFewSeconds(4000);
        try{
            //find and store activatedstatus
            WebElement chkingActivatedStatus = driver.findElement(By.xpath("//*[contains(@d,'M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z')]"));
            //check for ACTIVATED STATUS OR NOT[true sign]
            if(chkingActivatedStatus.isDisplayed()){
                // Assert.fail("Activated Status find..So not Send App Invitation to PATIENT.");
                logger.info("Activated Status found. Not sending App Invitation to PATIENT.");
            }
        }
        catch(Exception e){
            logger.info("Verified that this page has not any ACTIVATED STATUS for a paient");
            //click on more svg
            driver.findElement(By.xpath("(//button[@aria-label='More'])[1]")).click();
            logger.info("Cliked on More svg menu opens");
            // waitFewSeconds(3000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Renew invitation']")));
            driver.findElement(By.xpath("//*[text()='Renew invitation']")).click();
            waitFewSeconds(2000);
            logger.debug("Cliked on renew invitation btn..");
            try{
                logger.info("Enter in try block");

                waitFewSeconds(4000);
                //verify msg: Invitation Email was sent again.
                WebElement ele = driver.findElement(By.xpath("//*[text()='Invitation Email was sent again.']"));
                String ActualTitle = ele.getText();
                String ExpectedTitle = "Invitation Email was sent again.";
                // waitFewSeconds(2000);
                Assert.assertEquals(ExpectedTitle, ActualTitle);
                logger.info("Invitation App Message is successfully verified");
                // waitFewSeconds(5000);
            }catch(Exception exe){
                Assert.fail("Titles do not match");
            }

        }

    }

    public void checkAdminNotSendAppInvitationToAppActivePatient(Patient patient) {
        clickOnPatientsTab();
        searchPatientAndAppActivatedOrNotCheck();
    }

    public void searchPatientAndAppActivatedOrNotCheck(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement searchButton = driver.findElement(By.id("patient-search-bar-search-button"));
        waitFewSeconds(2000);
        click(searchButton);
        DataProviderClass.getProperties(); // Load properties
        fillTextById(DataProviderClass.PatientUname, "patient-search-bar-search-field");
        waitFewSeconds(4000);
        //find and store activatedstatus
        WebElement chkingActivatedStatus = driver.findElement(By.xpath("//*[contains(@d,'M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z')]"));
        if(chkingActivatedStatus.isDisplayed()){
            driver.findElement(By.xpath("(//button[@aria-label='More'])[1]")).click();
            // waitFewSeconds(3000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@data-test='renewInvitation-action']")));
            WebElement renewInvitationElement = driver.findElement(By.xpath("//li[@data-test='renewInvitation-action']"));
            String tabIndexAttribute = renewInvitationElement.getAttribute("tabindex");
            boolean isDisabled = tabIndexAttribute != null && tabIndexAttribute.equals("-1");
            if (isDisabled) {
                logger.info("Passed: App is Activated, Renew invitation element is disabled.");
            } else {
                Assert.fail("App is Not activated, Renew invitation element is Not disabled!!");
            }
        }
        else{
            Assert.fail("App is Not activated, Renew invitation element is Not disabled!!");
        }
    }



    public void UserProfileEditAndCancelEdit(Patient patient) {
        selectPrimaryUserForProfileEdit();
        EditPrimaryCancelProfile();
    }

    public void selectPrimaryUserForProfileEdit(){

        //click on shoe more arrow btn
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@aria-label,'Show more')])[1]"))).click();
        waitFewSeconds(4000);
    }

    public void EditPrimaryCancelProfile(){
        WebElement contactEditpath = driver.findElement(By.xpath("//span[text()='Edit']"));
        click(contactEditpath); 
        logger.info("Edit btn clicked");
        //used to scroll up, use because element not seen and clickable
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, -500);");
		//for primary data
        //adding paths for a edit salutation:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement primaryEditSalutation = driver.findElement(By.xpath("//input[@id='patientData-salutation']"));
        wait.until(ExpectedConditions.elementToBeClickable(primaryEditSalutation));
        primaryEditSalutation.click();
        waitFewSeconds(2000);
        //to clear the existing code in field
        int lengthSlautation = primaryEditSalutation.getAttribute("value").length();
        for (int i = 0; i < lengthSlautation; i++) {
            primaryEditSalutation.sendKeys(Keys.BACK_SPACE);
        }//end
        primaryEditSalutation.sendKeys("Patient");
        logger.debug("salutation done");
        //adding paths for a edit firstname:
        WebElement primaryEditfirstname = driver.findElement(By.xpath("//input[@name='firstName']"));
        primaryEditfirstname.click();
        waitFewSeconds(1000);
        primaryEditfirstname.clear();
        primaryEditfirstname.sendKeys("Automation-"+DataProviderClass.getRandomString());
        waitFewSeconds(1000);
        logger.debug("firstname done");
		
        //adding paths for a edit lastname:
        WebElement primaryEditLastName = driver.findElement(By.xpath("//input[@name='lastName']"));
        primaryEditLastName.click();
        waitFewSeconds(1000);
        primaryEditLastName.clear();
        primaryEditLastName.sendKeys("LastName-"+DataProviderClass.getRandomString());
        waitFewSeconds(1000);
        logger.debug("lastname done");
		
        //adding paths for a edit birthdate:
        WebElement primaryEditBirthDate = driver.findElement(By.xpath("//input[@id='patientData-birthdate']"));
        primaryEditBirthDate.click();
        waitFewSeconds(1000);
        primaryEditBirthDate.clear();
        primaryEditBirthDate.sendKeys("20/01/1998");
        waitFewSeconds(2000);
        logger.debug("birthday done");

        JavascriptExecutor jsdown = (JavascriptExecutor) driver;
        jsdown.executeScript("window.scrollBy(0, 200);");

        WebElement clickGenderDropdown = driver.findElement(By.xpath("//div[@aria-pressed='false' and @role='button']"));
        waitFewSeconds(2000);
        clickGenderDropdown.click();
        waitFewSeconds(2000);
        clickGenderDropdown.click();
        logger.debug("clicked dropdown");
        waitFewSeconds(2000);
        WebElement current_selected_gender_element = driver.findElement(By.xpath("//input[@name='gender']"));
        String genderVal = current_selected_gender_element.getAttribute("value");
        logger.debug("gender value: " + genderVal);

        String genderxpath;
        if ("MALE".equals(genderVal)) {
            genderxpath = "//span[text()='Female']";
            logger.debug("first..");
        } else if("FEMALE".equals(genderVal)) {
            genderxpath = "//span[text()='Male']";
            logger.debug("second");
        } else{
            genderxpath = "//span[text()='Male']";
            logger.debug("third");
        }

        logger.debug("After update val:" + genderxpath);

        WebElement selectDropdownOption = driver.findElement(By.xpath(genderxpath));
        waitFewSeconds(4000);
        logger.debug("selected option:" +selectDropdownOption);
        selectDropdownOption.click();

        logger.debug("clicked option");

        //adding paths for a edit socialsecurity no/insurance no:
        WebElement primaryEditSocialSecurityNo = driver.findElement(By.xpath("//input[@name='insuranceNumber']"));
        primaryEditSocialSecurityNo.click();
        waitFewSeconds(1000);
        primaryEditSocialSecurityNo.clear();
        primaryEditSocialSecurityNo.sendKeys(DataProviderClass.getRandomMobileNumber());
        waitFewSeconds(1000);
        logger.debug("insurance no done");
        //adding paths for a edit patient no:
        WebElement primaryEditPatientNo = driver.findElement(By.xpath("//input[@name='lifelongId']"));
        primaryEditPatientNo.click();
        waitFewSeconds(1000);
        primaryEditPatientNo.clear();
        primaryEditPatientNo.sendKeys(DataProviderClass.getRandomMobileNumber());
        waitFewSeconds(1000);
        logger.debug("patient num done");
        WebElement contactFieldSave = driver.findElement(By.xpath("//button[@id='save']"));
        click(contactFieldSave);
        logger.debug("Successfully saved primary fields..");
        waitFewSeconds(4000);
        //cancel field process for primary fields
        WebElement contactEditpathAgain = driver.findElement(By.xpath("//span[text()='Edit']"));
        click(contactEditpathAgain); 
        waitFewSeconds(1000);
        WebElement primaryEditfirstnameAgain = driver.findElement(By.xpath("//input[@name='firstName']"));
        // primaryEditfirstnameAgain.click();
        click(primaryEditfirstnameAgain);
        waitFewSeconds(1000);
        primaryEditfirstnameAgain.clear();
        primaryEditfirstnameAgain.sendKeys("AGAIN Updated First Name");
        waitFewSeconds(1000);
        WebElement cancelBtnpath = driver.findElement(By.xpath("//span[text()='Cancel']"));
        waitFewSeconds(3000);
        cancelBtnpath.click();
        logger.debug("Cancel btn click for primary fields success");
        waitFewSeconds(6000);

    }


    //working properly for edit/cancel contactDetail user profile
    public void EditContactDetailsCancelProfile(){

        //edit and save process start
        //used to scroll up, use because element not seen and clickable
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, -600);");
        //end
        WebElement contactDetailsEditpath = driver.findElement(By.xpath("//span[text()='Edit']"));
        // waitFewSeconds(2000);

        click(contactDetailsEditpath); 
        logger.debug("Edit btn clicked");

        //adding paths for a edit mobno:
        WebElement contactEditMobileno = driver.findElement(By.xpath("//input[@name='mobileNumber']"));
        
        contactEditMobileno.click();
        waitFewSeconds(1000);
        contactEditMobileno.clear();
        // update dynamic mob here
        contactEditMobileno.sendKeys(DataProviderClass.randomNumberGetWithPlusFormat());
        waitFewSeconds(1000);
        logger.debug("mobileno updated");
        waitFewSeconds(1000);
        WebElement contactFieldSave = driver.findElement(By.xpath("//button[@id='save']"));
        click(contactFieldSave);
        waitFewSeconds(6000);
        logger.debug("Saved successfully");
        //used to scroll up, use because element not seen and clickable
        JavascriptExecutor jsupnew = (JavascriptExecutor) driver;
        jsupnew.executeScript("window.scrollBy(0, -500);");
        //end
        logger.debug("Again scroll up");
        waitFewSeconds(6000);
        //end edit and save profile process

        //for cancel process start
        //edit btn click
        WebElement contactDetailsEditpathTwo = driver.findElement(By.xpath("//span[text()='Edit']"));
        logger.debug("Secondtime find xath of edit");
        click(contactDetailsEditpathTwo); 
        logger.debug("for Cancel process Edit btn clicked");
        WebElement contactEditEmailTwo = driver.findElement(By.xpath("//input[@name='email']"));
        contactEditEmailTwo.click();
        waitFewSeconds(1000);
        contactEditEmailTwo.clear();
        contactEditEmailTwo.sendKeys("updatedemailThreeNewCancel@gmail.com");
        waitFewSeconds(2000);
        logger.debug("Cancel email field updated");
        //target cancel btn click
        //end cancel process
        WebElement cancelBtnpath = driver.findElement(By.xpath("//span[text()='Cancel']"));
        waitFewSeconds(3000);
        cancelBtnpath.click();
        logger.debug("Cancel btn click success");
        waitFewSeconds(4000);

    }

    public void sendCatalogToPatientByDoctor(Patient patient, String catalogName){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='attachment-button']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Catalog']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='user-search-bar-search-button']"))).click();
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='user-search-bar-search-button']"))).sendKeys("Wege zum Lungensport");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='user-search-bar-search-field']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='user-search-bar-search-field']"))).sendKeys(catalogName);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='"+catalogName+"']/../../../..//div//input/../.."))).click();
        logger.debug("Selected checkbox");
        WebElement submitButton = driver.findElement(By.xpath("//span[text()='Send']"));
        click(submitButton);
        logger.info("Clicked on Send button");
        logoutFromUser();
    }


}
