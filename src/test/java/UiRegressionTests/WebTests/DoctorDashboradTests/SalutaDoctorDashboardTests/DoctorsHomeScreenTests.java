package UiRegressionTests.WebTests.DoctorDashboradTests.SalutaDoctorDashboardTests;

import org.testng.annotations.Test;

import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.DoctorHomePage;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;


public class DoctorsHomeScreenTests extends ChLoginBaseTest {
    @Test(alwaysRun=true,dataProvider = "create-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyTappingOnEachFeatureShouldNavigateToRespectiveScreens(Patient patient, Admin admin,
            Doctor doctor) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.respectiveScreenForDoctor();
    }

}
