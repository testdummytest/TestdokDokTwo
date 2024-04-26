package UiRegressionTests.MobileTests;

import org.testng.annotations.Test;

import Entities.Patient;
import Framework.DataProviderClass;
import PageObjects.Mobile.MobileHomePage;
import UiRegressionTests.MobileBaseTest;

public class PatientTests extends MobileBaseTest {
    @Test(enabled = false, dataProvider = "existing-hycare-patient-data", dataProviderClass = DataProviderClass.class)
    public void existingHyCarePatientCanLoginSuccessfully(Patient patient) {
        MobileHomePage mobileHomePage = new MobileHomePage(androidDriver);
        mobileHomePage.fillUserNameAndPasswordForAnExistingPatient(patient);
        mobileHomePage.verifyThatTheUserLoginSuccessfully();
        mobileHomePage.verifyDiGA1BoxIsAppearsInTheHomePage();
        mobileHomePage.verifyThatAfterClickingOnTheDIGA1ButtonTheRequiredMessageIsAppeared();
    }

    @Test( enabled = false,dataProvider = "existing-hycare-patient-data", dataProviderClass = DataProviderClass.class)
    public void verifyThatTheTermsAndConditionsIsExistInTheProfileTab(Patient patient) {
        MobileHomePage mobileHomePage = new MobileHomePage(androidDriver);
        mobileHomePage.fillUserNameAndPasswordForAnExistingPatient(patient);
        mobileHomePage.verifyTermsAndConditionsIsExistInTheProfileTab();
    }

    @Test(enabled = true, dataProvider = "existing-hycare-patient-data", dataProviderClass = DataProviderClass.class)
    public void verifyThatCanLogoutSuccessfullyFromTheApp(Patient patient) {
        System.out.println("called case");
        MobileHomePage mobileHomePage = new MobileHomePage(androidDriver);
        mobileHomePage.fillUserNameAndPasswordForAnExistingPatient(patient);
        mobileHomePage.logoutFromTheApp();
        mobileHomePage.verifyThatCanLogoutSuccessfullyFromTheApp();
    }

}
