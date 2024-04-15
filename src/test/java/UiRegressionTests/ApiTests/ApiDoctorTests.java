package UiRegressionTests.ApiTests;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.Test;
import Framework.DataProviderClass;
import PageObjects.APIsPage;
import UiRegressionTests.ChLoginBaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiDoctorTests extends ChLoginBaseTest {

    @Test(dataProviderClass = DataProviderClass.class)
    public void createDoctorVaiApiTests() {
        // Define the base URI
        DataProviderClass.getProperties();
        RestAssured.baseURI = DataProviderClass.EnvUrl;
        // RestAssured.baseURI = "https://qa.dev.docdok.ch";
        // Define the JSON payload
        String jsonBody = "{\"salutation\":\"Mr\",\"firstName\":\""+DataProviderClass.getUniqueId()+"\",\"lastName\":\"QA12\",\"gender\":\"MALE\",\"mobileNumber\":\"+"+DataProviderClass.getRandomMobileNumber()+"\",\"email\":\""+DataProviderClass.getRandomEmailforAutomation()+"\",\"langKey\":\"en\",\"clinicId\":\""+DataProviderClass.ClinicId+"\",\"class\":\"PHYSICIAN\"}";
        APIsPage apiPage = new APIsPage(driver);
        RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Bearer " + apiPage.getTokenGeneratedByPassword_Admin()) // Replace with your actual
                .header("Accept", "*/*")
                .body(jsonBody)
                .post("/rest/healthrelation/api/healthcareprofessionals")
                .then()
                .log().all() // Log the response details
                .assertThat().statusCode(anyOf(is(200),is(201))); 
    }

}
