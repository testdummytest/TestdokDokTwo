// package UiRegressionTests.WebTests.DoctorDashboradTests.HyCareDoctorDashboardTests;

// import org.testng.annotations.Test;

// import Entities.Admin;
// import Entities.Doctor;
// import Entities.Patient;
// import Framework.DataProviderClass;
// import PageObjects.DoctorHomePage;
// import PageObjects.LoginPage;
// import PageObjects.PatientHomePage;
// import PageObjects.TestingUtilPage;
// import UiRegressionTests.ChLoginBaseTest;

// public class createPatientTests extends ChLoginBaseTest {

//     @Test(dataProvider = "create-hycare-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
//     public void shouldVerifyThatTheDoctorCanCreatePatientSuccessfully(Patient patient, Admin admin, Doctor doctor) {
//         LoginPage loginPage = new LoginPage(driver);
//         loginPage.loginAsDoctor(doctor);
//         DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
//         doctorHomePage.createPatientByHyCareDoctor(patient);
//         TestingUtilPage testingUtilPage = new TestingUtilPage(driver);
//         String tmpPassword = testingUtilPage.openActivationUrlByTestingUtil(admin);
//         loginPage.activateTheUserBySettingNewPassword(patient, tmpPassword);
//         PatientHomePage patientHomePage = new PatientHomePage(driver);
//         patientHomePage.verifyThatTheUserLoggedInSuccessfully();

//     }
// }
