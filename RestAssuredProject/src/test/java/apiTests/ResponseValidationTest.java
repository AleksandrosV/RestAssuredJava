package apiTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResponseValidationTest {

	private static final String API_URL = "https://reqres.in/api/users?page=2";
	private static final long ACCEPTABLE_RESPONSE_TIME = 600; // milliseconds

	// Verify API Response Status Code
	@Test
	void testStatusCode() {
		Response response = RestAssured.get(API_URL);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Status code is not 200 OK");
	}

	// Verify API Response Format
	@Test
	void testResponseFormat() {
		Response response = RestAssured.get(API_URL);
		String contentType = response.getContentType();
		Assert.assertEquals(contentType, "application/json; charset=utf-8", "Response format is not JSON");
	}

	// Verify API Response Fields
	@Test
	void testResponseFields() {
		Response response = RestAssured.get(API_URL);
		JsonPath jsonPath = response.jsonPath();

		// Verify presence of expected fields
		Assert.assertNotNull(jsonPath.get("page"), "Page field is missing");
		Assert.assertNotNull(jsonPath.get("per_page"), "Per_page field is missing");
		Assert.assertNotNull(jsonPath.get("total"), "Total field is missing");
		Assert.assertNotNull(jsonPath.get("total_pages"), "Total_pages field is missing");
		Assert.assertNotNull(jsonPath.get("data"), "Data field is missing");
	}

	// Verify that the API response time is within acceptable limits.
	@Test
	void testResponseTime() {
		Response response = RestAssured.get(API_URL);
		long responseTime = response.getTime();

		System.out.println("Response time: " + responseTime + " milliseconds");
		Assert.assertTrue(responseTime <= ACCEPTABLE_RESPONSE_TIME, "Response time is not within acceptable limits");
	}

	// Verify that the API response headers are correct.
	@Test
	void testResponseHeaders() {
		Response response = RestAssured.get(API_URL);
		// Verify Content-Type header
		String expectedContentType = "application/json; charset=utf-8";
		String actualContentType = response.getHeader("Content-Type");
		Assert.assertEquals(actualContentType, expectedContentType, "Content-Type header is incorrect");
	}
}
