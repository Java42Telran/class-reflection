package telran.annotation.validation.example;
import telran.annotation.validation.constraints.*;

public class Company {
	public Company(String name) {
		super();
		this.name = name;
		
	}
	@Patern(message="Wrong company name", value=".{3,}")
String name;
	
	
	@Override
	public String toString() {
		return "Company [name=" + name +  "]";
	}
	
	

}
