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

public class DeleteUserTest extends Utils{

	TestDataBuild td = new TestDataBuild();
	Response response;
	RequestSpecification req;
	ApiResources DeleteUserApi = ApiResources.valueOf("DeleteUser");

	@BeforeMethod
	public void precondition() throws IOException {

		req = RestAssured.given().spec(requestSpecification());

	}
	
	//Verify that when correct user id is passed for Deletion, user is deleted successfully
	@Test
	public void deleteUser() throws IOException {
		response = req.when().delete(DeleteUserApi.getResource()+getGlobalValue("idForUserDeletion")).then().log().all().header("Server", getGlobalValue("server"))
				.extract().response();
		
		Assert.assertEquals(response.getStatusCode(), 204);
		Assert.assertEquals(response.asString().isEmpty(), true);
		
	/*Note:
	 * The complete verification should include the steps of hitting the Get List of Users API and then verify that user is
	 * deleted successfully. However that step is not included as part of verification as Get list of users API is returning static values only
	 */
	}
	
	//Verification when invalid user id is passed(special character)
	@Test
	public void deleteUserWithWrongId() throws IOException {
		response = req.when().delete(DeleteUserApi.getResource()+getGlobalValue("idForUserDeletionwithSpecialCharacter")).then().header("Server", getGlobalValue("server"))
				.extract().response();
		
		Assert.assertEquals(response.getStatusCode(), 400);
		
	}

}
