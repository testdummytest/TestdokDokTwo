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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CreatePatientProxyTests extends ChLoginBaseTest {

    private static final Logger logger = LogManager.getLogger(CreatePatientProxyTests.class);

    //used for a child patient
    @Test(dataProvider = "create-child-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheDoctorCanCreateChildPatientSuccessfully(Patient patient, Admin admin, Doctor doctor) {
        logger.info("Start Test");
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
