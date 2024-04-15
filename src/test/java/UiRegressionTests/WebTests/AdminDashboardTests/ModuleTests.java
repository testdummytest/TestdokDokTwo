package UiRegressionTests.WebTests.AdminDashboardTests;

import org.testng.annotations.Test;

import Entities.Admin;
import Framework.DataProviderClass;
import PageObjects.AdminHomePage;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModuleTests extends ChLoginBaseTest {

    private static final Logger logger = LogManager.getLogger(ModuleTests.class);

    @Test(dataProvider = "existing-admin-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyAdmincreatemodule(Admin admin) {
        
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        AdminHomePage adminHomepage = new AdminHomePage(driver);
        adminHomepage.createEditDeleteModulebyadmin(admin);
    }

    
}