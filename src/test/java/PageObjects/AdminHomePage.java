package PageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Entities.Admin;
import Entities.Patient;

import UiRegressionTests.loggersetup;
import java.util.logging.Logger;

public class AdminHomePage extends BasePage{

    private static final Logger logger = loggersetup.getLogger();
    
    WebDriver webDriver;

    public AdminHomePage(WebDriver driver) {
        super(driver);
    }


    public void createAssistantByTheAdmin(Admin admin) {

        //click on the teambox
        clickOnTeamBoxAtHomePage();
        clickOnDoctorAssistantCreateBtn();

        fillMandatoryFieldsForMPA(admin);

        clickOnSaveButton();
        
        logoutFromUser();
    }

    public void createDoctorPhysicianByTheAdmin(Admin admin) {

        //click on the teambox
        clickOnTeamBoxAtHomePage();
        clickOnPhysicianCreateBtn();
        fillMandatoryFieldsForDoctorPhysician(admin);
        clickOnSaveButton();
        logoutFromUser();
        logger.info("SUCCESSFULLY CREATED PHYSICIAN AND LOGGED OUT FROM ADMIN");
    }


    public void createClinicByDoctor(Admin admin) {

        //click on the teambox
        clickOnClinicsPageSideTab();
        clickOnCreateClinicBtn();

        fillFieldsForClinic(admin);

        logger.info("Successfully filled the clinic name..");

        clickOnSaveButton();
        driver.findElement(By.xpath("//span[text()=\"Clinic successfully created\"]"));
        logoutFromUser();
        logger.info("SUCCESSFULLY CREATED CLINIC AND LOGGED OUT FROM ADMIN");
    }

    public void clickOnTeamBoxAtHomePage() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[text()='group_work_item']")).click();
            Thread.sleep(2000);
            //logger.info("Success clicked on team..");
        } catch (Exception e) {
            logger.info("Error in clicking Team btn");
        }
    }


    public void clickOnClinicsPageSideTab() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("(//*[text()='Clinics'])[1]")).click();
            Thread.sleep(1000);
            logger.info("CLINIC clicked");
        } catch (Exception e) {
            logger.info("Error in clicking clinic side tab page");
        }
    }


    public void clickOnDoctorAssistantCreateBtn() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//span[@class='jss215'])[1]")).click();
            //logger.info("Success clicked on assistant btn..");
            Thread.sleep(4000);
        }catch(Exception e){
            logger.info("Error OPENING POPUP");
        }
    }


    public void clickOnPhysicianCreateBtn() {
        logger.info("called physician click method");
        try {
            Thread.sleep(5000);
            driver.findElement(By.xpath("//span[text()='Create Physician']")).click();
            //logger.info("Success clicked on physician btn..");
            Thread.sleep(5000);
        }catch(Exception e){
            logger.info("Error OPENING POPUP");
        }
    }



    public void clickOnCreateClinicBtn() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//*[text()='Create Clinic']")).click();
            logger.info("Success CLINIC popup opens");
            Thread.sleep(3000);
        }catch(Exception e){
            logger.info("Error OPENING POPUP");
        }
    }    


    private void fillMandatoryFieldsForMPA(Admin admin) {

        setChildSalutation(admin.getChildSalutation());

        //fname
        setChildFirstName(admin.getChildFirstName());
        setChildLastName(admin.getChildLastName());
        setChildPatientGender();
        setChildMobileNumber(admin.getChildMobileNumber());
        setChildEmail(admin.getChildEmail());
        setChildLanguage();
        setClinicdocassistant();
    }

    private void fillMandatoryFieldsForDoctorPhysician(Admin admin) {

        setPhysicianSalutation(admin.getChildSalutation());

        //fname
        setPhysicianFirstName(admin.getChildFirstName());
        setPhysicianLastName(admin.getChildLastName());
        setPhysicianGender();
        setPhysicianMobileNumber(admin.getChildMobileNumber());
        setPhysicianEmail(admin.getChildEmail());
        setPhysicianLanguage();
        setClinicPhysician();
    }


    private void fillFieldsForClinic(Admin admin) {

        // setClinicName(admin.getFirstName());
        String x = admin.getFirstName();
        logger.info(x);
        setClinicName(x);
        setClinicMobileNumber(admin.getMobileNumber());
        setClinicStreet(admin.getStreet());
       
        setClinicStreetNumber(admin.getClinicStreetNumber());
        setClinicity(admin.getClinicCity());
        setClinicZipcode(admin.getClinicZipcode());

    }




    private void setChildSalutation(String lastChildSal) {
        fillTextById(lastChildSal, "createMpa-salutation");
        logger.info("called mpa salutation set");
    }

    private void setPhysicianSalutation(String lastChildSal) {
        fillTextById(lastChildSal, "createPhysician-salutation");
        logger.info("called physician salutation set");
    }

    //set the proxyfirstname
    private void setChildFirstName(String firstChildName) {
        fillTextByNameForChild(firstChildName, "firstName");
    }

    //set the physician fname
    private void setPhysicianFirstName(String firstChildName) {
        fillTextByNameForChild(firstChildName, "firstName");
    }

    //set the clinic name
    private void setClinicName(String firstChildName) {
        fillTextByNameForChild(firstChildName, "name");
    }


    //set the proxyfirstname
    private void setChildLastName(String lastChildName) {
        fillTextByNameForChild(lastChildName, "lastName");
    }
    //set the physician lname
    private void setPhysicianLastName(String lastChildName) {
        fillTextByNameForChild(lastChildName, "lastName");
    }

    private void setChildPatientGender() {
        WebElement genderButton = driver.findElement(By.id("createMpa-gender"));
        WebElement gender = genderButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(gender);
        WebElement femaleButton = driver.findElement(By.id("FEMALE"));
        click(femaleButton);
    }

    private void setPhysicianGender() {
        WebElement genderButton = driver.findElement(By.id("createPhysician-gender"));
        WebElement gender = genderButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(gender);
        WebElement femaleButton = driver.findElement(By.id("FEMALE"));
        click(femaleButton);
    }

    //set the mobile no
    private void setChildMobileNumber(String childMobbNo) {
        fillTextByNameForChild(childMobbNo, "mobileNumber");
    }//end

    //set the mobile no
    private void setClinicMobileNumber(String childMobbNo) {
        fillTextByNameForChild(childMobbNo, "phoneNumber");
    }//end

    //set the mobile no
    private void setClinicStreet(String clinicStreetName) {
        fillTextByNameForChild(clinicStreetName, "street");
    }//end


    //set StreetNumber
    private void setClinicStreetNumber(String clinicStreetNo) {
        fillTextByNameForChild(clinicStreetNo, "streetNumber");
    }//end


    //set Clinicity
    private void setClinicity(String clinicCity) {
        fillTextByNameForChild(clinicCity, "city");
    }//end
    
    
    //set ClinicZipcode
    private void setClinicZipcode(String clinicZipcode) {
        fillTextByNameForChild(clinicZipcode, "zipCode");
    }//end





    //set the physician mno
    private void setPhysicianMobileNumber(String childMobbNo) {
        fillTextByNameForChild(childMobbNo, "mobileNumber");
    }//end

    //set the email
    private void setChildEmail(String childemailis) {
        fillTextByNameForChild(childemailis, "email");
    }//end

    //set the email
    private void setPhysicianEmail(String childemailis) {
        fillTextByNameForChild(childemailis, "email");
    }//end

    //for language
    private void setChildLanguage() {
        WebElement languageButton = driver.findElement(By.id("createMpa-langKey"));
        WebElement language = languageButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(language);
        WebElement deButton = driver.findElement(By.id("en"));
        click(deButton);
    }//end

    //for language
    private void setPhysicianLanguage() {
        WebElement languageButton = driver.findElement(By.id("createPhysician-langKey"));
        WebElement language = languageButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(language);
        WebElement deButton = driver.findElement(By.id("en"));
        click(deButton);
    }//end


    //for clinic
    private void setClinicdocassistant() {
        WebElement clinicButton = driver.findElement(By.id("createMpa-clinicId"));
        WebElement clinic = clinicButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(clinic);
        WebElement selectclinicButton = driver.findElement(By.xpath("(//*[text()='UsB Clinic'])[1]"));
        click(selectclinicButton);
    }//end

    //for clinic
    private void setClinicPhysician() {
        WebElement clinicButton = driver.findElement(By.id("createPhysician-clinicId"));
        WebElement clinic = clinicButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(clinic);
        WebElement selectclinicButton = driver.findElement(By.xpath("(//*[text()='UsB Clinic'])[1]"));
        click(selectclinicButton);
    }//end


    private void clickOnSaveButton() {
        WebElement saveButton = driver.findElement(By.id("save"));
        click(saveButton);
        waitFewSeconds(1500);
    }

    public void sendAppInvitationToPatientByAdmin(Patient patient) {
        clickOnPatientsTab();
        sendInvitationToPatient();
        // logoutFromUser();
    }

    private void clickOnPatientsTab() {
        WebElement patientsTab = driver.findElement(By.id("menu.patients"));
        click(patientsTab);
        logger.info("CLICKED PATIENT TAB SUCCESS");
    }

    //this func executes the catch block if not find element
    private void sendInvitationToPatient() {

        driver.findElement(By.xpath("//*[text()='App Activated']")).click();

        logger.info("Clicked on App activated filter");

        waitFewSeconds(6000);

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

            logger.info("Element not found So in catch block executes..: " + e.getMessage());

            logger.info("Verified that this page has not any ACTIVATED STATUS for a paient");

            logger.info("Into catch block");

            //click on more svg
            driver.findElement(By.xpath("(//button[@aria-label='More'])[1]")).click();
            logger.info("Cliked on More svg menu opens");

            waitFewSeconds(3000);

            driver.findElement(By.xpath("//*[text()='Renew invitation']")).click();
            waitFewSeconds(2000);

            logger.info("Cliked on renew invitation btn..");

            try{
                logger.info("In try block");

                waitFewSeconds(4000);
                //verify msg: Invitation Email was sent again.
                WebElement ele = driver.findElement(By.xpath("//*[text()='Invitation Email was sent again.']"));
                String ActualTitle = ele.getText();
                String ExpectedTitle = "Invitation Email was sent again.";
                waitFewSeconds(2000);
                Assert.assertEquals(ExpectedTitle, ActualTitle);
                logger.info("Invitation App Message is successfully verified");

                // waitFewSeconds(5000);

                // driver.findElement(By.xpath("(//span[text()='Patients'])[3]")).click();
                // driver.findElement(By.xpath("//span[text()='App Activated']")).click();
                // waitFewSeconds(10000);

            }catch(Exception exe){

                logger.info("Into catch block");
                Assert.fail("Titles do not match");

            }

        }

    }


    public void sendAppInvitationToUsersTabUserByAdmin(Patient patient) {
        clickOnUsersTab();
        sendInvitationToUsersTabUser();
    }

    private void clickOnUsersTab() {
        WebElement usersTab = driver.findElement(By.id("menu.users"));
        click(usersTab);
        logger.info("CLICKED USERS TAB SUCCESS");
    }

    //this func executes the catch block if not find element
    private void sendInvitationToUsersTabUser() {

        driver.findElement(By.xpath("//*[text()='App Activated']")).click();

        logger.info("Clicked on App activated filter");

        waitFewSeconds(6000);

        try{
            //find and store activatedstatus
            WebElement chkingActivatedStatus = driver.findElement(By.xpath("//*[contains(@d,'M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z')]"));

            //check for ACTIVATED STATUS OR NOT[true sign]
            if(chkingActivatedStatus.isDisplayed()){
                // Assert.fail("Activated Status find..So not Send App Invitation to USER.");
                logger.info("Activated Status found. Not sending App Invitation to USER.");
            }
        }
        catch(Exception e){

            logger.info("Element not found So in catch block executes..: " + e.getMessage());

            logger.info("Verified that this page has not any ACTIVATED STATUS for a user");

            logger.info("Into catch block");

            //click on more svg
            driver.findElement(By.xpath("(//button[@aria-label='More'])[1]")).click();
            logger.info("Cliked on More svg menu opens");

            waitFewSeconds(3000);

            driver.findElement(By.xpath("//*[text()='Renew invitation']")).click();
            waitFewSeconds(2000);

            logger.info("Cliked on renew invitation btn..");

            try{
                logger.info("In try block");

                waitFewSeconds(4000);
                //verify msg: Invitation Email was sent again.
                WebElement ele = driver.findElement(By.xpath("//*[text()='Invitation Email was sent again.']"));
                String ActualTitle = ele.getText();
                String ExpectedTitle = "Invitation Email was sent again.";
                waitFewSeconds(2000);
                Assert.assertEquals(ExpectedTitle, ActualTitle);
                logger.info("Invitation App Message is successfully verified");

            }catch(Exception exe){

                logger.info("Into catch block");
                Assert.fail("Titles do not match");
            }
        }
    }

    public void UserProfileEditAndCancelEditByAdmin(Patient patient) {
        clickOnPatientsTab();
        selectPrimaryUserForProfileEditByAdmin();
        EditPrimaryCancelProfileByAdmin();
        EditContactDetailsCancelProfileByAdmin();
    }

    public void selectPrimaryUserForProfileEditByAdmin(){

        WebElement searchButton = driver.findElement(By.id("patient-search-bar-search-button"));
        click(searchButton);
        fillTextById("updatedemailthree@gmail.com", "patient-search-bar-search-field");
        waitFewSeconds(5000);
        WebElement myPatients = driver.findElement(By.id("myPatients"));
        WebElement myPatient = myPatients.findElement(By.xpath(("//a[contains(@href, '/private/app/patients/PAT')]")));
        click(myPatient);

        logger.info("Clickd on patient");

        //click on shoe more arrow btn
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@aria-label,'Show more')])[1]"))).click();
        waitFewSeconds(4000);

        logger.info("Show More Success..");
    }


    public void EditPrimaryCancelProfileByAdmin(){

        WebElement contactEditpath = driver.findElement(By.xpath("(//button[contains(@class, 'jss62') and @tabindex='0'])[8]"));
        click(contactEditpath); 
        logger.info("Edit btn clicked");

        //used to scroll up, use because element not seen and clickable
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, -500);");
        //end

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

        primaryEditSalutation.sendKeys("PatientUpdated");
        logger.info("salutation done");
		
        //adding paths for a edit firstname:
        WebElement primaryEditfirstname = driver.findElement(By.xpath("//input[@name='firstName']"));
        primaryEditfirstname.click();
        waitFewSeconds(1000);
        primaryEditfirstname.clear();
        primaryEditfirstname.sendKeys("YS");
        waitFewSeconds(1000);
        logger.info("firstname done");
		
        //adding paths for a edit lastname:
        WebElement primaryEditLastName = driver.findElement(By.xpath("//input[@name='lastName']"));
        primaryEditLastName.click();
        waitFewSeconds(1000);
        primaryEditLastName.clear();
        primaryEditLastName.sendKeys("Updated Last Name up");
        waitFewSeconds(1000);
        logger.info("lastname done");
		
        // //adding paths for a edit birthdate:
        WebElement primaryEditBirthDate = driver.findElement(By.xpath("//input[@id='patientData-birthdate']"));
        primaryEditBirthDate.click();
        waitFewSeconds(1000);
        primaryEditBirthDate.clear();
        primaryEditBirthDate.sendKeys("20/01/1998");
        waitFewSeconds(2000);
        logger.info("birthday done");

        waitFewSeconds(1000);

        WebElement clickGenderDropdown = driver.findElement(By.xpath("//div[@aria-pressed=\"false\"]"));
        clickGenderDropdown.click();

        waitFewSeconds(3000);

        WebElement selectDropdownOption = driver.findElement(By.xpath("//span[text()=\"Male\"]"));
        selectDropdownOption.click();

        waitFewSeconds(3000);

        //adding paths for a edit socialsecurity no/insurance no:
        WebElement primaryEditSocialSecurityNo = driver.findElement(By.xpath("//input[@name='insuranceNumber']"));
        primaryEditSocialSecurityNo.click();
        waitFewSeconds(1000);
        primaryEditSocialSecurityNo.clear();
        primaryEditSocialSecurityNo.sendKeys("333");
        waitFewSeconds(1000);
        logger.info("insurance no done");
		
        //adding paths for a edit patient no:
        WebElement primaryEditPatientNo = driver.findElement(By.xpath("//input[@name='lifelongId']"));
        primaryEditPatientNo.click();
        waitFewSeconds(1000);
        primaryEditPatientNo.clear();
        primaryEditPatientNo.sendKeys("777");
        waitFewSeconds(1000);
        logger.info("patient num done");

        WebElement contactFieldSave = driver.findElement(By.xpath("//button[@id='save']"));
        click(contactFieldSave);
        logger.info("Successfully saved primary fields..");

        waitFewSeconds(4000);

        //cancel field process for primary fields
        WebElement contactEditpathAgain = driver.findElement(By.xpath("(//button[contains(@class, 'jss62') and @tabindex='0'])[8]"));
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

        waitFewSeconds(6000);

        logger.info("Cancel btn click for primary fields success");

    }
 
    
    //working properly for edit/cancel contactDetail user profile
    public void EditContactDetailsCancelProfileByAdmin(){

        //edit and save process start
        //used to scroll up, use because element not seen and clickable
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, -500);");
        //end

        WebElement contactDetailsEditpath = driver.findElement(By.xpath("(//button[contains(@class, 'jss62') and @tabindex='0'])[9]"));

        // waitFewSeconds(2000);

        click(contactDetailsEditpath); 

        logger.info("Edit btn clicked");

        //adding paths for a edit email:
        WebElement contactEditEmail = driver.findElement(By.xpath("//input[@name='email']"));
        
        contactEditEmail.click();
        waitFewSeconds(1000);

        contactEditEmail.clear();

        contactEditEmail.sendKeys("updatedemailFour@gmail.com");
        waitFewSeconds(1000);

        logger.info("Mail updated");

        
        //adding paths for a edit mobno:
        WebElement contactEditMobileno = driver.findElement(By.xpath("//input[@name='mobileNumber']"));
        
        contactEditMobileno.click();
        waitFewSeconds(1000);

        contactEditMobileno.clear();

        contactEditMobileno.sendKeys("+419994911");
        waitFewSeconds(1000);

        logger.info("mobileno updated");

        waitFewSeconds(1000);

        
        WebElement contactFieldSave = driver.findElement(By.xpath("//button[@id='save']"));
        click(contactFieldSave);

        waitFewSeconds(6000);

        logger.info("Saved successfully");

        //used to scroll up, use because element not seen and clickable
        JavascriptExecutor jsupnew = (JavascriptExecutor) driver;
        jsupnew.executeScript("window.scrollBy(0, -500);");
        //end

        logger.info("Again scroll up");

        waitFewSeconds(6000);
        //end edit and save profile process


        //for cancel process start
        //edit btn click
        WebElement contactDetailsEditpathTwo = driver.findElement(By.xpath("(//button[contains(@class, 'jss62') and @tabindex='0'])[9]"));
        
        logger.info("Secondtime find xath of edit");
        
        click(contactDetailsEditpathTwo); 

        logger.info("for Cancel process Edit btn clicked");

        WebElement contactEditEmailTwo = driver.findElement(By.xpath("//input[@name='email']"));
        waitFewSeconds(2000);
        contactEditEmailTwo.click();
        waitFewSeconds(1000);

        contactEditEmailTwo.clear();

        contactEditEmailTwo.sendKeys("updatedemailThreeNewCancel@gmail.com");
        waitFewSeconds(2000);

        logger.info("Cancel email field updated");

        //target cancel btn click
        //end cancel process

        WebElement cancelBtnpath = driver.findElement(By.xpath("//span[text()='Cancel']"));
        waitFewSeconds(3000);
        cancelBtnpath.click();

        logger.info("Cancel btn click success");

        waitFewSeconds(4000);

    }

}
