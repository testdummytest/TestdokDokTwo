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
import java.io.File;
import Entities.Admin;
import Entities.Patient;
import Framework.DataProviderClass;
import org.apache.logging.log4j.LogManager;import org.apache.logging.log4j.Logger;

public class AdminHomePage extends BasePage{

    private static final Logger logger = LogManager.getLogger(AdminHomePage.class);
    
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
    }


    public void createClinicByDoctor(Admin admin) {

        //click on the teambox
        clickOnClinicsPageSideTab();
        clickOnCreateClinicBtn();
        fillFieldsForClinic(admin);
        logger.debug("Successfully filled the clinic name..");
        clickOnSaveButton();
        driver.findElement(By.xpath("//span[text()=\"Clinic successfully created\"]"));
        logoutFromUser();
    }

    public void clickOnTeamBoxAtHomePage() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[text()='group_work_item']")).click();
            Thread.sleep(2000);
        } catch (Exception e) {
            logger.error("Error in clicking Team btn");
        }
    }

    public void createVerifyStudy(Admin admin){

        createStudy();
        activateStudy();
        addPhysicianAndCoordinatorToStudy();
        addSurveyToStudy();
        addSchedulingToSurvey();
        removeSurveyFromStudy();
        exportStudyData();
        deleteCurrentStudy();
        
    }
    public void createStudy(){
        driver.findElement(By.xpath("//span[text()='Studies']")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Create Study']"))).click();
        String name = "AutomationStudy-"+DataProviderClass.getRandomString();
        String key = DataProviderClass.getUniqueId();
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys(name);
        driver.findElement(By.xpath("//input[@name='key']")).sendKeys(key);
        driver.findElement(By.xpath("//span[text()='Create']")).click();
        String xpathExpression = "//span[text()='Study " + name + " successfully created']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression)));
        assert driver.findElement(By.xpath(xpathExpression)).isDisplayed();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='" + name + "' ]")));
        assert driver.findElement(By.xpath("//h4[text()='" + name + "' ]")).isDisplayed();
        logger.debug("Study created and verified successfully");

    }

    public void activateStudy() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Paused']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Active']"))).click();
        waitFewSeconds(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Active']")));
        assert driver.findElement(By.xpath("//span[text()='Active']")).isDisplayed();
        logger.debug("Study's ACTIVATED status is selected and verified");
    }

    public void addPhysicianAndCoordinatorToStudy() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='studyPhysicians-add-button']")));
        driver.findElement(By.xpath("//button[@id='studyPhysicians-add-button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-test='select-0']")));
        driver.findElement(By.xpath("//span[@data-test='select-0']")).click();
        driver.findElement(By.xpath("//span[text()='Add']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='infopanel']/span[contains(text(), 'successfully added')]")));
        assert driver.findElement(By.xpath("//span[contains(text(), 'successfully added')]")).isDisplayed();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='studyPhysicians-person-0']")));
        assert driver.findElement(By.xpath("//div[@id='studyPhysicians-person-0']")).isDisplayed();
        logger.debug("Physician added to study and verified successfully");
        waitFewSeconds(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='studyCoordinator-add-button']")));
        driver.findElement(By.xpath("//button[@id='studyCoordinator-add-button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-test='select-0']")));
        driver.findElement(By.xpath("//span[@data-test='select-0']")).click();
        driver.findElement(By.xpath("//span[text()='Add']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='infopanel']/span[contains(text(), 'successfully added')]")));
        assert driver.findElement(By.xpath("//span[contains(text(), 'successfully added')]")).isDisplayed();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='studyCoordinator-person-0']")));
        assert driver.findElement(By.xpath("//div[@id='studyCoordinator-person-0']")).isDisplayed();
        logger.debug("Coordinator added to study and verified successfully");

    }

    public void addSurveyToStudy(){
        DataProviderClass.getProperties();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Add Survey']"))).click();
        WebElement searchButton = driver.findElement(By.id("addSurveyToStudy-surveys-search-field"));
        click(searchButton);
        fillTextById(DataProviderClass.StudySurvey, "addSurveyToStudy-surveys-search-field");
        waitFewSeconds(5000);
        WebElement agreeCheckbox = driver.findElement(By.xpath("//span[text()='"+DataProviderClass.StudySurvey+"']/../../../..//div//input"));
        click(agreeCheckbox);
        WebElement submitButton = driver.findElement(By.id("save"));
        click(submitButton);
        assert driver.findElement(By.xpath("//span[contains(text(), 'Survey successfully added to the study')]")).isDisplayed();
    }

    public void addSchedulingToSurvey(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@aria-label='More'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Schedule Survey']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Do schedule the survey']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Send as soon patient is added to the study']"))).click();
        WebElement submitButton = driver.findElement(By.id("save"));
        click(submitButton);
    }

    public void removeSurveyFromStudy(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@aria-label='More'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Remove Survey from Study']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Survey successfully removed from Study')]"))).click();
        assert driver.findElement(By.xpath("//span[contains(text(), 'Survey successfully removed from Study')]")).isDisplayed();
    }

    public void exportStudyData(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Export Studydata']")));
        driver.findElement(By.xpath("//span[text()='Export Studydata']")).click();
        logger.debug("Clicked on Export Studydata");
        waitFewSeconds(20);
    }

    public void deleteCurrentStudy(){

        driver.findElement(By.xpath("//span[text()='Active']")).click();
        driver.findElement(By.xpath("//li[@data-value='FINISHED']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Delete Study']")));
        driver.findElement(By.xpath("//span[text()='Delete Study']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Ok']")));
        driver.findElement(By.xpath("//span[text()='Ok']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Study successfully deleted']")));
        assert driver.findElement(By.xpath("//span[text()='Study successfully deleted']")).isDisplayed();
        logger.debug("Study is successfully deleted");
    }


    public void selectClinictDisableChat(Admin admin) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='Clinics'])[1]"))).click();
        driver.findElement(By.xpath("(//span[text()='Clinics'])[1]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='action-overlay -odd'])[1]")));
        driver.findElement(By.xpath("(//div[@class='action-overlay -odd'])[1]")).click();

        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-test='sync-disable']")));
            assert driver.findElement(By.xpath("//*[@data-test='sync-disable']")).isDisplayed();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Enable/Disable Chat')]")));
            waitFewSeconds(2000);
            driver.findElement(By.xpath("//span[contains(text(), 'Enable/Disable Chat')]")).click();
            logger.debug("Clicked on a Disable chat");
            waitFewSeconds(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='action-overlay -odd'])[1]")));
            driver.findElement(By.xpath("(//div[@class='action-overlay -odd'])[1]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-test='sync-enable']")));
            assert driver.findElement(By.xpath("//*[@data-test='sync-enable']")).isDisplayed();
            logger.info("Chat disabled successfully");

        }catch(Exception e){
            logger.debug("Clinic is already disabled");
        }
        driver.navigate().refresh();
        logoutFromUser();
    }

    public void selectClinicEnableChat(Admin admin) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='Clinics'])[1]"))).click();
        driver.findElement(By.xpath("(//span[text()='Clinics'])[1]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='action-overlay -odd'])[1]")));
        driver.findElement(By.xpath("(//div[@class='action-overlay -odd'])[1]")).click();

        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-test='sync-enable']")));
            assert driver.findElement(By.xpath("//*[@data-test='sync-enable']")).isDisplayed();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Enable/Disable Chat')]")));
            waitFewSeconds(2000);
            driver.findElement(By.xpath("//span[contains(text(), 'Enable/Disable Chat')]")).click();
            logger.debug("Clicked on a Disable chat");
            waitFewSeconds(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='action-overlay -odd'])[1]")));
            driver.findElement(By.xpath("(//div[@class='action-overlay -odd'])[1]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-test='sync-disable']")));
            assert driver.findElement(By.xpath("//*[@data-test='sync-disable']")).isDisplayed();
            logger.info("Chat enabled successfully");

        }catch(Exception e){
            logger.debug("Clinic is already enabled");
        }
        driver.navigate().refresh();
        logoutFromUser();
        logger.debug("Logged out from admin 2nd time");
        
    }


    public String createCatalog(Admin admin){

        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Upload Content']"))).click();
        waitFewSeconds(20);
        logger.debug("Clicked on uplaod catalog");

        String catalogName = fillCatalogFormDataAndSave();
        logger.debug(catalogName);
        return catalogName;

    }

    public void editAndDeleteCatalog(Admin admin, String catalogName){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Catalog']"))).click();
        searchTheCatalog(catalogName);
        editCatalogData();
        searchTheCatalog(catalogName);
        deleteCatalog();

    }

    public String fillCatalogFormDataAndSave(){
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='title']"))).click();
        String catalogTitle = "AutomationCatalog-"+DataProviderClass.getRandomString();
        driver.findElement(By.xpath("//input[@name='title']")).sendKeys(catalogTitle);
        driver.findElement(By.xpath("//input[@name='text']")).sendKeys("AutomationCatalogDescription-"+DataProviderClass.getRandomString());
        driver.findElement(By.xpath("//input[@name='tags']")).sendKeys("tag-"+DataProviderClass.getRandomString());
        driver.findElement(By.xpath("//input[@name='license']")).sendKeys("license-"+DataProviderClass.getRandomString());
        WebElement element_lang = driver.findElement(By.xpath("//input[@id='downshift-0-input']"));
        element_lang.click();
        element_lang.sendKeys(Keys.ARROW_DOWN);
        element_lang.sendKeys(Keys.ENTER);
        waitFewSeconds(30);
        driver.findElement(By.xpath("//span[text()='For multiple tags, separate each tag with a semicolon']")).click();
        WebElement element_studies = driver.findElement(By.xpath("//input[@id='downshift-1-input']"));
        waitFewSeconds(3000);
        element_studies.click();
        String studySearch = DataProviderClass.StudyName;
        driver.findElement(By.xpath("//input[@id='downshift-1-input']")).sendKeys(studySearch);
        waitFewSeconds(5000);
        element_studies.sendKeys(Keys.ARROW_DOWN);
        element_studies.sendKeys(Keys.ENTER);

        logger.debug("selected study");
        driver.findElement(By.xpath("//span[text()='For multiple tags, separate each tag with a semicolon']")).click();
        WebElement element_clinic = driver.findElement(By.xpath("//input[@id='downshift-2-input']"));
        element_clinic.click();
        element_clinic.sendKeys(Keys.ARROW_DOWN);
        element_clinic.sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//span[text()='For multiple tags, separate each tag with a semicolon']")).click();
        logger.debug("ends dropdown selection");
        // Locate the file upload input element
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='file']")));
        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
        
        String projectPath = System.getProperty("user.dir");
        String relativeFilePath = projectPath + File.separator + "CatalogFile.pdf";
        fileInput.sendKeys(relativeFilePath);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='save']")));
        driver.findElement(By.xpath("//button[@id='save']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Content successfully uploaded to catalog']")));
        assert driver.findElement(By.xpath("//span[text()='Content successfully uploaded to catalog']")).isDisplayed();

        logger.info("Catalog uploaded successfully");
        logoutFromUser();
        return catalogTitle;
    }

    public void searchTheCatalog(String catalogName){   
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-search-bar-search-button"))).click();
        logger.debug("search btn clicked");
        waitFewSeconds(5000);
        WebElement searchCatalog = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-search-bar-search-field")));
        searchCatalog.clear();
        searchCatalog.sendKeys(catalogName);
        waitFewSeconds(50);
    }

    public void editCatalogData(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='action-overlay -odd'])[1]")));
        driver.findElement(By.xpath("(//div[@class='action-overlay -odd'])[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Edit']")));
        driver.findElement(By.xpath("//span[text()='Edit']")).click();

        String element_text_xpth = "//input[@name='text']";
        driver.findElement(By.xpath(element_text_xpth)).clear();
        driver.findElement(By.xpath(element_text_xpth)).sendKeys("AutomationCatalogDesUpdated-"+DataProviderClass.getRandomString());

        String element_tag_xpath = "//input[@name='tags']";
        driver.findElement(By.xpath(element_tag_xpath)).clear();
        driver.findElement(By.xpath(element_tag_xpath)).sendKeys("tagUpdated-"+DataProviderClass.getRandomString());

        String element_license_xpath = "//input[@name='license']";
        driver.findElement(By.xpath(element_license_xpath)).clear();
        driver.findElement(By.xpath(element_license_xpath)).sendKeys("licenseUpdated-"+DataProviderClass.getRandomString());
        
        WebElement element_lang = driver.findElement(By.xpath("//input[@id='downshift-0-input']"));
        element_lang.click();
        waitFewSeconds(5000);
        element_lang.sendKeys(Keys.ARROW_DOWN);
        element_lang.sendKeys(Keys.ENTER);
        element_lang.sendKeys(Keys.BACK_SPACE);
        element_lang.sendKeys(Keys.ARROW_DOWN);
        element_lang.sendKeys(Keys.ENTER);
        waitFewSeconds(10);
        driver.findElement(By.xpath("//span[text()='For multiple tags, separate each tag with a semicolon']")).click();

        WebElement element_studies = driver.findElement(By.xpath("//input[@id='downshift-1-input']"));
        element_studies.click();
        element_studies.sendKeys(Keys.ARROW_DOWN);
        element_studies.sendKeys(Keys.ENTER);
        element_studies.sendKeys(Keys.BACK_SPACE);
        driver.findElement(By.xpath("//span[text()='For multiple tags, separate each tag with a semicolon']")).click();
        waitFewSeconds(4000);

        WebElement element_clinic = driver.findElement(By.xpath("//input[@id='downshift-2-input']"));
        element_clinic.click();
        element_clinic.sendKeys(Keys.ARROW_DOWN);
        element_clinic.sendKeys(Keys.ENTER);
        element_clinic.sendKeys(Keys.BACK_SPACE);
        driver.findElement(By.xpath("//span[text()='For multiple tags, separate each tag with a semicolon']")).click();
        waitFewSeconds(60);
        // Locate the file upload input element
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='file']")));
        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
        String projectPath = System.getProperty("user.dir");
        String relativeFilePath = projectPath + File.separator + "CatalogFile.pdf";
        fileInput.sendKeys(relativeFilePath);
        waitFewSeconds(60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='save']")));
        driver.findElement(By.xpath("//button[@id='save']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Content successfully uploaded to catalog']")));
        assert driver.findElement(By.xpath("//span[text()='Content successfully uploaded to catalog']")).isDisplayed();
        logger.info("Catalog updated successfully");
        driver.navigate().refresh();
    }

    public void deleteCatalog(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(180));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='action-overlay -odd'])[1]")));
        driver.findElement(By.xpath("(//div[@class='action-overlay -odd'])[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Delete']")));
        driver.findElement(By.xpath("//span[text()='Delete']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Yes']")));
        driver.findElement(By.xpath("//span[text()='Yes']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='No rows found']")));
        assert driver.findElement(By.xpath("//span[text()='No rows found']")).isDisplayed();
        logger.info("Catalog Deleted Successfully.");

    }


    public void clickOnClinicsPageSideTab() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("(//*[text()='Clinics'])[1]")).click();
            Thread.sleep(1000);
            logger.debug("CLINIC clicked");
        } catch (Exception e) {
            logger.error("Error in clicking clinic side tab page");
        }
    }


    public void clickOnDoctorAssistantCreateBtn() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//span[@class='jss215'])[1]")).click();
            Thread.sleep(4000);
        }catch(Exception e){
            logger.error("Error OPENING POPUP");
        }
    }


    public void clickOnPhysicianCreateBtn() {
        logger.info("called physician click method");
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//span[text()='Create Physician']")).click();
            Thread.sleep(3000);
        }catch(Exception e){
            logger.error("Error OPENING POPUP");
        }
    }



    public void clickOnCreateClinicBtn() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//*[text()='Create Clinic']")).click();
            logger.debug("Success CLINIC popup opens");
            Thread.sleep(3000);
        }catch(Exception e){
            logger.error("Error OPENING POPUP");
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
        setClinicDocAssistant();
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
        logger.debug(x);
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
    private void setClinicDocAssistant() {
        WebElement clinicButton = driver.findElement(By.id("createMpa-clinicId"));
        WebElement clinic = clinicButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(clinic);
        WebElement selectclinicButton = driver.findElement(By.xpath("(//*[text()='00 Automation Clinic'])"));
        click(selectclinicButton);
    }//end

    //for clinic
    private void setClinicPhysician() {
        WebElement clinicButton = driver.findElement(By.id("createPhysician-clinicId"));
        WebElement clinic = clinicButton.findElement(By.xpath("preceding-sibling::div[1]"));
        click(clinic);
        WebElement selectclinicButton = driver.findElement(By.xpath("(//*[text()='00 Automation Clinic'])"));
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
                logger.debug("Activated Status found. Not sending App Invitation to PATIENT.");
            }
        }
        catch(Exception e){
            // logger.info("Element not found So in catch block executes..: " + e.getMessage());
            logger.info("Verified that this page has not any ACTIVATED STATUS for a paient");
            //click on more svg
            driver.findElement(By.xpath("(//button[@aria-label='More'])[1]")).click();
            logger.debug("Cliked on More svg menu opens");
            waitFewSeconds(3000);
            driver.findElement(By.xpath("//*[text()='Renew invitation']")).click();
            waitFewSeconds(2000);
            logger.debug("Cliked on renew invitation btn..");
            try{
                logger.debug("Enter in try block");
                // waitFewSeconds(2000);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Invitation Email was sent again.']")));
                //verify msg: Invitation Email was sent again.
                WebElement ele = driver.findElement(By.xpath("//*[text()='Invitation Email was sent again.']"));
                String ActualTitle = ele.getText();
                String ExpectedTitle = "Invitation Email was sent again.";
                // waitFewSeconds(2000);
                Assert.assertEquals(ExpectedTitle, ActualTitle);
                logger.info("Invitation App Message is successfully verified");

            }catch(Exception exe){

                logger.error("Enter into catch block");
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
        logger.debug("CLICKED USERS TAB SUCCESS");
    }

    //this func executes the catch block if not find element
    private void sendInvitationToUsersTabUser() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        driver.findElement(By.xpath("//*[text()='App Activated']")).click();
        logger.debug("Clicked on App activated filter");
        waitFewSeconds(4000);
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
            logger.debug("Verified that this page has not any ACTIVATED STATUS for a user");
            //click on more svg
            driver.findElement(By.xpath("(//button[@aria-label='More'])[1]")).click();
            logger.debug("Cliked on More svg menu opens");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Renew invitation']")));
            // waitFewSeconds(3000);
            driver.findElement(By.xpath("//*[text()='Renew invitation']")).click();
            waitFewSeconds(2000);
            logger.info("Cliked on renew invitation btn..");
            try{
                logger.debug("Enter in try block");
                waitFewSeconds(3000);
                //verify msg: Invitation Email was sent again.
                WebElement ele = driver.findElement(By.xpath("//*[text()='Invitation Email was sent again.']"));
                String ActualTitle = ele.getText();
                String ExpectedTitle = "Invitation Email was sent again.";
                // waitFewSeconds(2000);
                Assert.assertEquals(ExpectedTitle, ActualTitle);
                logger.debug("Invitation App Message is successfully verified");

            }catch(Exception exe){

                logger.debug("Enter into catch block");
                Assert.fail("Titles do not match");
            }
        }
    }

    public void UserProfileEditAndCancelEditByAdmin(Patient patient) {
        clickOnPatientsTab();
        selectPrimaryUserForProfileEditByAdmin(patient);
        EditPrimaryCancelProfileByAdmin();
        // EditContactDetailsCancelProfileByAdmin();
    }

    public void selectPrimaryUserForProfileEditByAdmin(Patient patient){

        WebElement searchButton = driver.findElement(By.id("patient-search-bar-search-button"));
        click(searchButton);
        fillTextById(patient.getEmail(), "patient-search-bar-search-field");
        waitFewSeconds(5000);
        WebElement myPatients = driver.findElement(By.id("myPatients"));
        WebElement myPatient = myPatients.findElement(By.xpath(("//a[contains(@href, '/private/app/patients/PAT')]")));
        click(myPatient);
        logger.debug("Clickd on patient");

        //click on shoe more arrow btn
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@aria-label,'Show more')])[1]"))).click();
        waitFewSeconds(4000);
        logger.debug("Show More Success..");
    }


    public void EditPrimaryCancelProfileByAdmin(){

        WebElement contactEditpath = driver.findElement(By.xpath("//span[text()='Edit']"));
        click(contactEditpath); 
        logger.debug("Edit btn clicked");
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
        }
        primaryEditSalutation.sendKeys("PatientUpdated");
        logger.debug("salutation done");
        //adding paths for a edit firstname:
        WebElement primaryEditfirstname = driver.findElement(By.xpath("//input[@name='firstName']"));
        primaryEditfirstname.click();
        waitFewSeconds(1000);
        primaryEditfirstname.clear();
        primaryEditfirstname.sendKeys("YS");
        waitFewSeconds(1000);
        logger.debug("firstname done");
        //adding paths for a edit lastname:
        WebElement primaryEditLastName = driver.findElement(By.xpath("//input[@name='lastName']"));
        primaryEditLastName.click();
        waitFewSeconds(1000);
        primaryEditLastName.clear();
        primaryEditLastName.sendKeys("Updated Last Name up");
        waitFewSeconds(1000);
        logger.debug("lastname done");
		
        // //adding paths for a edit birthdate:
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
        primaryEditPatientNo.sendKeys("777");
        waitFewSeconds(1000);
        logger.debug("patient num done");

        WebElement contactFieldSave = driver.findElement(By.xpath("//button[@id='save']"));
        click(contactFieldSave);
        logger.info("Successfully saved primary fields..");
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
        waitFewSeconds(6000);
        logger.info("Cancel btn click for primary fields success");

    }
    
    public void EditContactDetailsCancelProfileByAdmin(){
        //edit and save process start
        //used to scroll up, use because element not seen and clickable
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, -500);");
        //end
        WebElement contactDetailsEditpath = driver.findElement(By.xpath("//span[text()='Edit']"));
        // waitFewSeconds(2000);
        click(contactDetailsEditpath); 
        logger.debug("Edit btn clicked");
        //adding paths for a edit email:
        WebElement contactEditEmail = driver.findElement(By.xpath("//input[@name='email']"));
        contactEditEmail.click();
        waitFewSeconds(1000);
        contactEditEmail.clear();
        contactEditEmail.sendKeys("updatedemailFour@gmail.com");
        waitFewSeconds(1000);
        logger.debug("Mail updated");
        //adding paths for a edit mobno:
        WebElement contactEditMobileno = driver.findElement(By.xpath("//input[@name='mobileNumber']"));
        contactEditMobileno.click();
        waitFewSeconds(1000);
        contactEditMobileno.clear();
        contactEditMobileno.sendKeys("+419994911");
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
        //edit btn click
        WebElement contactDetailsEditpathTwo = driver.findElement(By.xpath("//span[text()='Edit']"));
        logger.debug("Secondtime find xath of edit");
        click(contactDetailsEditpathTwo); 
        logger.debug("for Cancel process Edit btn clicked");
        WebElement contactEditEmailTwo = driver.findElement(By.xpath("//input[@name='email']"));
        waitFewSeconds(2000);
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
        logger.info("Cancel btn click success");
        waitFewSeconds(4000);

    }

    public void createEditDeleteModulebyadmin (Admin admin)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='Surveys'])"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Modules']"))).click();
        logger.debug("Clicked on module");
        String moduleName = createModule(); 
        editModule(moduleName);
        deleteModule(moduleName);
         
    }
    
    public String createModule(){
        String randomName = "00Automation_"+DataProviderClass.getRandomString();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Create module']"))).click();
        logger.info("Click on Create Module");
        driver.findElement(By.xpath("//input[@name='groupName']")).sendKeys(randomName);
        WebElement element = driver.findElement(By.xpath("//input[@id='downshift-0-input']"));
        element.click();
        element.sendKeys(Keys.ARROW_DOWN);
        element.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Save']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Module saved successfully']")));
        assert driver.findElement(By.xpath("//span[text()='Module saved successfully']")).isDisplayed();
        logger.info("Module created successfully");
        return randomName;
    
    }

    public void editModule(String moduleName){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        String oldCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+moduleName+"']/../../..//div[2]//div"))).getText();
        logger.info(oldCount);
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Modules']"))).click(); 
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+moduleName+"']/../../..//div[3]//div//button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Edit']"))).click();
        driver.findElement(By.xpath("//input[@name='groupName']")).clear(); 
        driver.findElement(By.xpath("//input[@name='groupName']")).sendKeys(moduleName); 
        WebElement element = driver.findElement(By.xpath("//input[@id='downshift-0-input']"));
        element.click();
        element.sendKeys(Keys.ARROW_DOWN);
        element.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Save']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Module saved successfully']")));
        assert driver.findElement(By.xpath("//span[text()='Module saved successfully']")).isDisplayed();
        String latestCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+moduleName+"']/../../..//div[2]//div"))).getText();
        logger.info(latestCount);
        Assert.assertEquals(Integer.parseInt(oldCount) + 1, Integer.parseInt(latestCount));
        logger.info("Module Updated successfully");

    
    }
    public void deleteModule(String moduleName){
        driver.navigate().refresh();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));    
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Modules']"))).click();    
        logger.info("Going to delete = "+moduleName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+moduleName+"']/../../..//div[3]//div//button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Delete']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Ok']"))).click();
        logger.info("Module Delete successfully");


    }

    // public void surveybyadmin (Admin admin)
    // {
    //         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
    //         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='Surveys'])"))).click();
    //         logger.info("Clicked on survey");
    //         uploadSurvey();
    //         updateSurvey();
    //         activateSurvey();
    //         downloadJSON();
    //         deleteSurvey();
            
    // }

}
