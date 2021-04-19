package Assignment.ReqResTesting;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.ApiResources;
import resources.TestDataBuild;
import resources.Utils;

public class LoginTest extends Utils{
	
	TestDataBuild td = new TestDataBuild();
	Response response;
	RequestSpecification req;
	ApiResources loginApi = ApiResources.valueOf("LoginSuccessfull");

	@BeforeMethod
	public void precondition() throws IOException {

		req = RestAssured.given().spec(requestSpecification());

	}
	/*Verify below mentioned scenarios:
	 * 1. Login with blank email ID
	 * 2. Login with blank password
	 * 3. Login with Registered email ID and Password
	 */
	@Test(dataProviderClass = TestDataBuild.class, dataProvider = "getUserData")
	public void loginCheckForRegisteredUser(String email, String password) throws IOException {
		

		response = req.body(td.loginPayLoad(email, password)).when().post(loginApi.getResource()).then().log().all().header("Server", getGlobalValue("server"))
				.extract().response();
		
		//As per the API behaviour if email id is left blank and password is entered or not then "Missing email or username" is displayed
		if(email.isEmpty()) {
			Assert.assertEquals(getJsonPath(response, "error"), "Missing email or username");
			Assert.assertEquals(response.getStatusCode(), 400);
		}else if(!(email.isEmpty()) && password.isEmpty()) {
			Assert.assertEquals(getJsonPath(response, "error"), "Missing password");
			Assert.assertEquals(response.getStatusCode(), 400);
		}else {
			//Verification of registered email and password
			Assert.assertEquals(getJsonPath(response, "token"), "QpwL5tke4Pnpja7X4");
			Assert.assertEquals(response.getStatusCode(), 200);
		}
	}

	//Verification when login with unregistered email ID
	@Test
	public void loginCheckForUnRegisteredUser() throws IOException {
		
		response = req.body(td.loginPayLoad(getGlobalValue("unregisteredemail"), getGlobalValue("registeredpassword"))).when().post(loginApi.getResource()).then()
				.extract().response();
		
		Assert.assertEquals(getJsonPath(response, "error"), "user not found");
		Assert.assertEquals(response.getStatusCode(), 400);

	}
	/*Verification when login with registered email ID but with wrong password
	 * Assumption: 401 Unauthorized should be return in case of wrong password
	 */
	@Test
	public void loginCheckWithWrongPassword() throws IOException {
		
		response = req.body(td.loginPayLoad(getGlobalValue("registeredemail"), getGlobalValue("wrongpassword"))).when().post(loginApi.getResource()).then()
				.extract().response();
		
		Assert.assertEquals(response.getStatusCode(), 401);

	}
	/*Verification of using wrong http method for Register-Successfull API
	 * Assumption: Method Not Allowed should be displayed if wrong http method is used and for coverage only two invalid methods are used
	 */
	@Test
	public void loginCheckWithWrongHttpMethod() throws IOException {
		String httpMethod = getGlobalValue("wrongmethodForLoginApi");
		if(httpMethod.equalsIgnoreCase("Get")) {
			response = req.body(td.loginPayLoad(getGlobalValue("registeredemail"), getGlobalValue("registeredpassword"))).when().get(loginApi.getResource()).then().
					extract().response();
			
			Assert.assertEquals(response.getStatusCode(), 405);
		} else if (httpMethod.equalsIgnoreCase("Delete")) {
			response = req.body(td.loginPayLoad(getGlobalValue("registeredemail"), getGlobalValue("registeredpassword"))).when().get(loginApi.getResource()).then().
					extract().response();
			
			Assert.assertEquals(response.getStatusCode(), 405);
			
		}
	}
	

}
