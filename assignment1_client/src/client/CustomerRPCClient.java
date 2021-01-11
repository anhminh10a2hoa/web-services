package client;

import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import client.Customer;

public class CustomerRPCClient {

	public static void main(String[] args1) throws AxisFault {
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		 EndpointReference targetEPR = new
		 EndpointReference("http://localhost:8080/axis2/services/mg_CustomerService");
//		EndpointReference targetEPR = new EndpointReference(
//				"http://app3.cc.puv.fi/axis2_179/services/mg_CustomerService");
		options.setTo(targetEPR);
		int[] ids = { 34000, 35000, 36000 };
		String[] names = { "Siirus", "Sasha", "Lizzie" };
		float[] shoppinGAmounts = { 123.54f, 320.40f, 58.89f };
		boolean[] priviliged = { true, true, false };
		int[] discountPercentages = { 3, 10, 2 };
		// Setting the customer information
		QName opSetCustomer = new QName("http://service.customer.ws", "setCustomer");

		Object[] opSetCustomerArgs = null;

		for (int i = 0; i < ids.length; i++) {
			opSetCustomerArgs = new Object[] { names[i], ids[i], shoppinGAmounts[i], priviliged[i],
					discountPercentages[i] };
			// We call invokeRobust() method if we wouldn't expect any response from
			// the server
			serviceClient.invokeRobust(opSetCustomer, opSetCustomerArgs);
			// We call invokeBlocking() method if the we expect a value from the web service
			/*
			 * Class[] setCustomerReturnTypes = new Class[] { Integer.class }; Object[]
			 * setCustomerResponse = serviceClient.invokeBlocking(opSetCustomer,
			 * opSetCustomerArgs, setCustomerReturnTypes);
			 *
			 * System.out.println("Number of customers on the server: " + (Integer)
			 * setCustomerResponse[0]);
			 */
		}
		// Here we get the customer information
//		int id = 35000;
//		QName opGetCustomer = new QName("http://service.customer.ws", "getCustomer");
//		Object[] opGetCustomerArgs = new Object[] { id };
//
//		@SuppressWarnings("unchecked")
//		Class<String>[] returnTypes = new Class[] { String.class };
//		Object[] response = serviceClient.invokeBlocking(opGetCustomer, opGetCustomerArgs, returnTypes);
		
		// Here we remove the customer 
//		int id = 34000;
//		QName opRemoveCustomer = new QName("http://service.customer.ws", "deleteCustomer");
//		Object[] opRemoveCustomerArgs = new Object[] { id };
//		@SuppressWarnings("unchecked")
//		Class<String>[] returnTypes = new Class[] { String.class };
//		Object[] response = serviceClient.invokeBlocking(opRemoveCustomer, opRemoveCustomerArgs, returnTypes);
		
		// Here we update the customer
//		int id = 35000;
//		Customer customer = new Customer("Sasha", id, 320.40f, true, 10);
//		QName opUpdateCustomer = new QName("http://service.customer.ws", "updateCustomer");
//		Object[] opUpdateCustomerArgs = new Object[] { customer, id };
//		@SuppressWarnings("unchecked")
//		Class<String>[] returnTypes = new Class[] { String.class };
//		Object[] response = serviceClient.invokeBlocking(opUpdateCustomer, opUpdateCustomerArgs, returnTypes);
//
//		String result = response[0].toString();
//		System.out.println(result);
		
		// Here we get the customer by his/her shopping amount
		float lowerBoud = 10.00f;
		float upperBoud = 300.00f;
		QName opByShoppingAmountCustomer = new QName("http://service.customer.ws", "getCustomerByShoppingAmount");
		Object[] opByShoppingAmountCustomerArgs = new Object[] { lowerBoud, upperBoud };
		@SuppressWarnings("unchecked")
		Class<Customer>[] returnTypes = new Class[] { Customer[].class };
		
		Object[] response = serviceClient.invokeBlocking(opByShoppingAmountCustomer, opByShoppingAmountCustomerArgs, returnTypes);
		Customer[] result = (Customer[]) response[0];
		for (int i = 0; i < result.length; i++) {
			if(result[i] != null) {
				System.out.println(result[i].getCustomerName() + " " + result[i].getShoppingAmount());
			}
	    }

	}
}