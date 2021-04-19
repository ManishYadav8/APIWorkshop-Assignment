package Assignment.ReqResTesting;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.ApiResources;
import resources.GetCurrentDate;
import resources.TestDataBuild;
import resources.Utils;

public class CreateUserTest extends Utils {

	TestDataBuild td = new TestDataBuild();
	Response response;
	RequestSpecification req;
	ApiResources createUserApi = ApiResources.valueOf("CreateUser");

	@BeforeMethod
	public void precondition() throws IOException {

		req = RestAssured.given().spec(requestSpecification());

	}
	
	/*Verification of Response Body and Status code in case of below mentioned scenarios:
	 * 1. Valid name and job
	 * 2. Blank name and blank job
	 * 3. valid name but blank job
	 * 4. Blank Name but valid job
	 * Assumption: In case of blank name and blank job 400 Bad Request is displayed and for rest of the cases 201 created is displayed.
	 */
	@Test(dataProviderClass = TestDataBuild.class, dataProvider = "getUserCreationData")
	public void checkUserCreation(String name, String job) throws IOException {
		
		
		response = req.body(td.createUserPayLoad(name, job)).when().post(createUserApi.getResource()).then()
				.log().all().header("Server", getGlobalValue("server")).extract().response();
		
		if(name.isEmpty() && job.isEmpty()) {
			Assert.assertEquals(response.getStatusCode(), 400);
		}else {
			
			Assert.assertEquals(getJsonPath(response, "name"), name);
			Assert.assertEquals(getJsonPath(response, "job"), job);
			String[] splitparts = getJsonPath(response, "createdAt").split("T");
			Assert.assertEquals(splitparts[0], GetCurrentDate.getDate());
			Assert.assertEquals(response.getStatusCode(), 201);
			String id = getJsonPath(response, "id");
			Assert.assertEquals(id.isEmpty(), false);
			/* Note: After creation of the user, Get List of Users Api should be hit and then
			 * check for the created user in the list. But Get List of Users Api is returning
			 * static data, so not checking this scenario here.
			 */
		}
	}		
	
	/*Verification when passing invalid attributes(at place of name and job keys, email and random key is passed in RequestBody
	 * Assumption: 400 Bad Request should be displayed in case of invalid Request Body 
	 */
		@Test(dataProviderClass = TestDataBuild.class, dataProvider = "getUserCreationData")
		public void checkUserCreationWithWrongBody(String email, String randomkey) throws IOException {
			
			response = req.body(td.createUserPayLoad(email, randomkey)).when().post(createUserApi.getResource()).then().header("Server", getGlobalValue("server"))
					.extract().response();
		
				Assert.assertEquals(response.getStatusCode(), 400);
				
			}


	
}
