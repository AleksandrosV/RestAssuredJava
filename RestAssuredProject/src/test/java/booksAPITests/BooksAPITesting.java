package booksAPITests;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BooksAPITesting {
	private Response response;
	private static final String API_BOOKS_URL = "https://simple-books-api.glitch.me/books";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String AUTHOR = "author";
	// private static final String ISBN = "isbn";
	private static final String TYPE = "type";
	private static final String PRICE = "price";
	private static final String CURRENT_STOCK = "current-stock";
	private static final String AVAILABLE = "available";

	@BeforeMethod
	public void setup() {
		String accessToken = AccessTokenHelper.getAccessToken();
		response = given().header("Authorization", "Bearer " + accessToken).when().get(API_BOOKS_URL);
	}

	// Verify API Books response code
	@Test
	public void testBooksResponseCode() {
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, AccessTokenHelper.VALID_RESPONSE_CODE,
				"Status code is not:" + AccessTokenHelper.VALID_RESPONSE_CODE);
	}

	// Verify API Books response message
	@Test
	public void testBooksResponseMessage() {
		String statusMessage = response.getStatusLine();
		Assert.assertEquals(statusMessage, AccessTokenHelper.SUCCESS_MESSAGE,
				"Status message is not: " + AccessTokenHelper.SUCCESS_MESSAGE);
	}

	// Verify API Books response format
	@Test
	public void testBooksResponseFormat() {
		String contentType = response.getContentType();
		Assert.assertEquals(contentType, AccessTokenHelper.CONTENT_TYPE_JSON,
				"Response format is not:" + AccessTokenHelper.CONTENT_TYPE_JSON);
	}

	// Verify that the API Books response time is within acceptable limits
	@Test
	public void testBooksResponseTime() {
		long responseTime = response.getTime();
		Assert.assertTrue(responseTime <= AccessTokenHelper.ACCEPTABLE_RESPONSE_TIME,
				"Response time is not within limits of:" + AccessTokenHelper.ACCEPTABLE_RESPONSE_TIME);
	}

	// Verify API Books response fields
	@Test
	public void testBooksResponseFields() {
		JsonPath jsonPath = response.jsonPath();
		List<Map<String, Object>> keys = jsonPath.getList("$");
		for (Map<String, Object> key : keys) {
			Assert.assertTrue(key.containsKey(ID), ID + " field is missing");
			Assert.assertTrue(key.containsKey(NAME), NAME + " field is missing");
			Assert.assertTrue(key.containsKey(TYPE), TYPE + " field is missing");
			Assert.assertTrue(key.containsKey(AVAILABLE), AVAILABLE + " field is missing");
		}
	}

	// Verify API Books response fields of a specific book
	@Test
	public void testSpecificBookResponseFields() {
		// Obtain access token
		String accessToken = AccessTokenHelper.getAccessToken();
		// Generate a random book ID from 1 to 6
		Random random = new Random();
		int randomBookId = random.nextInt(6) + 1;
		String bookId = "/" + randomBookId;
		String requestUrl = API_BOOKS_URL + bookId;
		// Sending GET request to get details of the specific book
		Response specificBookResponse = given().header("Authorization", "Bearer " + accessToken)
				.contentType(ContentType.JSON).when().get(requestUrl);
		// Verify response status code
		int statusCode = specificBookResponse.getStatusCode();
		Assert.assertEquals(statusCode, AccessTokenHelper.VALID_RESPONSE_CODE,
				"Status code is not:" + AccessTokenHelper.VALID_RESPONSE_CODE);
		// Verify response fields of the specific book
		JsonPath jsonPath = specificBookResponse.jsonPath();

		Map<String, Object> bookDetails = jsonPath.getMap("$");
		System.out.println(bookDetails);
		Assert.assertTrue(bookDetails.containsKey(ID), ID + " field is missing");
		Assert.assertTrue(bookDetails.containsKey(NAME), NAME + " field is missing");
		Assert.assertTrue(bookDetails.containsKey(AUTHOR), AUTHOR + " field is missing");
		// Assert.assertTrue(bookDetails.containsKey(ISBN), ISBN + " field is missing");
		Assert.assertTrue(bookDetails.containsKey(TYPE), TYPE + " field is missing");
		Assert.assertTrue(bookDetails.containsKey(PRICE), PRICE + " field is missing");
		Assert.assertTrue(bookDetails.containsKey(CURRENT_STOCK), CURRENT_STOCK + " field is missing");
		Assert.assertTrue(bookDetails.containsKey(AVAILABLE), AVAILABLE + " field is missing");
	}
}
