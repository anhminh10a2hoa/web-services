package client;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import ws.customer.service.DBCustomerServiceLocator;
import ws.customer.service.DBCustomerServiceSoap11BindingStub;
public class Client {
	public static void getCustomerData(String dbUsername, String dbPassword, String db, String table, String condition, DBCustomerServiceSoap11BindingStub stub) {
		try {
			System.out.println("Results of reading data:\n" + stub.getCustomerData(dbUsername, dbPassword, db, table, condition));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void writeCustomerData(String dbUsername, String dbPassword, String db, String table, String values, DBCustomerServiceSoap11BindingStub stub) {
		try {
			System.out.println("The number of added rows: " + stub.writeCustomerData(dbUsername, dbPassword, db, table, values));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void deleteCustomerData(String dbUsername, String dbPassword, String db, String table, int customerId, DBCustomerServiceSoap11BindingStub stub) {
		try {
			System.out.println("Results of deleting data:\n" + stub.deleteCustomerData(dbUsername, dbPassword, db, table, customerId));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void updateCustomerData(String dbUsername, String dbPassword, String db, String table, int customerId, String values, DBCustomerServiceSoap11BindingStub stub) {
		try {
			System.out.println("Results of updating data:\n" + stub.updateCustomerData(dbUsername, dbPassword, db, table, customerId, values));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void searchNameData(String dbUsername, String dbPassword, String db, String table, String customerName, DBCustomerServiceSoap11BindingStub stub) {
		try {
			System.out.println("Results of updating data:\n" + stub.searchNameData(dbUsername, dbPassword, db, table, customerName));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void searchPriceData(String dbUsername, String dbPassword, String db, String table, double lowerPrice, double higherPrice, DBCustomerServiceSoap11BindingStub stub) {
		try {
			System.out.println("Results of updating data:\n" + stub.searchPriceData(dbUsername, dbPassword, db, table, lowerPrice, higherPrice));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void main(String[] args) {
       
        try {
            DBCustomerServiceLocator locator = new DBCustomerServiceLocator();
            DBCustomerServiceSoap11BindingStub stub = (DBCustomerServiceSoap11BindingStub) locator.getDBCustomerServiceHttpSoap11Endpoint();
    
            String dbUsername="e1800956";
            String dbPassword="xnmzsakPTzEr";
            String db="e1800956_assignment2_web_services";
            String table="assignment2";
            String condition="";
            
            Scanner scanner = new Scanner(System.in);
    		int option = 0;
    		do {
    			System.out.println("1/ Get all data");
    			System.out.println("2/ Add user");
    			System.out.println("3/ Delete user");
    			System.out.println("4/ Update user");
    			System.out.println("5/ Get list of users by name");
    			System.out.println("6/ Get list of users by shopping amount");
    			System.out.println("Choose your option");
    			option = Integer.parseInt(scanner.nextLine());
    			if(option == 1) {
    				getCustomerData(dbUsername, dbPassword, db, table, condition, stub);
    			}
    			else if(option == 2) {
    				System.out.println("Id: ");
    				int id = Integer.parseInt(scanner.nextLine());
    				System.out.println("Name: ");
    				String name = scanner.nextLine();
    				System.out.println("Shopping amount: ");
    				double shoppingAmount = Double.parseDouble(scanner.nextLine());
    				System.out.println("Privilegede: ");
    				boolean priviledge = Boolean.parseBoolean(scanner.nextLine());
    				System.out.println("Discount: ");
    				int discount = Integer.parseInt(scanner.nextLine());
    				String value = id + "," + name + "," + shoppingAmount + "," + priviledge + "," + discount;
    				writeCustomerData(dbUsername, dbPassword, db, table, value, stub);
    			}
    			else if(option == 3) {
    				System.out.println("Id: ");
    				int id = Integer.parseInt(scanner.nextLine());
    				deleteCustomerData(dbUsername, dbPassword, db, table, id, stub);
    			}
    			else if(option == 4) {
    				System.out.println("Id: ");
    				int id = Integer.parseInt(scanner.nextLine());
    				System.out.println("Name: ");
    				String name = scanner.nextLine();
    				System.out.println("Shopping amount: ");
    				double shoppingAmount = Double.parseDouble(scanner.nextLine());
    				System.out.println("Privilegede: ");
    				boolean priviledge = Boolean.parseBoolean(scanner.nextLine());
    				System.out.println("Discount: ");
    				int discount = Integer.parseInt(scanner.nextLine());
    				String value = id + "," + name + "," + shoppingAmount + "," + priviledge + "," + discount;
					updateCustomerData(dbUsername, dbPassword, db, table, id, value, stub);
    			}
    			else if(option == 5) {
    				System.out.println("Name: ");
    				String name = scanner.nextLine();
    				searchNameData(dbUsername, dbPassword, db, table, name, stub);
    			}
    			else if(option == 6) {
    				System.out.println("Lower price: ");
    				double lower = Double.parseDouble(scanner.nextLine());
    				System.out.println("Higher price: ");
    				double higher = Double.parseDouble(scanner.nextLine());
    				searchPriceData(dbUsername, dbPassword, db, table, lower, higher, stub);
    			}
    			else {
    				System.out.println("Bye!!");
    				break;
    			}
    		} while (option != 0);
    		scanner.close();
        } catch (ServiceException e) {
            e.printStackTrace();
        }    
        
        
    }
}