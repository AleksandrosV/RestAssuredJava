package booksAPITests;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BooksAPITesting {
	private Response response;
	private static final String API_BOOKS_URL = "https://simple-books-api.glitch.me/books";
	private static final long VALID_RESPONSE_CODE = 200;
	private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
	private static final long ACCEPTABLE_RESPONSE_TIME = 1000; // milliseconds

	@BeforeMethod
	public void setup() {
		String accessToken = AccessTokenHelper.getAccessToken();
		response = given().header("Authorization", "Bearer " + accessToken).when().get(API_BOOKS_URL);
	}

	// Test to get the list of books and validate the response
	@Test
	public void testGetBooks() {
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, VALID_RESPONSE_CODE, "Status code is not 200");
	}

	// Verify API Books Response Fields
	@Test
	public void testBooksResponseFields() {
		JsonPath jsonPath = response.jsonPath();
		List<Map<String, Object>> keys = jsonPath.getList("$");
		for (Map<String, Object> key : keys) {
			Assert.assertTrue(key.containsKey("id"), "id field is missing");
			Assert.assertTrue(key.containsKey("name"), "name field is missing");
			Assert.assertTrue(key.containsKey("type"), "type field is missing");
			Assert.assertTrue(key.containsKey("available"), "available field is missing");
		}
	}

	// Verify that the API response time is within acceptable limits.
	@Test
	public void testBooksResponseTime() {
		long responseTime = response.getTime();
		Assert.assertTrue(responseTime <= ACCEPTABLE_RESPONSE_TIME,
				"Response time is not within limits of:" + ACCEPTABLE_RESPONSE_TIME);
	}

	// Verify API Response Format
	@Test
	public void testBooksResponseFormat() {
		String contentType = response.getContentType();
		Assert.assertEquals(contentType, CONTENT_TYPE_JSON, "Response format is not:" + CONTENT_TYPE_JSON);
	}

	// Test to verify response fields of a specific book
	@Test
	public void testSpecificBookResponseFields() {
		// Obtain access token
		String accessToken = AccessTokenHelper.getAccessToken();
		// ID of the specific book you want to retrieve
		String bookId = "/1";
		String requestUrl = API_BOOKS_URL + bookId;
		// Sending GET request to get details of the specific book
		Response specificBookResponse = given().header("Authorization", "Bearer " + accessToken)
				.contentType(ContentType.JSON).when().get(requestUrl);
		// Verify response status code
		int statusCode = specificBookResponse.getStatusCode();
		Assert.assertEquals(statusCode, VALID_RESPONSE_CODE, "Status code is not:" + VALID_RESPONSE_CODE);
		// Verify response fields of the specific book
		JsonPath jsonPath = specificBookResponse.jsonPath();
		Map<String, Object> bookDetails = jsonPath.getMap("$");
		Assert.assertTrue(bookDetails.containsKey("id"), "id field is missing");
		Assert.assertTrue(bookDetails.containsKey("name"), "name field is missing");
		Assert.assertTrue(bookDetails.containsKey("author"), "author field is missing");
		Assert.assertTrue(bookDetails.containsKey("isbn"), "isbn field is missing");
		Assert.assertTrue(bookDetails.containsKey("type"), "type field is missing");
		Assert.assertTrue(bookDetails.containsKey("price"), "price field is missing");
		Assert.assertTrue(bookDetails.containsKey("current-stock"), "current-stock field is missing");
		Assert.assertTrue(bookDetails.containsKey("available"), "available field is missing");
	}
}
