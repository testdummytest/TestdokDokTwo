package UiRegressionTests.WebTests.DoctorDashboradTests.SalutaDoctorDashboardTests;


import org.testng.annotations.Test;

import Entities.Admin;
import Framework.DataProviderClass;
import PageObjects.AdminHomePage;
import PageObjects.DoctorHomePage;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;
import Entities.Doctor;
import Entities.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class SendAppInvitationToPatientTests extends ChLoginBaseTest {

    private static final Logger logger = LogManager.getLogger(SendAppInvitationToPatientTests.class);
 
    //used for send app invitation to patient by doctor
    @Test(dataProvider = "create-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheDoctorSendAppInvitationToPatient(Patient patient, Admin admin, Doctor doctor) {
        logger.info("Start Test");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);

        logger.info("Doctor login success");
        
        //create patient and logout from doctor
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.sendAppInvitationToPatientByDoctor(patient);

    }//end

    //used for send app invitation to patient by admin
    @Test(dataProvider = "create-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheAdminSendAppInvitationToPatient(Patient patient, Admin admin, Doctor doctor) {
        logger.info("Start Test");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);

        logger.info("Admin login success");
        
        //create patient and logout from doctor
        AdminHomePage adminHomePage = new AdminHomePage(driver);
        adminHomePage.sendAppInvitationToPatientByAdmin(patient);

    }//end


    //used for send invitation to the Users tab user by admin
    @Test(dataProvider = "create-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheAdminSendAppInvitationToUsersTabUser(Patient patient, Admin admin, Doctor doctor) {
        logger.info("Start Test");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(admin);

        logger.info("Admin login success");
        
        //create patient and logout from doctor
        AdminHomePage adminHomePage = new AdminHomePage(driver);
        adminHomePage.sendAppInvitationToUsersTabUserByAdmin(patient);

    }//end



    //used for check if app activated then disabled send invitation
    @Test(dataProvider = "create-patient-by-doctor-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatDoctorNotSendAppInvitationToPatientWhichPatientAppActivated(Patient patient, Admin admin, Doctor doctor) {
        logger.info("Start Test");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);

        logger.info("Doctor login success");
        
        //create patient and logout from doctor
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.checkAdminNotSendAppInvitationToAppActivePatient(patient);

    }//end

}
