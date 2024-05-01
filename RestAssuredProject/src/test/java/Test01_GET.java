import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Test01_GET {

	@Test
	void test_01() {
		Response response = RestAssured.get("https://simple-books-api.glitch.me/books");

		System.out.println(response.asString());
		System.out.println(response.getBody().asString());
		System.out.println(response.getStatusCode());
		System.out.println(response.getStatusLine());
		System.out.println(response.getHeader("content-type"));
		System.out.println(response.getTime());
	}
	
	@Test
	void testResponseFields() {
	    Response response = RestAssured.get("https://simple-books-api.glitch.me/books");

	    JsonPath jsonPath = response.jsonPath();
	    List<Map<String, Object>> books = jsonPath.getList("$");

	    for (Map<String, Object> book : books) {
	        System.out.println("Book ID: " + book.get("id"));
	        System.out.println("Book Name: " + book.get("name"));
	        System.out.println("Book Type: " + book.get("type"));
	        System.out.println("Book Availability: " + book.get("available"));

	        Assert.assertTrue(book.containsKey("id"), "id field is missing");
	        Assert.assertTrue(book.containsKey("name"), "name field is missing");
	        Assert.assertTrue(book.containsKey("type"), "type field is missing");
	        Assert.assertTrue(book.containsKey("available"), "available field is missing");
	    }
	}

}
