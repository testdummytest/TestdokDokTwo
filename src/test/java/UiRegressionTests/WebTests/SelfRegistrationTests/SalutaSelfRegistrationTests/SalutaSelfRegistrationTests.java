package UiRegressionTests.WebTests.SelfRegistrationTests.SalutaSelfRegistrationTests;

import org.testng.annotations.Test;

import Entities.Admin;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.LoginPage;
import PageObjects.PatientHomePage;
import PageObjects.SalutaSelfRegistrationPage;
import PageObjects.TestingUtilPage;
import UiRegressionTests.SalutaBaseTest;

public class SalutaSelfRegistrationTests extends SalutaBaseTest {
    @Test(enabled = false, dataProvider = "create-patient-by-self-Registration-data", dataProviderClass = DataProviderClass.class)
    public void createPatient(Patient patient, Admin admin) {
        SalutaSelfRegistrationPage salutaSelfRegistrationPage = new SalutaSelfRegistrationPage(driver);
        salutaSelfRegistrationPage.fillFieldsForCreatePatient(patient);
        salutaSelfRegistrationPage.verifyGettingSuccessPage();
        TestingUtilPage testingUtilPage = new TestingUtilPage(driver);
        String tmpPassword = testingUtilPage.openActivationUrlByTestingUtil(admin);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.activateTheUserBySettingNewPassword(patient, tmpPassword);
        PatientHomePage patientHomePage = new PatientHomePage(driver);
        patientHomePage.verifyThatTheUserLoggedInSuccessfully();
    }

}
