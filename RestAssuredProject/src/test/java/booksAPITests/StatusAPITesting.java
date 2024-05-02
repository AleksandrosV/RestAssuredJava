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
	private static final long RESPONSE_TIME = 1000; // milliseconds
	private static final long VALID_RESPONSE_CODE = 200;
	private static final String SUCCESS_MESSAGE = "HTTP/1.1 200 OK";
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";

	@BeforeMethod
	public void setup() {
		String accessToken = AccessTokenHelper.getAccessToken();
		response = given().header("Authorization", "Bearer " + accessToken).when().get(API_STATUS_URL);
	}

	// Verify API status code
	@Test
	public void testAPIStatusCode() {
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, VALID_RESPONSE_CODE, "Status code is not: " + VALID_RESPONSE_CODE);
	}

	// Verify API status message
	@Test
	public void testAPIStatusMessage() {
		String statusMessage = response.getStatusLine();
		Assert.assertEquals(statusMessage, SUCCESS_MESSAGE, "Status message is not: " + SUCCESS_MESSAGE);
	}

	// Verify API Response Format
	@Test
	public void testStatusResponseFormat() {
		String contentType = response.getContentType();
		Assert.assertEquals(contentType, CONTENT_TYPE, "Response format is not:" + CONTENT_TYPE);
	}

	// Verify that the API response time is within acceptable limits.
	@Test
	public void testStatusResponseTime() {
		long responseTime = response.getTime();
		Assert.assertTrue(responseTime <= RESPONSE_TIME, "Response time is not within limits of:" + RESPONSE_TIME);
	}

	// Verify API Response Fields
	@Test
	public void testStatusResponseFields() {
		JsonPath jsonPath = response.jsonPath();
		Assert.assertNotNull(jsonPath.get("status"), "Status field is missing");
	}
}
