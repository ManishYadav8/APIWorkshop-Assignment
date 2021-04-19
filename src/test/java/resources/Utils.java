package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {

	public static RequestSpecification reqspec;// It is made static so that it will cant change its value to null for
												// every single run.

	ResponseSpecification resspec;
	
   public RequestSpecification requestSpecification() throws IOException {
		
		if(reqspec==null) {
		
		reqspec = new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri(getGlobalValue("baseURL")).
				build();
        return reqspec;	
		}
		return reqspec;
	}

	

	public ResponseSpecification responseSpecification() {

		resspec = new ResponseSpecBuilder().build();
		return resspec;
	}

	public static String getGlobalValue(String key) throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);

	}

	public String getJsonPath(Response response, String key) {
		String res = response.asString();
		JsonPath js = new JsonPath(res);
		return js.get(key).toString();

	}
	
	

}
