package UiRegressionTests.WebTests.PatientDashboardTests.HomeScreenTests;

import org.testng.annotations.Test;

import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.LoginPage;
import PageObjects.PatientHomePage;
import UiRegressionTests.ChLoginBaseTest;

public class PateintHomeScreenTests extends ChLoginBaseTest {
    @Test(dataProvider = "login-patient-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyTappingOnEachFeatureShouldNavigateToRespectiveScreens(Patient patient) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsPatient(patient);
        PatientHomePage patientHomePage = new PatientHomePage(driver);
        patientHomePage.respectiveScreenForPateint();
    }


}
