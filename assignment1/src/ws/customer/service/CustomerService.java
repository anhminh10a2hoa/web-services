package ws.customer.service;

import ws.customer.data.Customer;
import ws.customer.data.CustomerDB;

public class CustomerService {
	public void setCustomer(String customerName, Integer customerID, float shoppingAmount, boolean privileged,
			int discountPercentage) {
		Customer customer = new Customer(customerName, customerID, shoppingAmount, privileged, discountPercentage);
		CustomerDB.addCustomer(customer);
	}

	public String getCustomer(int id) {
		Customer customer = CustomerDB.getCustomer(id);
		if (customer == null) {
			customer = new Customer();
			customer.setCustomerID(id);
		}
		return customer.toString();
	}
	public String deleteCustomer(int id) {
		Customer customer = CustomerDB.getCustomer(id);
		if (customer == null) {
			return "The customer id does not exist!!!";
		} else {
			CustomerDB.removeCustomer(id);
			return "Successful!!!";
		}
	}
	public String updateCustomer(Customer newCustomer,int id) {
		Customer customer = CustomerDB.getCustomer(id);
		if (customer == null) {
			return "The customer id does not exist!!!";
		}
		CustomerDB.update(newCustomer, id);
		customer = CustomerDB.getCustomer(id);
		return customer.toString();
	}
	public Customer[] getCustomerByShoppingAmount(float lowerBound, float upperBound) {
		return CustomerDB.getCustomerByShoppingAmount(lowerBound, upperBound);
	}
}