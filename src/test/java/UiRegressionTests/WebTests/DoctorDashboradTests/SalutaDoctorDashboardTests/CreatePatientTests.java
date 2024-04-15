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

public class CreatePatientTests extends ChLoginBaseTest {

    private static final Logger logger = LogManager.getLogger(CreatePatientTests.class);

    @Test(dataProvider = "create-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheDoctorCanCreatePatientSuccessfully(Patient patient, Admin admin, Doctor doctor) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.createPatientByTheDoctor(patient);
        TestingUtilPage testingUtilPage = new TestingUtilPage(driver);
        String tmpPassword = testingUtilPage.openActivationUrlByTestingUtil(admin);
        loginPage.activateTheUserBySettingNewPassword(patient, tmpPassword);
        PatientHomePage patientHomePage = new PatientHomePage(driver);
        patientHomePage.verifyThatTheUserLoggedInSuccessfully();
    }

    @Test(dataProvider = "create-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatThePatientResetPasswordSuccessfully(Patient patient, Admin admin, Doctor doctor) {
        logger.info("Start Test");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        String email = doctorHomePage.createPatientByTheDoctorFetchEmailString(patient);
        logger.info(email);
        TestingUtilPage testingUtilPage = new TestingUtilPage(driver);
        String tmpPassword = testingUtilPage.openActivationUrlByTestingUtil(admin);
        loginPage.activateTheUserBySettingNewPassword(patient, tmpPassword);
        PatientHomePage patientHomePage = new PatientHomePage(driver);
        patientHomePage.verifyThatTheUserLoggedInSuccessfully();
        patientHomePage.forgotpasswordbyPatient(patient, admin, doctor, email);
    }
    
}


