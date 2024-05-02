package booksAPITests;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AccessTokenHelper {
	private static final String API_CLIENT_URL = "https://simple-books-api.glitch.me/api-clients/";
	public static final String SUCCESS_MESSAGE = "HTTP/1.1 200 OK";
	public static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
	public static final long VALID_RESPONSE_CODE = 200;
	public static final long ACCEPTABLE_RESPONSE_TIME = 1000; // milliseconds

	public static String getAccessToken() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("clientName", "Alex");
		requestParams.put("clientEmail", "Alex@gmail.com");

		Response response = given().contentType(ContentType.JSON).body(requestParams.toString()).when()
				.post(API_CLIENT_URL).then().extract().response();

		return response.jsonPath().getString("accessToken");
	}
}
