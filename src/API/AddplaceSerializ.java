package API;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.Latitude;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class AddplaceSerializ {
	public static void main(String[] args) {
	AddPlace ad = new AddPlace();
	ad.setAccuracy(56);
	ad.setName("Harish");
	ad.setPhone_number("(+91) 983 893 3937");
	ad.setAddress("29, side layout, cohen 09");
	ad.setWebsite("http://google.com");
	ad.setLanguage("French-IN");
	List<String> tpess = new ArrayList<String>();
	tpess.add("shoe park");
	tpess.add("shop");
	ad.setTypes(tpess);
	Latitude lt= new Latitude();
	lt.setLat(-38.383494);
	lt.setLng(33.427362);
	ad.setLocation(lt);
	
	RestAssured.baseURI= "https://rahulshettyacademy.com";
	String addresponse = given().queryParam("key", "qaclick123").log().all().header("Content-Type","application/json")
	.body(ad).when().post("maps/api/place/add/json")
	.then().log().all().assertThat().statusCode(200).extract().response().asString();
	System.out.println("AddResponse ="+addresponse);
	JsonPath js=new JsonPath(addresponse);
	String placeid = js.getString("place_id");
	System.out.println("PlaceId ="+placeid);
	}

}
