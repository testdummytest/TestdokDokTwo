package PageObjects;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import org.openqa.selenium.WebDriver;
import Framework.DataProviderClass;
// import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIsPage extends BasePage{

    public APIsPage(WebDriver driver) {
        super(driver);
    }

    public String getTokenGeneratedByPassword() {
        DataProviderClass.getEnvUrl();
        baseURI = DataProviderClass.ApiUrl;
        // baseURI = "https://auth-qa.dev.docdok.ch";
        RequestSpecification request = given();

        request.header("Content-Type", "application/x-www-form-urlencoded").
                formParam("grant_type", "password").
                formParam("client_id", "browser").
                formParam("username", DataProviderClass.DoctorUname).
                formParam("password", DataProviderClass.DoctorPass);

        Response response = request.post("/auth/realms/docdok/protocol/openid-connect/token");
//        response.prettyPrint();
        String jsonString = response.getBody().asString();
        String tokenGenerated = JsonPath.from(jsonString).get("access_token");
        return tokenGenerated;
    }

    public String getTokenGeneratedByPassword_Admin() {
        DataProviderClass.getEnvUrl();
        baseURI = DataProviderClass.ApiUrl;
        // baseURI = "https://auth-qa.dev.docdok.ch";
        RequestSpecification request = given();
       
        request.header("Content-Type", "application/x-www-form-urlencoded").
                formParam("grant_type", "password").
                formParam("client_id", "browser").
                formParam("username", DataProviderClass.AdminUname).
                formParam("password", DataProviderClass.AdminPass);

        Response response = request.post("/auth/realms/docdok/protocol/openid-connect/token");
        // response.prettyPrint();
        String jsonString = response.getBody().asString();
        String tokenGenerated = JsonPath.from(jsonString).get("access_token");
        return tokenGenerated;
    }

    public String getTokenGeneratedByClientSecret() {
        DataProviderClass.getEnvUrl();
        baseURI = DataProviderClass.ApiUrl;
        // baseURI = "https://auth-qa.dev.docdok.ch";
        RequestSpecification request = given();

        request.header("Content-Type", "application/x-www-form-urlencoded").
                formParam("grant_type", "client_credentials").
                formParam("client_id", "piswebserver").
                formParam("client_secret", "ed0c4566-9a07-4835-bfcd-b6de34b90d84");

        Response response = request.post("/auth/realms/docdok/protocol/openid-connect/token");
//        response.prettyPrint();
        String jsonString = response.getBody().asString();
        String tokenGenerated = JsonPath.from(jsonString).get("access_token");
        return tokenGenerated;
    }

    public int getSurveysNumber() {
        DataProviderClass.getEnvUrl();
        baseURI = DataProviderClass.EnvUrl;
        // baseURI = "https://auth-qa.dev.docdok.ch";
        RequestSpecification request = given();
        DataProviderClass.getProperties();
        request.header("Authorization","Bearer "+getTokenGeneratedByPassword());
        Response response = request.get("/rest/survey/api/patients/"+DataProviderClass.PatientId+"/survey-definitions");
        // response.prettyPrint();
        // int surveysSent = response.path("find.nbSent");
        int surveysSent = response.path("find{it.name == '"+DataProviderClass.SurveyName+"'}.nbSent");
        return surveysSent;
    }
}
