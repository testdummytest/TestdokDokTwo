package Framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

import Entities.Admin;
import Entities.Doctor;
import Entities.Patient;
import java.util.Properties; 

public class DataProviderClass {
    public static String AdminUname;
    public static String AdminPass;
    public static String SalutaDoctorUname;
    public static String SalutaDoctorPass;
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

    public static void getProperties() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("config/config-Demo.properties"));
            String getEnvName = System.getProperty("env");

            if("QA".equals(getEnvName)){
                
                AdminUname = prop.getProperty("qaadminUsername");
                AdminPass = prop.getProperty("qaadminPassword");
                SalutaDoctorUname = prop.getProperty("qaSalutadoctorUsername");
                SalutaDoctorPass = prop.getProperty("qaSalutadoctorPassword");
                HycareDoctorUname = prop.getProperty("qaHycaredoctorUsername");
                HycareDoctorPass = prop.getProperty("qaHycaredoctorPassword");
                MobileHycareDoctorUname = prop.getProperty("qaHycaredoctorUsernameMobile");
                MobileHycareDoctorPass = prop.getProperty("qaHycaredoctorPasswordMobile");
                PatientUname = prop.getProperty("qapatientUsername");
                PatientPass = prop.getProperty("qapatientPassword");
                MobilePatientUname = prop.getProperty("qapatientUsernameMobile");
                MobilePatientPass = prop.getProperty("qapatientPasswordMobile");
                ClinicId = prop.getProperty("qaClinicId");

            
            }
            else if("STAGE".equals(getEnvName)){
                
                AdminUname = prop.getProperty("stageadminUsername");
                AdminPass = prop.getProperty("stageadminPassword");
                SalutaDoctorUname = prop.getProperty("stageSalutadoctorUsername");
                SalutaDoctorPass = prop.getProperty("stageSalutadoctorPassword");
                HycareDoctorUname = prop.getProperty("stageHycaredoctorUsername");
                HycareDoctorPass = prop.getProperty("stageHycaredoctorPassword");
                MobileHycareDoctorUname = prop.getProperty("stageHycaredoctorUsernameMobile");
                MobileHycareDoctorPass = prop.getProperty("stageHycaredoctorPasswordMobile");
                PatientUname = prop.getProperty("stagepatientUsername");
                PatientPass = prop.getProperty("stagepatientPassword");
                MobilePatientUname = prop.getProperty("stagepatientUsernameMobile");
                MobilePatientPass = prop.getProperty("stagepatientPasswordMobile");
                ClinicId = prop.getProperty("stageClinicId");


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

    public static String getRandomEmailForSaluta() {
        return String.format("%s@%s", getUniqueId(), "saluta.ch");
    }

    public static String getRandomEmailForVinzenz() {
        return String.format("%s@%s", getUniqueId(), "vinzenz.de");
    }

    public static String getRandomEmailForHyCare() {
        return String.format("%s@%s", getUniqueId(), "HyCare.ch");
    }

    @DataProvider(name = "create-saluta-patient-by-self-Registration-data")
    public static Object[][] getSalutaPatientData() {
        getProperties(); // Load properties
        Object patient = new Patient("automation", "test", Generate.date(25), getRandomEmailForSaluta(),
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
        Object doctor = new Doctor(SalutaDoctorUname, SalutaDoctorPass);
         // Object patient = new Patient("patient@saluta.tests");
         Object patient = new Patient(PatientUname);

        return new Object[][] { { doctor, patient } };
    }

    @DataProvider(name = "create-saluta-patient-by-doctor-data")
    public static Object[][] getSalutaPatientDataByDoctor() {
        getProperties(); // Load properties
        Object patient = new Patient("automation", "test", Generate.date(25), getRandomEmailForSaluta(),
                "+" + getRandomMobileNumber(), "12345678", "12345678", "12345678");
        Object admin = new Admin(AdminUname, AdminPass);
        Object doctor = new Doctor(SalutaDoctorUname, SalutaDoctorPass);
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
    @DataProvider(name = "create-saluta-child-patient-by-doctor-data")
    public static Object[][] getSalutaChildPatientDataByDoctor() {
        getProperties(); // Load properties

        Object patient = new Patient("latestautomation", "test", Generate.date(25),"SALUTATIONS","FIRSTCHILDNAMEONE","LASTCHILDNAMEONE","+" + getRandomMobileNumber(),getRandomEmailForHyCare(),"12345678","12345678","12345678");
        Object admin = new Admin(AdminUname, AdminPass);
        Object doctor = new Doctor(SalutaDoctorUname, SalutaDoctorPass);
        return new Object[][] { { patient, admin, doctor } };
    }//end
    //new for a doctor assistant
    @DataProvider(name = "create-doctor-assistant-by-admin")
    public static Object[][] getSalutaDcotorAssistantData() {
        getProperties(); // Load properties

        Object patient = new Patient("latestautomation", "test", Generate.date(25),"SALUTATIONS","FIRSTCHILDNAMEONE","LASTCHILDNAMEONE","+" + getRandomMobileNumber(),getRandomEmailForHyCare(),"12345678","12345678","12345678");
        Object admin = new Admin(AdminUname, AdminPass,"ADr.","ASSisDOC","testt","+" + getRandomMobileNumber(),getRandomEmailForHyCare());
        Object doctor = new Doctor(SalutaDoctorUname, SalutaDoctorPass,"12345678","12345678");
        return new Object[][] { { patient, admin, doctor } };
    }//end
    
    //new for a physician-doctor
    @DataProvider(name = "create-doctor-physician-by-admin")
    public static Object[][] getSalutaDoctorPhysicianData() {
        getProperties(); // Load properties

        Object patient = new Patient("latestautomation", "test", Generate.date(25),"SALUTATIONS","FIRSTCHILDNAMEONE","LASTCHILDNAMEONE","+" + getRandomMobileNumber(),getRandomEmailForHyCare(),"12345678","12345678","12345678");
        Object admin = new Admin(AdminUname, AdminPass,"PHISIO","PHISIOFour","lasttest","+" + getRandomMobileNumber(),getRandomEmailForHyCare());
        Object doctor = new Doctor(SalutaDoctorUname, SalutaDoctorPass,"12345678","12345678");
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
     public static Object[][] getSalutaClinicData() {
        getProperties(); // Load properties
 
         Object admin = new Admin(AdminUname, AdminPass,"AutomationClinic","+" + getRandomMobileNumber(),"ClinicStreetTwo","888","AhmedabadISAH","111","");
         return new Object[][] { { admin } };
     }//end
}
