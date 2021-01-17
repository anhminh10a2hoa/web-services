package client;

import java.util.Scanner;

import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import client.Customer;

public class CustomerRPCClient {
	
	public static void addAllData(RPCServiceClient serviceClient) throws AxisFault {
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
			serviceClient.invokeRobust(opSetCustomer, opSetCustomerArgs);
		}
	}
	
	public static void getUserById(int id, RPCServiceClient serviceClient) throws AxisFault {
		QName opGetCustomer = new QName("http://service.customer.ws", "getCustomer");
		Object[] opGetCustomerArgs = new Object[] { id };

		@SuppressWarnings("unchecked")
		Class<String>[] returnTypes = new Class[] { String.class };
		Object[] response = serviceClient.invokeBlocking(opGetCustomer, opGetCustomerArgs, returnTypes);
		String result = response[0].toString();
		System.out.println(result);
	}
	
	public static void removeUserById(int id, RPCServiceClient serviceClient) throws AxisFault {
		QName opRemoveCustomer = new QName("http://service.customer.ws", "deleteCustomer");
		Object[] opRemoveCustomerArgs = new Object[] { id };
		@SuppressWarnings("unchecked")
		Class<String>[] returnTypes = new Class[] { String.class };
		Object[] response = serviceClient.invokeBlocking(opRemoveCustomer, opRemoveCustomerArgs, returnTypes);
		String result = response[0].toString();
		System.out.println(result);
	}
	
	public static void updateUserById(int id, String name, RPCServiceClient serviceClient) throws AxisFault {
		Customer customer = new Customer(name, id, 100.00f, true, 10);
		QName opUpdateCustomer = new QName("http://service.customer.ws", "updateCustomer");
		Object[] opUpdateCustomerArgs = new Object[] { customer, id };
		@SuppressWarnings("unchecked")
		Class<String>[] returnTypes = new Class[] { String.class };
		Object[] response = serviceClient.invokeBlocking(opUpdateCustomer, opUpdateCustomerArgs, returnTypes);
		String result = response[0].toString();
		System.out.println(result);
	}
	
	public static void getUserByShoppingAmountCustomer(float lowerBoud, float upperBoud, RPCServiceClient serviceClient) throws AxisFault {
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

	public static void main(String[] args1) throws AxisFault {
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		 EndpointReference targetEPR = new
		 EndpointReference("http://localhost:8080/axis2/services/mg_CustomerService");
		options.setTo(targetEPR);
		
		
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		do {
			System.out.println("1/ Add all data");
			System.out.println("2/ Get user");
			System.out.println("3/ Update user");
			System.out.println("4/ Remove user");
			System.out.println("5/ Get list of users by shopping amount");
			System.out.println("Choose your option");
			option = Integer.parseInt(scanner.nextLine());
			if(option == 1) {
				addAllData(serviceClient);
			}
			else if(option == 2) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				getUserById(id, serviceClient);
			}
			else if(option == 3) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				System.out.println("Name: ");
				String name = scanner.nextLine();
				updateUserById(id, name, serviceClient);
			}
			else if(option == 4) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				removeUserById(id, serviceClient);
			}
			else if(option == 5) {
				System.out.println("Lower bound: ");
				Float lp = Float.parseFloat(scanner.nextLine());
				System.out.println("Upper bound: ");
				Float hp = Float.parseFloat(scanner.nextLine());
				getUserByShoppingAmountCustomer(lp, hp, serviceClient);
			}
			else {
				System.out.println("Bye!!");
				break;
			}
		} while (option != 0);
		scanner.close();
	}
}