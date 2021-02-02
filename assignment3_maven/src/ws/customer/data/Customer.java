package ws.customer.data;

import java.util.Date;

public class Customer {

	private String customerName;
	private Integer customerID;
	private float shoppingAmount;
	private boolean privileged;
	private int discountPercentage;
	private Date shoppingDate;

	public Customer() {
		customerName = "Customer not knowm";
		customerID = 0;
		shoppingAmount = 0.0f;
		privileged = false;
		discountPercentage = 0;
		shoppingDate = new Date(0);
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

	public void setShoppingAmount(float shoppingAmount) {
		this.shoppingAmount = shoppingAmount;
	}

	public float getShoppingAmount() {
		return shoppingAmount;
	}

	public void setPrivileged(boolean privilleged) {
		this.privileged = privilleged;
	}

	public boolean getPrivileged() {
		return privileged;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setShoppingDate(Date shoppingDate) {
		this.shoppingDate = shoppingDate;
	}

	public Date getShoppingDate() {
		return this.shoppingDate;
	}

	public float getDiscount() {
		return shoppingAmount * discountPercentage / 100.0f;
	}

	@Override
	public String toString() {
		return customerID + " " + customerName + " " + shoppingAmount + " " + shoppingDate;
	}

}