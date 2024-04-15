package UiRegressionTests.WebTests.DoctorDashboradTests.SalutaDoctorDashboardTests;

import org.testng.annotations.Test;
import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.DoctorHomePage;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DeleteNonActivePatientByDoctorTests extends ChLoginBaseTest {
    private static final Logger logger = LogManager.getLogger(DeleteNonActivePatientByDoctorTests.class);

    @Test(dataProvider = "create-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
    public void createPatientAndDeleteNonActivePatientByDoctor(Patient patient, Admin admin, Doctor doctor) {
        logger.info("Start Test");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);
        //create patient and logout from doctor
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.createAndDeletePatientCallbackFunc(patient);

    }

}
