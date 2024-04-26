// package UiRegressionTests.WebTests.DoctorDashboradTests;

// import java.util.logging.Logger;

// import org.testng.annotations.Test;

// import Entities.Admin;
// import Framework.DataProviderClass;
// import PageObjects.AdminHomePage;
// import PageObjects.LoginPage;
// import UiRegressionTests.ChLoginBaseTest;
// import UiRegressionTests.loggersetup;

// public class UploadsurveyTests extends ChLoginBaseTest {

//     private static final Logger logger = loggersetup.getLogger();

//     @Test(dataProvider = "existing-admin-data", dataProviderClass = DataProviderClass.class)
//     public void shouldVerifyAdmincanuploadsurvey(Admin admin) {
        
//         LoginPage loginPage = new LoginPage(driver);
//         loginPage.loginAsAdmin(admin);
//         logger.info("Admin login success");
//         AdminHomePage adminhomepage = new AdminHomePage(driver);
//         adminhomepage.surveybyadmin(admin);
//     }

    
// }
