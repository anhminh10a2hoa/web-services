package ws.customer.data;

import java.util.Hashtable;
import java.util.Iterator;

public class CustomerDB {

	// Here we define a collection for customers.
	private static Hashtable<Integer, Customer> customers = new Hashtable<Integer, Customer>();

	public static String addCustomer(Customer customer) {

		String result = "Current number of entries: ";
		if (customer != null)
			customers.put(customer.getCustomerID(), customer);

		return result + customers.size();

	}
	
	public static void removeCustomer(int id) {
		customers.remove(id);
	}
	
	public static Customer update(Customer customer, int id) {
		customers.put(id, customer);
		return customers.get(id);
	}

	public static Customer getCustomer(int id) {
		return customers.get(id);
	}
	
	public static Customer[] getCustomerByShoppingAmount(float lowerBound, float upperBound) {
		Customer[] customersArray = new Customer[customers.size()];
		int n = 0;
		for (int key : customers.keySet()) {
		    if(customers.get(key).getShoppingAmount() >= lowerBound && customers.get(key).getShoppingAmount() <= upperBound) {
				customersArray[n] = customers.get(key);
			}
			n++;
		}
		return customersArray;
	}
}

//jar -cvf ex31_pojo_service.aar META-INF ws