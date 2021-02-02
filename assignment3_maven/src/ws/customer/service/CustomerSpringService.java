package ws.customer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ws.customer.data.Customer;

public class CustomerSpringService {

	@Autowired
	private List<Customer> customer = new ArrayList<Customer>();
	
	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}
	
	public void addCustomer(Customer c){
		customer.add(c);
	}

	public Customer getCustomerById(int id) {
		for(Customer c : customer) {
			if(c.getCustomerID().equals(id)) {
				return c;
			}
		}
		return new Customer();
	}
	
	public List<Integer> getListOfIds(){
		List<Integer> result = new ArrayList<Integer>();
		
		for (Customer c : customer) {
			result.add(c.getCustomerID());
		}
		
		return result;
	}
	
	public Customer getLargestShoppingAmount() {
		Customer newCustomer = new Customer();
		
		float max_amount = 0;
		for (Customer c : customer) {
			if (c.getShoppingAmount() > max_amount) {
				max_amount = c.getShoppingAmount();
				newCustomer = c;
			}
		}
		
		return newCustomer;
	}
	
	public List<Customer> getCustomersByPrivileged(boolean privileged){
		List<Customer> result = new ArrayList<Customer>();
		
		for (Customer c : customer) {
			if (c.getPrivileged() == privileged) {
				result.add(c);
			}
		}
		
		return result;
	}
}