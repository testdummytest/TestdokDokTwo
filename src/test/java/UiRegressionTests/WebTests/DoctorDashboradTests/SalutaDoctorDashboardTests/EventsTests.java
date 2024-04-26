package UiRegressionTests.WebTests.DoctorDashboradTests.SalutaDoctorDashboardTests;

import org.testng.annotations.Test;

import Entities.Doctor;
import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.DoctorHomePage;
import PageObjects.LoginPage;
import UiRegressionTests.ChLoginBaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventsTests extends ChLoginBaseTest {
    private static final Logger logger = LogManager.getLogger(EventsTests.class);

    @Test(dataProvider = "login-doctor-And-patient-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyEventValidation(Doctor doctor, Patient patient) {
        logger.info("Start Test");
        LoginPage loginPage= new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.selectPatient(patient);
        doctorHomePage.addAnEventForTodayDate();
        doctorHomePage.verifyThatTheEventAddedSuccessfully();
        doctorHomePage.updateEvent();
        doctorHomePage.verifyThatTheEventUpdatedSuccessfully();
        doctorHomePage.deleteEventAndVerifyThatTheEventIsDeleted();
    }

    @Test(dataProvider = "login-doctor-And-patient-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyEventStatus(Doctor doctor, Patient patient) {
        logger.info("Start Test");

        LoginPage loginPage= new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.selectPatient(patient);
        doctorHomePage.addAnEventInPastTime();
        doctorHomePage.verifyEventStatus();
    }

    @Test(dataProvider = "login-doctor-And-patient-data", dataProviderClass = DataProviderClass.class)
    public void shouldVerifyThatTheMonthlyWeeklyAndDailyViewsLookLikeTheyShouldBe(Doctor doctor, Patient patient) {
        logger.info("Start Test");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsDoctor(doctor);
        DoctorHomePage doctorHomePage = new DoctorHomePage(driver);
        doctorHomePage.selectPatient(patient);
        doctorHomePage.verifyTheTheWeeklyViewIsAsExpected();
        doctorHomePage.verifyTheTheDailyViewIsAsExpected();
        doctorHomePage.verifyTheTheMonthlyViewIsAsExpected();
    }


}
