package UiRegressionTests.WebTests.DoctorDashboradTests.SalutaDoctorDashboardTests;

import org.testng.annotations.Test;

import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.DoctorHomePage;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;
import UiRegressionTests.loggersetup;
import java.util.logging.Logger;

public class DoctorEditUserProfileAndCancelEditTests extends ChLoginBaseTest {

    private static final Logger logger = loggersetup.getLogger();
    
    //used for profile edit and cancel edit profile by doctor
    @Test(dataProvider = "login-doctor-And-patient-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheDoctorCanEditAndCancelEditProfileOfUser(Doctor doctor, Patient patient) {
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);
        logger.info("Doctor login success");
        //create patient and logout from doctor
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.selectPatient(patient);
        doctorHomePage.UserProfileEditAndCancelEdit(patient);

    }//end

}
