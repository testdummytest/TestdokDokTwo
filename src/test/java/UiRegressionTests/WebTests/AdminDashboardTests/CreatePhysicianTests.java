package UiRegressionTests.WebTests.AdminDashboardTests;

import org.testng.annotations.Test;

import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.AdminHomePage;
import PageObjects.DoctorHomePage;
import PageObjects.LoginPage;
import PageObjects.TestingUtilPage;
import UiRegressionTests.ChLoginBaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreatePhysicianTests extends ChLoginBaseTest {

    private static final Logger logger = LogManager.getLogger(CreatePhysicianTests.class);
    
    //used for physician-doctor
    @Test(dataProvider = "create-doctor-physician-by-admin", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheAdminCanCreatePhysicianSuccessfully(Patient patient, Admin admin, Doctor doctor) {
        logger.info("Start Test");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        //create patient and logout from doctor
        AdminHomePage adminHomepage = new AdminHomePage(driver);
        adminHomepage.createDoctorPhysicianByTheAdmin(admin);
        TestingUtilPage testingUtilPage = new TestingUtilPage(driver);
        String tmpPassword = testingUtilPage.openActivationUrlByTestingUtil(admin);
        loginPage.activateTheDocAssistantBySettingNewPassword(doctor, tmpPassword);
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.verifyThatThePhysicianDoctorLoggedInSuccessfully();

    }//end

}
