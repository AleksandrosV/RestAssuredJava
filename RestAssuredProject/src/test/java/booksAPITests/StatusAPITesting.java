package booksAPITests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StatusAPITesting {
	private Response response;
	private static final String API_STATUS_URL = "https://simple-books-api.glitch.me/status";

	@BeforeMethod
	public void setup() {
		String accessToken = AccessTokenHelper.getAccessToken();
		response = given().header("Authorization", "Bearer " + accessToken).when().get(API_STATUS_URL);
	}

	// Verify API Status response code
	@Test
	public void testStatusResponseCode() {
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, AccessTokenHelper.VALID_RESPONSE_CODE,
				"Status code is not: " + AccessTokenHelper.VALID_RESPONSE_CODE);
	}

	// Verify API Status response message
	@Test
	public void testStatusResponseMessage() {
		String statusMessage = response.getStatusLine();
		Assert.assertEquals(statusMessage, AccessTokenHelper.SUCCESS_MESSAGE,
				"Status message is not: " + AccessTokenHelper.SUCCESS_MESSAGE);
	}

	// Verify API Status response format
	@Test
	public void testStatusResponseFormat() {
		String contentType = response.getContentType();
		Assert.assertEquals(contentType, AccessTokenHelper.CONTENT_TYPE_JSON,
				"Response format is not:" + AccessTokenHelper.CONTENT_TYPE_JSON);
	}

	// Verify that the API Status response time is within acceptable limits
	@Test
	public void testStatusResponseTime() {
		long responseTime = response.getTime();
		Assert.assertTrue(responseTime <= AccessTokenHelper.ACCEPTABLE_RESPONSE_TIME,
				"Response time is not within limits of:" + AccessTokenHelper.ACCEPTABLE_RESPONSE_TIME);
	}

	// Verify API Status response fields
	@Test
	public void testStatusResponseFields() {
		JsonPath jsonPath = response.jsonPath();
		Assert.assertNotNull(jsonPath.get("status"), "Status field is missing");
	}
}
