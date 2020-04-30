package API;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import API.Payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class CRUD {
	public static void main(String[] args) {
		//add place
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String addresponse = given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payloads.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP")).body("status", equalTo("OK"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		System.out.println("AddResponse ="+addresponse);
		JsonPath js=new JsonPath(addresponse);
		String placeid = js.getString("place_id");
		System.out.println("PlaceId ="+placeid);
		
		//update place
		String newAddress = "Kamakshipalya123";
		String putresponse = given().log().all().queryParam("key", "qaclick123").header("Content", "application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeid+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"))
		.extract().response().toString();
		System.out.println(putresponse);
		
		//get place
		String getresponse = given().queryParam("place_id", placeid).queryParam("key", "qaclick123").header("Content", "application/json")
		.when().get("/maps/api/place/get/json").then().assertThat().statusCode(200).extract().response().toString();
		JsonPath js1 = new JsonPath(getresponse);
		String address = js1.getString("address");
		System.out.println("Updated Adress= "+address);
		
		
		
	}

}
