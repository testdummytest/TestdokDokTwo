package UiRegressionTests.ApiTests;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.Test;

import Framework.DataProviderClass;
import PageObjects.APIsPage;
import UiRegressionTests.ChLoginBaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiPatientProxyTests extends ChLoginBaseTest {

    @Test(dataProviderClass = DataProviderClass.class)
    public void createPatientProxyViaApiTest() {
        // Define the base URI
        DataProviderClass.getProperties();
        RestAssured.baseURI = DataProviderClass.EnvUrl;
        // RestAssured.baseURI = "https://qa.dev.docdok.ch";

        // Define the JSON payload
        String jsonBody = "{\"class\":\"PATIENT_PROXY\",\"firstName\":\""+DataProviderClass.getUniqueId()+"\",\"lastName\":\"childcheck\",\"birthdate\":\"1995-01-08T00:30:00.000Z\",\"email\":\""+DataProviderClass.getRandomEmailforAutomation()+"\",\"mobileNumber\":\"+"+DataProviderClass.getRandomMobileNumber()+"\",\"salutation\":\"Mrs\",\"type\":\"PATIENTPROXIED\",\"gender\":\"FEMALE\",\"langKey\":\"en\",\"proxy\":{\"salutation\":\"Mr\",\"firstName\":\""+DataProviderClass.getUniqueId()+"\",\"lastName\":\"Parentproxy\",\"gender\":\"MALE\"}}";
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
