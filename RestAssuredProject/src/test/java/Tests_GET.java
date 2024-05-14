import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class Tests_GET {

	@Test
	public void test_1() {
		given().
			get("https://reqres.in/api/users?page=2").
		then().
			statusCode(200).
			body("data.id[0]", equalTo(7)).
			body("data.first_name", hasItems("Michael", "Lindsay")).
			log().all();
	}

    @Test
    public void testCreateUserSuccess() {
        String requestBody = """
            {
                "name": "morpheus",
                "job": "leader",
                "age": 21
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("https://reqres.in/api/users") // Replace with your endpoint
        .then()
            .statusCode(201)
            .body("name", equalTo("morpheus"))
            .body("job", equalTo("leader"))
            .body("id", notNullValue())
            .body("createdAt", notNullValue());
    }

    @Test
    public void testCreateUserWithoutAge() {
        String requestBody = """
            {
                "name": "morpheus",
                "job": "leader"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("https://reqres.in/api/users")
        .then()
            .statusCode(201)
            .body("name", equalTo("morpheus"))
            .body("job", equalTo("leader"))
            .body("id", notNullValue())
            .body("createdAt", notNullValue());
    }

    @Test
    public void testCreateUserMissingJob() {
        String requestBody = """
            {
                "name": "morpheus",
                "age": 21
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("https://reqres.in/api/users")
        .then()
            .statusCode(400); // Assuming 400 is returned for missing required fields
    }
}
