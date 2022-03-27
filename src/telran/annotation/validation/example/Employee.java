package telran.annotation.validation.example;


import java.io.Serializable;
import java.time.LocalDate;

import telran.annotation.validation.constraints.*;

public class Employee implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private static final int MIN_SALARY = 5000;
private static final int MAX_SALARY = 30000;
private long id;
@Patern(value="[A-Z][a-z]+", message="wrong employee name")
private String name;

@Patern(message="wrong department", value="Development|QA|HR")
private String department;
@Min(value=MIN_SALARY, message="salary can't be less than "+MIN_SALARY)
@Max(value=MAX_SALARY, message="salary can't be greater than "+MAX_SALARY)
private int salary;
@Valid
private Company company;

public Employee(long id, String name,  String department, int salary, Company company) {
	this.id = id;
	this.name = name;
	this.department = department;
	this.salary = salary;
	this.company = company;
}
public Employee() {
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Employee other = (Employee) obj;
	if (id != other.id)
		return false;
	return true;
}

public long getId() {
	return id;
}
public String getName() {
	return name;
}

public String getDepartment() {
	return department;
}
public int getSalary() {
	return salary;
}
@Override
public String toString() {
	return "Employee [id=" + id + ", name=" + name +  ", department=" + department
			+ ", salary=" + salary + ", company=" + company +  "]";
}
}
