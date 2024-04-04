package UiRegressionTests.WebTests.DoctorDashboradTests.SalutaDoctorDashboardTests;

import java.util.logging.Logger;

import org.testng.annotations.Test;

import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.AdminHomePage;
import PageObjects.LoginPage;
import PageObjects.PatientHomePage;
import UiRegressionTests.ChLoginBaseTest;
import UiRegressionTests.loggersetup;

public class ClinicChatBlockUnblockForPatientTests extends ChLoginBaseTest {

    private static final Logger logger = loggersetup.getLogger();

    @Test(dataProvider = "existing-admin-doctor-patient", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyAdminBlockUnblockClinic(Admin admin, Patient patient, Doctor doctor) {
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        AdminHomePage adminhomepage = new AdminHomePage(driver);
        adminhomepage.selectClinictDisableChat(admin);
        loginPage.loginAsPatient(patient);
        logger.info("Patient login success");
        PatientHomePage patientHomePage = new PatientHomePage(driver);
        patientHomePage.verifyPatientChatDisabled(patient);
        loginPage.loginAsAdmin(admin);
        logger.info("Again Admin login success");
        adminhomepage.selectClinicEnableChat(admin);
        loginPage.loginAsPatient(patient);
        logger.info("Again Patient login success");
        patientHomePage.verifyPatientChatEnabled(patient);

    }
    
}
