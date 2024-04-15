package UiRegressionTests.WebTests.AdminDashboardTests;
import org.testng.annotations.Test;
import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;
import PageObjects.AdminHomePage;
import PageObjects.DoctorHomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CatalogTests extends ChLoginBaseTest {
    private static final Logger logger = LogManager.getLogger(CatalogTests.class);


    @Test(dataProvider = "existing-admin-doctor-patient", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyAdminUploadEditCatalog(Admin admin,Patient patient, Doctor doctor) {
        logger.info("Start Test");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        AdminHomePage adminHomepage = new AdminHomePage(driver);
        String catalogName = adminHomepage.createCatalog(admin);
        
        loginPage.loginAsDoctor(doctor);
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.selectPatient(patient);
        doctorHomePage.sendCatalogToPatientByDoctor(patient, catalogName);

        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        adminHomepage.editAndDeleteCatalog(admin, catalogName);    
    }
    
}
