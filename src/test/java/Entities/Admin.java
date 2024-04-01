package Entities;

public class Admin extends User{
    public Admin(String email, String password) {
        super(email,password);
    }
    public Admin(String email, String password, String lastChildSal, String firstChildName, String lastChildName, String childMobbNo, String childemailis) {
        super(email, password, lastChildSal, firstChildName, lastChildName, childMobbNo, childemailis);
        //setSmsCode(smsCode);
        //setPassword(password);
        //setConfirmPassword(confirmPassword);
    }

    public Admin(String email, String password, String firstChildName, String childMobbNo, String clinicStreetName, String clinicStreetNo, String clinicCity, String clinicZipcode, String blankparam) {
        super(email, password, firstChildName, childMobbNo, clinicStreetName, clinicStreetNo, clinicCity, clinicZipcode, blankparam);
    }
}
