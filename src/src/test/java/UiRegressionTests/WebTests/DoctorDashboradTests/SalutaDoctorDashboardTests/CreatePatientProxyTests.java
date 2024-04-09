package UiRegressionTests.WebTests.DoctorDashboradTests.SalutaDoctorDashboardTests;

import org.testng.annotations.Test;

import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.DoctorHomePage;
import PageObjects.LoginPage;
import PageObjects.PatientHomePage;
import PageObjects.TestingUtilPage;
import UiRegressionTests.ChLoginBaseTest;
import UiRegressionTests.loggersetup;
import java.util.logging.Logger;


public class CreatePatientProxyTests extends ChLoginBaseTest {

    private static final Logger logger = loggersetup.getLogger();

    //used for a child patient
    @Test(dataProvider = "create-child-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheDoctorCanCreateChildPatientSuccessfully(Patient patient, Admin admin, Doctor doctor) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);
        logger.info("login success");
        //create patient and logout from doctor
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.createChildPatientByTheDoctor(patient);

        TestingUtilPage testingUtilPage = new TestingUtilPage(driver);
        String tmpPassword = testingUtilPage.openActivationUrlByTestingUtil(admin);
        loginPage.activateTheUserBySettingNewPassword(patient, tmpPassword);
        PatientHomePage patientHomePage = new PatientHomePage(driver);
        patientHomePage.verifyThatTheUserLoggedInSuccessfully();

    }//end child used func

}
