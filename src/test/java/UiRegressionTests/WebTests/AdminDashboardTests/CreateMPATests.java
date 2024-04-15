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

public class CreateMPATests extends ChLoginBaseTest {

    private static final Logger logger = LogManager.getLogger(CreateMPATests.class);

    //used for a child patient
    @Test(dataProvider = "create-doctor-assistant-by-admin", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheAdminCanCreateAssistant_MPASuccessfully(Patient patient, Admin admin, Doctor doctor) {
        logger.info("Start Test");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        AdminHomePage adminHomepage = new AdminHomePage(driver);
        adminHomepage.createAssistantByTheAdmin(admin);
        TestingUtilPage testingUtilPage = new TestingUtilPage(driver);
        String tmpPassword = testingUtilPage.openActivationUrlByTestingUtil(admin);
        loginPage.activateTheDocAssistantBySettingNewPassword(doctor, tmpPassword);
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.verifyThatTheDocAssistLoggedInSuccessfully();

    }//end child used func

}
