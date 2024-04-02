package UiRegressionTests.WebTests.DoctorDashboradTests.SalutaDoctorDashboardTests;

import java.util.logging.Logger;

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
import UiRegressionTests.loggersetup;
import java.util.logging.Logger;

public class clinicChatBlockUnblockForPatient extends ChLoginBaseTest {

    private static final Logger logger = loggersetup.getLogger();

    //used for a child patient
    @Test(dataProvider = "create-clinic-by-admin", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyAdminBlockUnblockClinic(Admin admin) {
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        AdminHomePage adminhomepage = new AdminHomePage(driver);
        adminhomepage.clinicSelectAndBlock(admin);

        // TestingUtilPage testingUtilPage = new TestingUtilPage(driver);
        // String tmpPassword = testingUtilPage.openActivationUrlByTestingUtil(admin);
        // loginPage.activateTheDocAssistantBySettingNewPassword(doctor, tmpPassword);
        // DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        // doctorHomePage.verifyThatTheDocAssistLoggedInSuccessfully();

    }//end child used func
    
}
