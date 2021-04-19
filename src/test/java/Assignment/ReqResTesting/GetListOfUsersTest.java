package Assignment.ReqResTesting;

import java.io.IOException;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pojo.Data;
import Pojo.ListOfUsersResponseBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.ApiResources;
import resources.TestDataBuild;
import resources.Utils;

public class GetListOfUsersTest extends Utils {

	TestDataBuild td = new TestDataBuild();
	Response response;
	RequestSpecification req;
	ApiResources listofUsersApi = ApiResources.valueOf("GetListOfusers");
	ListOfUsersResponseBody l = new ListOfUsersResponseBody();

	@BeforeMethod
	public void precondition() throws IOException {

		req = RestAssured.given().spec(requestSpecification());

	}

	/* Verification of schema and response body parameters values when passing page number
	 * as query parameter
	 */
	@Test
	public void checkListOfusers() throws IOException {

		response = req.queryParam("page", getGlobalValue("PageNumber")).when().get(listofUsersApi.getResource()).then()
				.header("Server", getGlobalValue("server")).extract().response();
		Assert.assertEquals(200, response.getStatusCode());

		ListOfUsersResponseBody s = req.queryParam("page", getGlobalValue("PageNumber")).when()
				.get(listofUsersApi.getResource()).as(ListOfUsersResponseBody.class);

		Assert.assertEquals(s.getSupport().getText(), "To keep ReqRes free, contributions towards server costs are appreciated!");
		Assert.assertEquals(s.getSupport().getUrl(), "https://reqres.in/#support-heading");
		Assert.assertEquals(s.getPage(), Integer.parseInt(getGlobalValue("PageNumber")));
		Assert.assertEquals(s.getPer_page(), 6);
		Assert.assertEquals(s.getTotal(), 12);
		Assert.assertEquals(s.getTotal_pages(), 2);
		List<Data> l = s.getData();
		for(int i=0;i<l.size();i++) {
			Assert.assertEquals(l.get(i).getId(), i+1+(Integer.parseInt(getGlobalValue("PageNumber"))-1)*6);
			//Note: Similarly we can verify all the remaining keys: email,first_name,last_name,avatar
		}
	}

	/* Verification when query parameter for page is invalid(special character or
	 * invalid page number)
	 */
	@Test
	public void checkListOfuserswithInvalidPage() throws IOException {

		response = req.queryParam("page", getGlobalValue("invalidPageNumber")).when().get(listofUsersApi.getResource())
				.then().log().all().header("Server", getGlobalValue("server")).extract().response();

		Assert.assertEquals(response.getStatusCode(), 400);
	}
	
	/* Verification when query parameter is not passed
	 * Assumption: if query parameter is not passed then all the users should be returned for single request
	 */
	@Test
	public void checkListOfusersWithoutQueryParam() throws IOException {

		response = req.when().get(listofUsersApi.getResource()).then()
				.header("Server", getGlobalValue("server")).extract().response();
		Assert.assertEquals(200, response.getStatusCode());

		ListOfUsersResponseBody s = req.when()
				.get(listofUsersApi.getResource()).as(ListOfUsersResponseBody.class);

		Assert.assertEquals(s.getSupport().getText(), "To keep ReqRes free, contributions towards server costs are appreciated!");
		Assert.assertEquals(s.getSupport().getUrl(), "https://reqres.in/#support-heading");
		Assert.assertEquals(s.getTotal(), 12);
		List<Data> l = s.getData();
		Assert.assertEquals(l.size(), s.getTotal());
		//Note: Size of data array should be equal to the total is's or records
			
	}
}
