package data;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	private String customerName;
	private Integer customerID;
	private String image;
	private String job;
	private Date hireDate;
	private Double salary;

	public Customer() {
		customerName = "Customer not known";
		customerID = 0;
		image = "Image not known";
		job = "Job not known";
		hireDate = new Date(0);
		salary = 0.00;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}

	public Integer getCustomerID() {
		return customerID;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
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

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getHireDate() {
		return this.hireDate;
	}

	@Override
	public String toString() {
		return customerID + " " + customerName + " " + image + " " + job + " " + hireDate + " " + salary;
	}

	public Customer(String customerName, int customerID, String image, String job, Date hireDate, double salary) {
		this.customerName = customerName;
		this.customerID = customerID;
		this.image = image;
		this.job = job;
		this.hireDate = hireDate;
		this.salary = salary;
	}

	public String getCustomerInfo(int id) {
		String info = "";
		if (this.customerID == id)
			info = customerName + " " + image + " " + job + " " + hireDate + " " + salary;
		return info;
	}
}