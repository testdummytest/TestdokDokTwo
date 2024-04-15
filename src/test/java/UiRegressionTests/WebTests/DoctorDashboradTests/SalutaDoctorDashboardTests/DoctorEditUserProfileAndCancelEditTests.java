package UiRegressionTests.WebTests.DoctorDashboradTests.SalutaDoctorDashboardTests;

import org.testng.annotations.Test;

import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.DoctorHomePage;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DoctorEditUserProfileAndCancelEditTests extends ChLoginBaseTest {

    private static final Logger logger = LogManager.getLogger(DoctorEditUserProfileAndCancelEditTests.class);
    
    //used for profile edit and cancel edit profile by doctor
    @Test(dataProvider = "login-doctor-And-patient-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheDoctorCanEditAndCancelEditProfileOfUser(Doctor doctor, Patient patient) {
        logger.info("Start Test");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);
        logger.info("Doctor login success");
        //create patient and logout from doctor
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.selectPatient(patient);
        doctorHomePage.UserProfileEditAndCancelEdit(patient);

    }

}
