import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class Tests_POST {
	
	@Test
	public void test_1_post() {
		
//			JSONObject request = new JSONObject();
//			request.put("name", "Alex");
//			request.put("jobs", "QA Automation");
//
//			System.out.println(request.toJSONString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "Aleksandros");
		map.put("jobs", "QA Automation");

		JSONObject request = new JSONObject(map);

		given().
			header("Content-Type", "application/json").
			contentType(ContentType.JSON).accept(ContentType.JSON).
			body(request.toJSONString()).
		when().
			post("https://reqres.in/api/users").
		then().
			statusCode(201);
	}

	@Test
	public void test_2_put() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "Aleksandros");
		map.put("jobs", "QA Automation");

		JSONObject request = new JSONObject(map);

		System.out.println(request.toJSONString());
		
		given().
			header("Content-Type", "application/json").
			contentType(ContentType.JSON).accept(ContentType.JSON).
			body(request.toJSONString()).
		when().
			put("https://reqres.in/api/users/2").
		then().
			statusCode(200).
			log().all();
	}
	
	@Test
	public void test_3_patch() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "Aleksandros");
		map.put("jobs", "QA Automation");

		JSONObject request = new JSONObject(map);

		System.out.println(request.toJSONString());
		
		given().
			header("Content-Type", "application/json").
			contentType(ContentType.JSON).accept(ContentType.JSON).
			body(request.toJSONString()).
		when().
			patch("https://reqres.in/api/users/2").
		then().
			statusCode(200).
			log().all();
	}
	
	@Test
	public void test_4_delete() {
		given().
			delete("https://reqres.in/api/users/2").
		then().
			statusCode(204).
			log().all();
	}

}
