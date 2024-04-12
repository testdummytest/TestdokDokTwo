package UiRegressionTests.WebTests.AdminDashboardTests;
import UiRegressionTests.ChLoginBaseTest;
import UiRegressionTests.loggersetup;
import java.util.logging.Logger;

import org.testng.annotations.Test;

import Entities.Admin;
import Framework.DataProviderClass;
import PageObjects.AdminHomePage;
import PageObjects.LoginPage;

public class StudyTests extends ChLoginBaseTest{
    
    private static final Logger logger = loggersetup.getLogger();
    
    @Test(dataProvider = "existing-admin-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheAdminCanCreateAndVerifyStudySuccessfully(Admin admin) {
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        AdminHomePage adminhomepage = new AdminHomePage(driver);
        adminhomepage.createVerifyStudy(admin);

    }
}
