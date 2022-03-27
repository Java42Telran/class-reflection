package telran.annotation.validation.example;
import java.time.*;

import telran.annotation.validation.Validator;



public class CnstraintsTestAppl {

	public static void main(String[] args) {
		Company companyInvalid = new Company("");
		Employee emplInvalid = new Employee(0, "kolya", "kuku", 500000, companyInvalid);
		System.out.println("=======================================");
		System.out.println("Printing all wrong fields");
		Validator validator = new Validator();
		validator.validate(emplInvalid).forEach(System.out::println);
		System.out.println("=======================================");
		System.out.println("Printing all correct fields");
		Company company = new Company("Motorola");
		Employee empl = new Employee(123, "Moshe",  "QA", 15000, company);
		System.out.println(validator.validate(empl));
		

	}

}
