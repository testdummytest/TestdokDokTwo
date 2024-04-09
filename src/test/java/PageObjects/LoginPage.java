package PageObjects;

import org.openqa.selenium.WebDriver;

import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;

public class LoginPage extends BasePage{
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void activateTheUserBySettingNewPassword(Patient patient, String text) {
        setCodeReceivedToTheNewUser(text);
        setNewPassword(patient.getPassword(), patient.getConfirmPassword());
    }

    public void loginAsPatient(Patient patient) {
        loginAsUser(patient.getEmail(), patient.getPassword());
    }

    public void loginAsDoctor(Doctor doctor) {
        loginAsUser(doctor.getEmail(), doctor.getPassword());
    }

    //new added for doc assistant activation
    public void activateTheDocAssistantBySettingNewPassword(Doctor doctor, String text) {
        setCodeReceivedToTheNewUser(text);
        setNewPassword(doctor.getPassword(), doctor.getConfirmPassword());
    }

    //for admin
    public void loginAsAdmin(Admin admin) {
        loginAsUser(admin.getEmail(), admin.getPassword());
    }

}
