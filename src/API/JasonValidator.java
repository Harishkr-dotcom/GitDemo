package API;

import org.testng.Assert;

import API.Payloads;
import io.restassured.path.json.JsonPath;

public class JasonValidator {
	public static void main(String[] args) {
		JsonPath js= new JsonPath(Payloads.CoursePrice());
		//get course count
		System.out.println("Number of courses ="+js.getInt("courses.size()"));
		
		//get purchase ammount
		System.out.println("Purchase amount ="+js.getInt("dashboard.purchaseAmount"));
		
		//get titile of first
		System.out.println("Get first course titile ="+js.get("courses[0].title"));
		
		//get All course titles and their respective Prices
		for(int i=0; i<js.getInt("courses.size()");i++){
			System.out.println("Titles ="+js.get("courses["+i+"].title"));
			System.out.println("Price ="+js.get("courses["+i+"].price"));
		}
		
		//get no of copies sold by RPA Course
		for(int i=0; i<js.getInt("courses.size()");i++){
			if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")){
				System.out.println("Copies of RPA course ="+js.get("courses["+i+"].copies"));
				break;
			}
		}
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		int totalamount = 0;
		for(int i=0; i<js.getInt("courses.size()");i++){
			int price = js.get("courses["+i+"].price");
			int copies = js.get("courses["+i+"].copies");
			int amount = price*copies;
			totalamount = totalamount+amount;
		}
		System.out.println("Purchase expected amount =" +js.getInt("dashboard.purchaseAmount"));
		Assert.assertEquals(totalamount, js.getInt("dashboard.purchaseAmount"));
	}

}
