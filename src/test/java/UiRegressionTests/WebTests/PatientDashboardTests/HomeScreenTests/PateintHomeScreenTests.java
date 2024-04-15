package UiRegressionTests.WebTests.PatientDashboardTests.HomeScreenTests;

import org.testng.annotations.Test;

import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.LoginPage;
import PageObjects.PatientHomePage;
import UiRegressionTests.ChLoginBaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PateintHomeScreenTests extends ChLoginBaseTest {
    private static final Logger logger = LogManager.getLogger(PateintHomeScreenTests.class);

    @Test(dataProvider = "login-patient-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyTappingOnEachFeatureShouldNavigateToRespectiveScreensForPatient(Patient patient) {
        logger.info("Start Test");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsPatient(patient);
        PatientHomePage patientHomePage = new PatientHomePage(driver);
        patientHomePage.respectiveScreenForPateint();
    }


}
