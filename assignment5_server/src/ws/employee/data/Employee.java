package ws.employee.data;

import java.util.Date;

public class Employee {

	private String employeeName;
	private Integer employeeId;
	private String job;
	private double salary;
	private String hireDate;
	
	public Employee() {
		employeeName = "Employee not knowm";
		employeeId = 0;
		job = "Job not known";
		salary = 0.00;
		hireDate = "Date not know";
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getJob() {
		return job;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getSalary() {
		return salary;
	}
	
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getHireDate() {
		return this.hireDate;
	}

	@Override
	public String toString() {
		return "\nEmployee name=" + employeeName + "\n" 
				+ "Employee id=" + employeeId + "\n" 
				+ "Job=" + job + "\n" 
				+ "Salary=" + salary + "\n" 
				+ "Hire Date=" + hireDate + "\n";
	}

}