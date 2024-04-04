package UiRegressionTests.ApiTests;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.Test;

import Framework.DataProviderClass;
import PageObjects.APIsPage;
import UiRegressionTests.ChLoginBaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiPatientTests extends ChLoginBaseTest {

    @Test(dataProviderClass = DataProviderClass.class)
    public void createPatientViaApiTest() {
        // Define the base URI
        DataProviderClass.getProperties();
        RestAssured.baseURI = DataProviderClass.EnvUrl;
        // RestAssured.baseURI = "https://qa.dev.docdok.ch";

        // Define the JSON payload
        String jsonBody = "{\"class\":\"PATIENT\",\"firstName\":\""+DataProviderClass.getUniqueId()+"\",\"lastName\":\"345\",\"birthdate\":\"1998-01-08T00:30:00.000Z\",\"email\":\""+DataProviderClass.getRandomEmailforAutomation()+"\",\"mobileNumber\":\"+"+DataProviderClass.getRandomMobileNumber()+"\",\"salutation\":\"Mr\",\"type\":\"PATIENTSELF\",\"gender\":\"MALE\",\"langKey\":\"en\"}";
        APIsPage apiPage = new APIsPage(driver);

        RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Bearer " + apiPage.getTokenGeneratedByPassword()) // Replace with your actual
                .header("Accept", "*/*")
                .body(jsonBody)
                .post("/rest/healthrelation/api/patients")
                .then()
                .log().all() // Log the response details
                .assertThat().statusCode(anyOf(is(200),is(201)));
    }

}
