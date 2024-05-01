package booksAPITests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BooksAPITesting {
	private Response response;
	private static final String API_BOOKS_URL = "https://simple-books-api.glitch.me/books";
	private static final long VALID_RESPONSE_CODE = 200;
	private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";

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
		String contentType = response.getContentType();
		Assert.assertEquals(contentType, CONTENT_TYPE_JSON, "Response format is not JSON");
	}

	// Test to get details of a specific book
	@Test
	public void testGetSpecificBook() {
		// ID of the specific book you want to retrieve
		String bookId = "/1"; // Change to the actual book ID
		String requestUrl = API_BOOKS_URL + bookId;

		// Sending GET request to get details of the specific book
		Response specificBookResponse = given().contentType(ContentType.JSON).when().get(requestUrl);
		System.out.println(specificBookResponse.getBody().asString());
		int statusCode = specificBookResponse.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Status code is not 200");
	}
}
