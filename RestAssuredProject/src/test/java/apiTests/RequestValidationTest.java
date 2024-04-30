package apiTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RequestValidationTest {

	// Verify that the API request parameters are correctly passed to the API
	@Test
	void testRequestParameters() {
		// Define request parameters
		String pageParameter = "2";

		// Make the API request with the defined parameters
		Response response = RestAssured.get("https://reqres.in/api/users?page=" + pageParameter);

		// Extract response data or perform validation based on the request parameters
		// For example, you can validate that the "page" parameter in the response
		// matches the expected value
		String responseBody = response.getBody().asString();
		System.out.println("Response Body : " + responseBody);
		Assert.assertTrue(responseBody.contains("\"page\":" + pageParameter),
				"Request parameter 'page' is not correctly passed to the API");
	}
}
