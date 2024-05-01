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
	void testResponseFormat() {
		String contentType = response.getContentType();
		Assert.assertEquals(contentType, CONTENT_TYPE, "Response format is not:" + CONTENT_TYPE);
	}

	// Verify that the API response time is within acceptable limits.
	@Test
	void testResponseTime() {
		long responseTime = response.getTime();
		Assert.assertTrue(responseTime <= ACCEPTABLE_RESPONSE_TIME,
				"Response time is not within limits of:" + ACCEPTABLE_RESPONSE_TIME);
	}

	// Verify API Response Fields
	@Test
	void testResponseFields() {
		JsonPath jsonPath = response.jsonPath();
		Assert.assertNotNull(jsonPath.get("status"), "Status field is missing");
	}

	// Test to get the list of books and print the response body to the console
	@Test
	public void testGetBooksAndPrintResponse() {
		// Optional query parameters
		String type = "non-fiction"; // Change as needed
		int limit = 20; // Change as needed

		// Building the request URL with query parameters
		String requestUrl = "https://simple-books-api.glitch.me/books";
		if (type != null) {
			requestUrl += "?type=" + type;
		}
		if (limit > 0 && limit <= 20) {
			requestUrl += (type != null ? "&" : "?") + "limit=" + limit;
		}

		// Sending GET request to /books endpoint
		Response response = given().contentType(ContentType.JSON).when().get(requestUrl);

		// Printing the response body to the console
		System.out.println("Response Body:");
		System.out.println(response.getBody().asString());

		// Validating response
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Status code is not 200");

		// Additional assertions can be added to validate the response body, such as
		// book details, format, etc.
	}

	// Test to get details of a specific book
	@Test
	public void testGetSpecificBook() {
		// ID of the specific book you want to retrieve
		String bookId = "1"; // Change to the actual book ID

		// Building the request URL for the specific book
		String requestUrl = "https://simple-books-api.glitch.me/books/" + bookId;

		// Sending GET request to get details of the specific book
		Response response = given().contentType(ContentType.JSON).when().get(requestUrl);
		
		System.out.println("Response Body:");
		System.out.println(response.getBody().asString());

		// Validating response
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Status code is not 200");

		// Additional assertions can be added to validate the response body of the
		// specific book
	}
}
