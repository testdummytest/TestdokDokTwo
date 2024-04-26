package Framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import java.util.Properties; 
import java.util.Base64;


public class DataProviderClass {
    public static String AdminUname;
    public static String AdminPass;
    public static String DoctorUname;
    public static String DoctorPass;
    public static String HycareDoctorUname;
    public static String HycareDoctorPass;
    public static String MobileHycareDoctorUname;
    public static String MobileHycareDoctorPass;
    public static String PatientUname;
    public static String PatientPass;
    public static String MobilePatientUname;
    public static String MobilePatientPass;
    public static String EnvUrl;
    public static String ApiUrl;
    public static String radioBtnId;
    public static String activationUrlUtilityPage;
    public static String ClinicId;
    public static String PatientId;
    public static String SurveyName;
    public static String StudyName;
    public static String StudySurvey;

    private static final String AES_ALGORITHM = "AES";
    private static final String SECRET_KEY = "abcd123456789012";

    public static void getProperties() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("config/config-Demo.properties"));
            String getEnvName = System.getProperty("env");

            if("QA".equals(getEnvName)){
                
                AdminUname = prop.getProperty("qaadminUsername");
                String TempAdminPass = prop.getProperty("qaadminPassword");
                AdminPass = decrypt(TempAdminPass);
                DoctorUname = prop.getProperty("qadoctorUsername");
                String TempDoctorPass = prop.getProperty("qadoctorPassword");
                DoctorPass = decrypt(TempDoctorPass);
                HycareDoctorUname = prop.getProperty("qaHycaredoctorUsername");
                HycareDoctorPass = prop.getProperty("qaHycaredoctorPassword");
                MobileHycareDoctorUname = prop.getProperty("qaHycaredoctorUsernameMobile");
                MobileHycareDoctorPass = prop.getProperty("qaHycaredoctorPasswordMobile");
                PatientUname = prop.getProperty("qapatientUsername");
                String TempPatientPass = prop.getProperty("qapatientPassword");
                PatientPass = decrypt(TempPatientPass);
                MobilePatientUname = prop.getProperty("qapatientUsernameMobile");
                MobilePatientPass = prop.getProperty("qapatientPasswordMobile");
                ClinicId = prop.getProperty("qaClinicId");
                PatientId = prop.getProperty("qaPatientId");
                SurveyName = prop.getProperty("qaSurvey");
                StudySurvey = prop.getProperty("qaStudySurvey");
                StudyName = prop.getProperty("qaStudy");

            
            }
            else if("STAGE".equals(getEnvName)){
                
                AdminUname = prop.getProperty("stageadminUsername");
                String TempAdminPass = prop.getProperty("stageadminPassword");
                AdminPass = decrypt(TempAdminPass);
                DoctorUname = prop.getProperty("stagedoctorUsername");
                String TempDoctorPass = prop.getProperty("stagedoctorPassword");
                DoctorPass = decrypt(TempDoctorPass);
                HycareDoctorUname = prop.getProperty("stageHycaredoctorUsername");
                HycareDoctorPass = prop.getProperty("stageHycaredoctorPassword");
                MobileHycareDoctorUname = prop.getProperty("stageHycaredoctorUsernameMobile");
                MobileHycareDoctorPass = prop.getProperty("stageHycaredoctorPasswordMobile");
                PatientUname = prop.getProperty("stagepatientUsername");
                String TempPatientPass = prop.getProperty("stagepatientPassword");
                PatientPass = decrypt(TempPatientPass);
                MobilePatientUname = prop.getProperty("stagepatientUsernameMobile");
                MobilePatientPass = prop.getProperty("stagepatientPasswordMobile");
                ClinicId = prop.getProperty("stageClinicId");
                PatientId = prop.getProperty("stagePatientId");
                SurveyName = prop.getProperty("stageSurvey");
                StudySurvey = prop.getProperty("stageStudySurvey");
                StudyName = prop.getProperty("stageStudy");


            }else{

                MobilePatientUname = prop.getProperty("prodpatientUsernameMobile");
                MobilePatientPass = prop.getProperty("prodpatientPasswordMobile");
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getEnvUrl() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("config/config-Demo.properties"));
            String getEnvName = System.getProperty("env");
            
            if("QA".equals(getEnvName)){
                EnvUrl = prop.getProperty("qaEnvUrl");
                ApiUrl = prop.getProperty("qaApiURL");
                activationUrlUtilityPage = "//a[contains(@href, 'https://qa.dev.docdok.ch/rest/user/api/users/onboarding/?token')]";
            }else if("STAGE".equals(getEnvName)){
                EnvUrl = prop.getProperty("stageEnvUrl");
                ApiUrl = prop.getProperty("stageApiURL");
                activationUrlUtilityPage = "//a[contains(@href, 'https://stage.docdok.ch/rest/user/api/users/onboarding/?token')]";
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getutitlityPageRadiobtnVal() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("config/config-Demo.properties"));
            String getEnvName = System.getProperty("env");
            
            if("QA".equals(getEnvName)){
                radioBtnId = "envQA";            
            }else if("STAGE".equals(getEnvName)){
                radioBtnId = "envStage"; 
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String decrypt(String encryptedValue)  {
        try{
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
            return new String(decryptedBytes);
        }catch(Exception e){
            return null;
            }
    }

    public static String getRandomMobileNumber() {
        Random rand = new Random();
        Integer randomNum = 11 + rand.nextInt((999999999 - 11) + 1);
        return randomNum.toString();
    }

    public static String randomNumberGetWithPlusFormat() {
        Random rand = new Random();
        Integer randomNum = 11 + rand.nextInt((999999999 - 10) + 1);
        return "+" + randomNum.toString();
    }  

    public static String getRandomString() {
        return RandomStringUtils.randomAlphanumeric(5).toUpperCase();
    }

    public static String getUniqueId() {
        return String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000);
    }

    public static String getRandomEmailforAutomation() {
        return String.format("%s@%s", getUniqueId(), "automation.ch");
    }

    public static String getRandomEmailForVinzenz() {
        return String.format("%s@%s", getUniqueId(), "vinzenz.de");
    }

    public static String getRandomEmailForHyCare() {
        return String.format("%s@%s", getUniqueId(), "HyCare.ch");
    }

    @DataProvider(name = "create-patient-by-self-Registration-data")
    public static Object[][] getPatientData() {
        getProperties(); // Load properties
        Object patient = new Patient("automation", "test", Generate.date(25), getRandomEmailforAutomation(),
                getRandomMobileNumber(), "12345678", "12345678", "12345678");
        Object admin = new Admin(AdminUname, AdminPass);
        return new Object[][] { { patient, admin } };
    }

    @DataProvider(name = "create-Vinzenz-patient-by-self-Registration-data")
    public static Object[][] getVinzenzPatientData() {
        getProperties(); // Load properties
        Object patient = new Patient("automation", "test", Generate.date(25), getRandomEmailForVinzenz(),
                getRandomMobileNumber(), "12345678", "12345678", "12345678", "1234");
        Object admin = new Admin(AdminUname, AdminPass);
        return new Object[][] { { patient, admin } };
    }

    @DataProvider(name = "login-patient-data")
    public static Object[][] getLoginPatientData() {
        getProperties(); // Load properties
        Object patient = new Patient(PatientUname, PatientPass);
        return new Object[][] { { patient } };
    }

    @DataProvider(name = "login-doctor-And-patient-data")
    public static Object[][] getLoginDoctorAndPatientData() {
        getProperties(); // Load properties
        Object doctor = new Doctor(DoctorUname, DoctorPass);
         // Object patient = new Patient("patient@saluta.tests");
         Object patient = new Patient(PatientUname);

        return new Object[][] { { doctor, patient } };
    }

    @DataProvider(name = "create-patient-by-doctor-data")
    public static Object[][] getPatientDataByDoctor() {
        getProperties(); // Load properties
        Object patient = new Patient("automation", "test", Generate.date(25), getRandomEmailforAutomation(),
                "+" + getRandomMobileNumber(), "12345678", "12345678", "12345678");
        Object admin = new Admin(AdminUname, AdminPass);
        Object doctor = new Doctor(DoctorUname, DoctorPass);
        return new Object[][] { { patient, admin, doctor } };
    }

    @DataProvider(name = "create-hycare-patient-by-doctor-data")
    public static Object[][] getHyCarePatientDataByDoctor() {
        getProperties(); // Load properties
        Object patient = new Patient("Amany", "HyCare", Generate.date(25), getRandomEmailForHyCare(),
                "+" + getRandomMobileNumber(), "12345678", "12345678", "12345678");
        Object doctor = new Doctor(HycareDoctorUname, HycareDoctorPass);
        Object admin = new Admin(AdminUname, AdminPass);
        return new Object[][] { { patient, admin, doctor } };
    }


    //new saluta for a child patient
    @DataProvider(name = "create-child-patient-by-doctor-data")
    public static Object[][] getChildPatientDataByDoctor() {
        getProperties(); // Load properties

        Object patient = new Patient("latestautomation", "test", Generate.date(25),"SALUTATIONS","FIRSTCHILDNAMEONE","LASTCHILDNAMEONE","+" + getRandomMobileNumber(),getRandomEmailForHyCare(),"12345678","12345678","12345678");
        Object admin = new Admin(AdminUname, AdminPass);
        Object doctor = new Doctor(DoctorUname, DoctorPass);
        return new Object[][] { { patient, admin, doctor } };
    }//end
    //new for a doctor assistant
    @DataProvider(name = "create-doctor-assistant-by-admin")
    public static Object[][] getDcotorAssistantData() {
        getProperties(); // Load properties

        Object patient = new Patient("latestautomation", "test", Generate.date(25),"SALUTATIONS","FIRSTCHILDNAMEONE","LASTCHILDNAMEONE","+" + getRandomMobileNumber(),getRandomEmailForHyCare(),"12345678","12345678","12345678");
        Object admin = new Admin(AdminUname, AdminPass,"ADr.","ASSisDOC","testt","+" + getRandomMobileNumber(),getRandomEmailForHyCare());
        Object doctor = new Doctor(DoctorUname, DoctorPass,"12345678","12345678");
        return new Object[][] { { patient, admin, doctor } };
    }//end
    
    //new for a physician-doctor
    @DataProvider(name = "create-doctor-physician-by-admin")
    public static Object[][] getDoctorPhysicianData() {
        getProperties(); // Load properties

        Object patient = new Patient("latestautomation", "test", Generate.date(25),"SALUTATIONS","FIRSTCHILDNAMEONE","LASTCHILDNAMEONE","+" + getRandomMobileNumber(),getRandomEmailForHyCare(),"12345678","12345678","12345678");
        Object admin = new Admin(AdminUname, AdminPass,"PHISIO","PHISIOFour","lasttest","+" + getRandomMobileNumber(),getRandomEmailForHyCare());
        Object doctor = new Doctor(DoctorUname, DoctorPass,"12345678","12345678");
        return new Object[][] { { patient, admin, doctor } };
    }//end

    @DataProvider(name = "existing-hycare-patient-and-doctor-data")
    public static Object[][] getExistingHyCarePatientAndDoctorData() {
        getProperties(); // Load properties
        Object patient = new Patient(MobilePatientUname, MobilePatientPass);
        Object doctor = new Doctor(MobileHycareDoctorUname, MobileHycareDoctorPass);
        return new Object[][] { { patient, doctor } };
    }

    @DataProvider(name = "existing-hycare-patient-data")
    public static Object[][] getExistingHyCarePatientData() {
        getProperties(); // Load properties
        Object patient = new Patient(MobilePatientUname, MobilePatientPass);
        return new Object[][] { { patient } };
    }

     //new for admin create clinic
     @DataProvider(name = "create-clinic-by-admin")
     public static Object[][] getClinicData() {
        getProperties(); // Load properties
 
         Object admin = new Admin(AdminUname, AdminPass,"AutomationClinic","+" + getRandomMobileNumber(),"ClinicStreetTwo","888","AhmedabadISAH","111","");
         return new Object[][] { { admin } };
     }//end

     @DataProvider(name = "existing-admin-doctor-patient")
    public static Object[][] getExistingAdminDoctorPatientData() {
        getProperties(); // Load properties
        Object admin = new Admin(AdminUname, AdminPass,"PHISIO","PHISIOFour","lasttest","+" + getRandomMobileNumber(),getRandomEmailForHyCare());
        Object patient = new Patient(PatientUname,PatientPass);
        Object doctor = new Doctor(DoctorUname, DoctorPass,"12345678","12345678");
        return new Object[][] { { admin, patient, doctor } };
    }

    @DataProvider(name = "existing-admin-data")
    public static Object[][] getExistingAdminStudyData() {
        getProperties(); // Load properties
        Object admin = new Admin(AdminUname, AdminPass);
        return new Object[][] { { admin } };
    }
}
