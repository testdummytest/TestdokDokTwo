package UiRegressionTests.WebTests.AdminDashboardTests;
import UiRegressionTests.ChLoginBaseTest;
import org.testng.annotations.Test;
import Entities.Admin;
import Framework.DataProviderClass;
import PageObjects.AdminHomePage;
import PageObjects.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudyTests extends ChLoginBaseTest{
    
    private static final Logger logger = LogManager.getLogger(StudyTests.class);
    
    @Test(dataProvider = "existing-admin-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheAdminCanCreateAndVerifyStudySuccessfully(Admin admin) {
        logger.info("Start Test");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        AdminHomePage adminHomepage = new AdminHomePage(driver);
        adminHomepage.createVerifyStudy(admin);

    }
}
