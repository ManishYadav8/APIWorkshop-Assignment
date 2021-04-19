package resources;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class TestDataBuild extends Utils{
	
	public String registerPayLoad(String email, String password) {
		return "{\r\n" + 
				"    \"email\": \""+email+"\",\r\n" + 
				"    \"password\": \""+password+"\"\r\n" + 
				"}";
	}
	
	public String loginPayLoad(String email, String password) {
		return "{\r\n" + 
				"    \"email\": \""+email+"\",\r\n" + 
				"    \"password\": \""+password+"\"\r\n" + 
				"}";
	}
	
	public String createUserPayLoad(String name, String job) {
		return "{\r\n" + 
				"    \"name\": \""+name+"\",\r\n" + 
				"    \"job\": \""+job+"\"\r\n" + 
				"}";
	}

	@DataProvider
	public static Object[][] getUserData() throws IOException{
		
		Object[][] data = new Object[4][2];
		data[0][0] = "";
		data[0][1] = "";
		data[1][0] = "";
		data[1][1] = getGlobalValue("registeredpassword");
		data[2][0] = getGlobalValue("registeredemail");
		data[2][1] = "";
		data[3][0] = getGlobalValue("registeredemail");
		data[3][1] = getGlobalValue("registeredpassword");

		return data;
	}
	
	@DataProvider
	public static Object[][] getUserCreationData() throws IOException{
		
		Object[][] data = new Object[4][2];
		data[0][0] = "";
		data[0][1] = "";
		data[1][0] = "";
		data[1][1] = getGlobalValue("user_job");
		data[2][0] = getGlobalValue("user_name");
		data[2][1] = "";
		data[3][0] = getGlobalValue("user_name");
		data[3][1] = getGlobalValue("user_job");

		return data;
	}
	
	
	
}
