import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BooksAPITesting {

	private String accessToken;
	private Response response;
	private static final String API_CLIENT_URL = "https://simple-books-api.glitch.me/api-clients/";
	private static final String API_STATUS_URL = "https://simple-books-api.glitch.me/status";
	private static final long ACCEPTABLE_RESPONSE_TIME = 1000; // milliseconds
	private static final long VALID_RESPONSE_CODE = 200;
	private static final String SUCCESS_MESSAGE = "HTTP/1.1 200 OK";
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";

	// Method to get access token
	public String getAccessToken() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("clientName", "Alex");
		requestParams.put("clientEmail", "Alex@gmail.com");

		Response response = given().contentType(ContentType.JSON).body(requestParams.toString()).when()
				.post(API_CLIENT_URL).then().extract().response();

		accessToken = response.jsonPath().getString("accessToken");
		return accessToken;
	}

	private Response sendRequest(String accessToken) {
		if (accessToken == null) {
			accessToken = getAccessToken();
		}
		return given().header("Authorization", "Bearer " + accessToken).when().get(API_STATUS_URL);
	}

	@BeforeMethod
	public void setup() {
		response = sendRequest(accessToken);
	}

	// Verify API status code
	@Test
	public void testAPIStatusCode() {
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, VALID_RESPONSE_CODE, "Status code is not 200");
	}

	// Verify API status message
	@Test
	public void testAPIStatusMessage() {
		String statusMessage = response.getStatusLine();
		Assert.assertEquals(statusMessage, SUCCESS_MESSAGE, "Status message does not contain 'OK'");
	}

	// Verify API Response Format
	@Test
	void testResponseFormat() {
		String contentType = response.getContentType();
		Assert.assertEquals(contentType, CONTENT_TYPE, "Response format is not JSON");
	}

	// Verify that the API response time is within acceptable limits.
	@Test
	void testResponseTime() {
		long responseTime = response.getTime();
		Assert.assertTrue(responseTime <= ACCEPTABLE_RESPONSE_TIME, "Response time is not within acceptable limits");
	}

	// Verify API Response Fields
	@Test
	void testResponseFields() {
		JsonPath jsonPath = response.jsonPath();
		Assert.assertNotNull(jsonPath.get("status"), "Status field is missing");
	}
}
