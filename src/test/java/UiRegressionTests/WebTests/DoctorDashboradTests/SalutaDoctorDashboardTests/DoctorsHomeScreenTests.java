package UiRegressionTests.WebTests.DoctorDashboradTests.SalutaDoctorDashboardTests;

import org.testng.annotations.Test;

import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.DoctorHomePage;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DoctorsHomeScreenTests extends ChLoginBaseTest {
    private static final Logger logger = LogManager.getLogger(DoctorsHomeScreenTests.class);

    @Test(alwaysRun=true,dataProvider = "create-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyTappingOnEachFeatureShouldNavigateToRespectiveScreens(Patient patient, Admin admin,
            Doctor doctor) {
        logger.info("Start Test");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.respectiveScreenForDoctor();
    }

}
