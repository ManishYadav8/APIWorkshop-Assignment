package resources;

public enum ApiResources {
	
	RegisterSuccessfull("/api/register"),
	LoginSuccessfull("/api/login"),
	CreateUser("/api/users"),
	GetListOfusers("/api/users?page="),
	DeleteUser("/api/users/");
	
	private String resource;
	
	//Use of Constructor
	ApiResources(String resource){
		this.resource=resource;
	}
	
	public String getResource() {
		return resource;
	}

}
