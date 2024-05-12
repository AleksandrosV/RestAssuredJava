import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class DataDrivenExamples extends DataForTests {

	@Test(dataProvider = "DataForPost")
	public void test_post(String firstName, String job) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", firstName);
		map.put("job", job);

		JSONObject request = new JSONObject(map);

		given().
			header("Content-Type", "application/json").
			contentType(ContentType.JSON).accept(ContentType.JSON).
			body(request.toJSONString()).
		when().
			post("https://reqres.in/api/users").
		then().
			statusCode(201).
			log().all();
	}
}
