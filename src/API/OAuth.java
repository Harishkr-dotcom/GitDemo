package API;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import org.testng.Assert;

import POJO.GetCourses;

public class OAuth {
	
	public static void main(String[] args) throws InterruptedException {

		// TODO Auto-generated method stub

		String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2FzAFfk-wB-7qnK6j6bvvw6pvewV058yDaAbjgaXno7YHF5SWxnDlh9oqxzFiTz0vu_IQoZcAjFVW7oomoUrBsS6Y&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=2&prompt=none#";
		
		String partialcode=url.split("code=")[1];

		String code=partialcode.split("&scope")[0];

		System.out.println(code);

		String response =given() .urlEncodingEnabled(false).queryParams("code",code)

		               

		                   .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

		                        .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")

		                        .queryParams("grant_type", "authorization_code")

		                        .queryParams("state", "verifyfjdss")

		                        .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")

		                     // .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")

		                       

		                        .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")

		                        .when().log().all()

		                        .post("https://www.googleapis.com/oauth2/v4/token").asString();

		 System.out.println(response);

		JsonPath jsonPath = new JsonPath(response);

		    String accessToken = jsonPath.getString("access_token");

		    System.out.println("AccessToken"+accessToken);

		GetCourses gc = given().contentType("application/json").

		queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)

		.when().get("https://rahulshettyacademy.com/getCourse.php")

		.as(GetCourses.class);
		System.out.println("LinkedIn url: "+gc.getLinkedIn());
		System.out.println("Instructor: "+gc.getInstructor());
		
		//get the second course title
		System.out.println("sopup ui price: "+gc.getCourses().getApi().get(1).getCourseTitle());
		
		//get the second course title dynamically
		for(int i=0; i<gc.getCourses().getApi().size(); i++){
			if(gc.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
				System.out.println("Soup ui price: "+gc.getCourses().getApi().get(i).getPrice());
			}
			break;
		}
		
		//for the printing of web automation all course tites
		ArrayList<String> expected =new ArrayList<>();
		expected.add("Selenium Webdriver Java");
		expected.add("Cypress");
		expected.add("Protractor");
		ArrayList<String> arr= new ArrayList<>();
		for(int i=0 ;i<gc.getCourses().getWebAutomation().size(); i++){
			arr.add(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
		}
		Assert.assertTrue(arr.equals(expected));
		
	

		
		
		
	

	}
}
