package UiRegressionTests.WebTests.AdminDashboardTests;

import org.testng.annotations.Test;

import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.AdminHomePage;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;

public class AdminEditUserProfileAndCancelEditTests extends ChLoginBaseTest {


    //used for profile edit and cancel edit profile by doctor
    @Test(dataProvider = "existing-admin-doctor-patient", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheAdminCanEditAndCancelEditProfileOfUser(Admin admin, Patient patient, Doctor doctor) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);
        AdminHomePage adminHomePage = new AdminHomePage(driver);
        adminHomePage.UserProfileEditAndCancelEditByAdmin(patient);

    }//end

}
