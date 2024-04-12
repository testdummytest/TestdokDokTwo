package UiRegressionTests.WebTests.AdminDashboardTests;

import java.util.logging.Logger;

import org.testng.annotations.Test;

import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;
import UiRegressionTests.loggersetup;
import PageObjects.AdminHomePage;
import PageObjects.DoctorHomePage;

public class CatalogTests extends ChLoginBaseTest {

    private static final Logger logger = loggersetup.getLogger();

    @Test(dataProvider = "existing-admin-doctor-patient", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyAdminUploadEditCatalog(Admin admin,Patient patient, Doctor doctor) {
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        AdminHomePage adminhomepage = new AdminHomePage(driver);
        String catalogName = adminhomepage.createCatalog(admin);
        
        loginPage.loginAsDoctor(doctor);
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.selectPatient(patient);
        doctorHomePage.sendCatalogToPatientByDoctor(patient, catalogName);

        loginPage.loginAsAdmin(admin);
        logger.info("Admin login success");
        adminhomepage.editAndDeleteCatalog(admin, catalogName);    
    }
    
}
