package apiTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class NegativeScenariosTest {
	// Verify API response for invalid request method
	@Test
	void testInvalidRequestMethod() {

		// Make the API request using the invalid method
		Response response = RestAssured.patch("https://reqres.in/api/users?page=2");

		// Extract the response status code
		int statusCode = response.getStatusCode();
		String statusMessage = response.getStatusLine();
		System.out.println("Response code: " + statusCode);
		System.out.println("Response message: " + statusMessage);

		// Validate that the response status code indicates method not allowed
		Assert.assertEquals(statusCode, 404, "Response status code does not indicate method not allowed");
	}

}
