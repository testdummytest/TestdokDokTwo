package UiRegressionTests.WebTests.DoctorDashboradTests.SalutaDoctorDashboardTests;

import org.testng.annotations.Test;

import Entities.Admin;
import Framework.DataProviderClass;
import PageObjects.AdminHomePage;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;
import UiRegressionTests.loggersetup;
import java.util.logging.Logger;


public class CreateClinicTests extends ChLoginBaseTest {

    private static final Logger logger = loggersetup.getLogger();
 
    //used for create clinic by admin
    @Test(dataProvider = "create-clinic-by-admin", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheAdminCanCreateClinicsSuccessfully(Admin admin) {
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);

        logger.info("Admin login success");
        
        AdminHomePage adminhomepage = new AdminHomePage(driver);
        adminhomepage.createClinicByDoctor(admin);

    }//end

}
