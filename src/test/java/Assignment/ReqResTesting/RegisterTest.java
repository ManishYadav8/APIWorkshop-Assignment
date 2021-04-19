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

public class RegisterTest extends Utils {

	RequestSpecification req;
	Response response;
	TestDataBuild tdb = new TestDataBuild();
	ApiResources registrationApi = ApiResources.valueOf("RegisterSuccessfull");
	
	@BeforeMethod
	public void precondition() throws IOException {

		req = RestAssured.given().spec(requestSpecification());

	}
	
	/*Verification of below mentioned scenarios:
	 * 1. When valid email and password are entered.
	 * 2. When email is left blank and password is entered
	 * 3. When email is entered and password is left blank
	 * 4. When both email and password are left blank
	 */
	@Test(dataProviderClass = TestDataBuild.class, dataProvider = "getUserData")
	public void m1(String email, String password) throws IOException {

		response = req.body(tdb.registerPayLoad(email, password)).when().post(registrationApi.getResource()).then().log().all()
				.header("Server", getGlobalValue("server")).extract().response();

		if (email.isEmpty()) {
			Assert.assertEquals(getJsonPath(response, "error"), "Missing email or username");
			Assert.assertEquals(response.getStatusCode(), 400);
		} else if (!(email.isEmpty()) && password.isEmpty()) {
			Assert.assertEquals(getJsonPath(response, "error"), "Missing password");
			Assert.assertEquals(response.getStatusCode(), 400);
		} else {
			Assert.assertEquals(response.getStatusCode(), 200);
			String token = getJsonPath(response, "token");
			Assert.assertEquals(token.isEmpty(), false);
		}

	}
	
	/*Verification of using wrong http method for Register-Successfull API
	 * Assumption: Method Not Allowed should be displayed if wrong http method is used and for coverage only two invalid methods are used
	 */
	@Test
	public void loginCheckWithWrongHttpMethod() throws IOException {
		String httpMethod = getGlobalValue("wrongmethodForRegisterApi");
		if(httpMethod.equalsIgnoreCase("Get")) {
			response = req.body(tdb.loginPayLoad(getGlobalValue("registeredemail"), getGlobalValue("registeredpassword"))).when().get(registrationApi.getResource()).then().
					extract().response();
			
			Assert.assertEquals(response.getStatusCode(), 405);
		} else if (httpMethod.equalsIgnoreCase("Delete")) {
			response = req.body(tdb.loginPayLoad(getGlobalValue("registeredemail"), getGlobalValue("registeredpassword"))).when().get(registrationApi.getResource()).then().
					extract().response();
			
			Assert.assertEquals(response.getStatusCode(), 405);
			
		}
	}
}
