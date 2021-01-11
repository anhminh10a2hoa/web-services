package ws.customer.data;

public class Customer {
	private String customerName;
	private Integer customerID;
	private float shoppingAmount;
	private boolean privileged;
	private int discountPercentage;

	public Customer() {
		customerName = "not knowm";
		customerID = 0;
		shoppingAmount = 0.0f;
		privileged = false;
		discountPercentage = 0;
	}

	public Customer(String customerName, Integer customerID, float shoppingAmount, boolean privileged,
			int discountPercentage) {
		this.customerName = customerName;
		this.customerID = customerID;
		this.shoppingAmount = shoppingAmount;
		this.privileged = privileged;
		this.discountPercentage = discountPercentage;
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

	public void setPrivileged(boolean privileged) {
		this.privileged = privileged;
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

	public float getDiscount() {
		return shoppingAmount * discountPercentage / 100.0f;
	}

	@Override
	public String toString() {
		return "Name: " + this.customerName + " " + System.lineSeparator() + "ID: " + this.customerID + " "
				+ System.lineSeparator() + "Shopping amount: " + this.shoppingAmount + " " + System.lineSeparator()
				+ "Priviliged: " + this.privileged + " " + System.lineSeparator() + "Discount: " + this.getDiscount();
	}
}