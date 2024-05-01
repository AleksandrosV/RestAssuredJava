package booksAPITests;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AccessTokenHelper {

	private static final String API_CLIENT_URL = "https://simple-books-api.glitch.me/api-clients/";

	public static String getAccessToken() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("clientName", "Alex");
		requestParams.put("clientEmail", "Alex@gmail.com");

		Response response = given().contentType(ContentType.JSON).body(requestParams.toString()).when()
				.post(API_CLIENT_URL).then().extract().response();

		return response.jsonPath().getString("accessToken");
	}
}
