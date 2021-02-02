package client;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.xml.rpc.ServiceException;
import ws.customer.data.xsd.Customer;
import ws.customer.service.CustomerSpringServiceLocator;
import ws.customer.service.CustomerSpringServiceSoap11BindingStub;
public class SOAPClient {
	
	public static void getCustomerById(int id) {
		CustomerSpringServiceLocator locator = new CustomerSpringServiceLocator();
		try {
			CustomerSpringServiceSoap11BindingStub stub = (CustomerSpringServiceSoap11BindingStub) locator.getCustomerSpringServiceHttpSoap11Endpoint();
			Customer customer = stub.getCustomerById(id);
			System.out.println("Customer name: " + customer.getCustomerName());
            System.out.println("Customer ID: " + customer.getCustomerID());
            System.out.println("Priviliged: " + customer.getPrivileged());
            System.out.println("Discount percentage: " + customer.getDiscountPercentage());
            System.out.println("Discount: " + customer.getDiscount());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addCustomer(int customerID, String customerName, float discount, int discountPercentage, boolean privileged, float shoppingAmount) {
		CustomerSpringServiceLocator locator = new CustomerSpringServiceLocator();
		try {
			CustomerSpringServiceSoap11BindingStub stub = (CustomerSpringServiceSoap11BindingStub) locator.getCustomerSpringServiceHttpSoap11Endpoint();
			stub.addCustomer(new Customer(customerID, customerName, discount, discountPercentage, privileged, shoppingAmount, new Date()));
			System.out.println("Add Successful");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getListId() {
		CustomerSpringServiceLocator locator = new CustomerSpringServiceLocator();
		try {
			CustomerSpringServiceSoap11BindingStub stub = (CustomerSpringServiceSoap11BindingStub) locator.getCustomerSpringServiceHttpSoap11Endpoint();
			for (int i : stub.getListOfIds()) {
				System.out.println(i);
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getLargestShoppingAmount() {
		CustomerSpringServiceLocator locator = new CustomerSpringServiceLocator();
		try {
			CustomerSpringServiceSoap11BindingStub stub = (CustomerSpringServiceSoap11BindingStub) locator.getCustomerSpringServiceHttpSoap11Endpoint();
			Customer customer = stub.getLargestShoppingAmount();
			System.out.println("Customer name: " + customer.getCustomerName());
            System.out.println("Customer ID: " + customer.getCustomerID());
            System.out.println("Priviliged: " + customer.getPrivileged());
            System.out.println("Discount percentage: " + customer.getDiscountPercentage());
            System.out.println("Discount: " + customer.getDiscount());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getCustomersByPrivileged(boolean privileged) {
		CustomerSpringServiceLocator locator = new CustomerSpringServiceLocator();
		try {
			CustomerSpringServiceSoap11BindingStub stub = (CustomerSpringServiceSoap11BindingStub) locator.getCustomerSpringServiceHttpSoap11Endpoint();
			if(stub.getCustomersByPrivileged(privileged) != null) {
				for(Customer customer : stub.getCustomersByPrivileged(privileged)) {
					System.out.println("Customer name: " + customer.getCustomerName());
		            System.out.println("Customer ID: " + customer.getCustomerID());
		            System.out.println("Priviliged: " + customer.getPrivileged());
		            System.out.println("Discount percentage: " + customer.getDiscountPercentage());
		            System.out.println("Discount: " + customer.getDiscount());
				}
			} else {
				System.out.println("Not found");
			}
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
    public static void main(String[] args) {
        CustomerSpringServiceLocator locator = new CustomerSpringServiceLocator();
        Scanner scanner = new Scanner(System.in);
		int option = 0;
		do {
			System.out.println("1/ Get customers by Id");
			System.out.println("2/ Add a customer");
			System.out.println("3/ Get list of ids");
			System.out.println("4/ Get largest shopping amount");
			System.out.println("5/ Get customer by privileged");
			System.out.println("Choose your option");
			option = Integer.parseInt(scanner.nextLine());
			if(option == 1) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				getCustomerById(id);
			}
			else if(option == 2) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				System.out.println("Name: ");
				String name = scanner.nextLine();
				System.out.println("Discount: ");
				float discount = Float.parseFloat(scanner.nextLine());
				System.out.println("Discount percentage: ");
				int discountPercentage = Integer.parseInt(scanner.nextLine());
				System.out.println("Privilegede: ");
				boolean priviledge = Boolean.parseBoolean(scanner.nextLine());
				System.out.println("Shopping amount: ");
				float shoppingAmount = Float.parseFloat(scanner.nextLine());
				addCustomer(id, name, discount, discountPercentage, priviledge, shoppingAmount);
			}
			else if(option == 3) {
				getListId();
			}
			else if(option == 4) {
				getLargestShoppingAmount();
			}
			else if(option == 5) {
				System.out.println("Privilegede: ");
				boolean priviledge = Boolean.parseBoolean(scanner.nextLine());
				getCustomersByPrivileged(priviledge);
			}
			else {
				System.out.println("Bye!!");
				break;
			}
		} while (option != 0);
		scanner.close();
    }
}