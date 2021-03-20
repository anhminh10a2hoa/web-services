package data;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	private String customerName;
	private String address;
	private String phoneNumber;
	private String order;
	private double total;

	public Customer() {
		
	}
	
	public Customer(String customerName, String address, String phoneNumber, String order, double total) {
		this.customerName = customerName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.order = order;
		this.total = total;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerName() {
		return customerName;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrder() {
		return order;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}

	public double getTotal() {
		return total;
	}
	
	@Override
	public String toString() {
		return customerName + " " + address + " " + phoneNumber + " " + order + " " + total;
	}
}