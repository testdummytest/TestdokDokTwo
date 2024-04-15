package UiRegressionTests.WebTests.AdminDashboardTests;

import org.testng.annotations.Test;

import Entities.Admin;
import Framework.DataProviderClass;
import PageObjects.AdminHomePage;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CreateClinicTests extends ChLoginBaseTest {

    private static final Logger logger = LogManager.getLogger(CreateClinicTests.class);
 
    //used for create clinic by admin
    @Test(dataProvider = "create-clinic-by-admin", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheAdminCanCreateClinicsSuccessfully(Admin admin) {
        logger.info("Start Test");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        AdminHomePage adminHomepage = new AdminHomePage(driver);
        adminHomepage.createClinicByDoctor(admin);

    }//end

}
