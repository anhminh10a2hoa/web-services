package client;
import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;

import org.apache.axiom.attachments.ByteArrayDataSource;

import data.Customer;
import data.Product;


public class RPCClient {
    public static void main(String[] args) throws Exception {
        RPCServiceClient client = new RPCServiceClient();
        Options options = new Options();
        EndpointReference targetEndPoint = new EndpointReference(
                "https://localhost:8443/axis2/services/assignment_8");
        options.setTo(targetEndPoint);
        options.setProperty(org.apache.axis2.Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
        client.setOptions(options);
        
        String nameSpaceURI="http://service.file.soap";
        
        Scanner scanner = new Scanner(System.in);
		int option = 0;
		do {
			System.out.println("1/ Buy");
			System.out.println("2/ Get all orders");
			System.out.println("3/ Get order buy customer name");
			System.out.println("Choose your option");
			option = Integer.parseInt(scanner.nextLine());
			
			if (option == 1) {
				String optionBuy = "";
				String tempOrder = "";
				double tempTotal = 0;
				int count = 0;
				while(true) {
					getListProduct(client, nameSpaceURI);
					System.out.println("F: finish");
					optionBuy = scanner.nextLine();
					if(!optionBuy.equals("F")) {
						System.out.println("How many?");
						count = Integer.parseInt(scanner.nextLine());
						tempOrder += count + " " + getProductName(client, nameSpaceURI, optionBuy) + ";";
						tempTotal += count * Double.parseDouble(getProductPrice(client, nameSpaceURI, optionBuy));
						count = 0;
					} else {
						System.out.println("Name: ");
						String name = scanner.nextLine();
						System.out.println("Address: ");
						String address = scanner.nextLine();
						System.out.println("Phone number: ");
						String phoneNumber = scanner.nextLine();
						Customer customer = new Customer(name, address, phoneNumber, tempOrder, tempTotal);
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
						// Here we write the customerList ArrayList to the
						// byteArrayOutputStream.
						objectOutputStream.writeObject(customer);
						objectOutputStream.close();
						byteArrayOutputStream.close();
						byte[] objectArray = byteArrayOutputStream.toByteArray();
						// Here we create a data source with the objectArray.
						ByteArrayDataSource dataSource = new ByteArrayDataSource(objectArray);
				        postOrder(client, nameSpaceURI, objectArray);
				        break;
					}
				}
			}
			if (option == 2) {
				getAllOrders(client, nameSpaceURI);
			}
			if (option == 3) {
				System.out.println("Name: ");
				String name = scanner.nextLine();
				getOrderByName(client, nameSpaceURI, name);
			}
		} while (option != 0);
		scanner.close();
    }
    
    public static void postOrder(RPCServiceClient client, String serverNameSpaceURI,
			byte[] objectArray) throws AxisFault {
    	QName uploadByteArrayQName = new QName(serverNameSpaceURI, "postOrder");
		// Here we use POJO to upload the byte array to the Web service.
		Object[] response = client.invokeBlocking(uploadByteArrayQName, new Object[] { objectArray },
				new Class[] { String.class });
		// Here we print the results received from the Web service.
		System.out.println(response[0]);
    }
    
    public static void getListProduct(RPCServiceClient client, String serverNameSpaceURI) throws AxisFault {
    	QName uploadByteArrayQName = new QName(serverNameSpaceURI, "getListProduct");
		// Here we use POJO to upload the byte array to the Web service.
		Object[] response = client.invokeBlocking(uploadByteArrayQName, new Object[] {  },
				new Class[] { String.class });
		// Here we print the results received from the Web service.
		System.out.println(response[0]);
    }
    
    public static String getProductPrice(RPCServiceClient client, String serverNameSpaceURI, String id) throws AxisFault {
    	QName uploadByteArrayQName = new QName(serverNameSpaceURI, "getProductPrice");
		// Here we use POJO to upload the byte array to the Web service.
		Object[] response = client.invokeBlocking(uploadByteArrayQName, new Object[] { id },
				new Class[] { String.class });
		// Here we print the results received from the Web service.
		return (String)response[0];
    }
    
    public static String getProductName(RPCServiceClient client, String serverNameSpaceURI, String id) throws AxisFault {
    	QName uploadByteArrayQName = new QName(serverNameSpaceURI, "getProductName");
		// Here we use POJO to upload the byte array to the Web service.
		Object[] response = client.invokeBlocking(uploadByteArrayQName, new Object[] { id },
				new Class[] { String.class });
		// Here we print the results received from the Web service.
		return (String)response[0];
    }
    
    public static void getAllOrders(RPCServiceClient client, String serverNameSpaceURI) throws AxisFault {
    	QName uploadByteArrayQName = new QName(serverNameSpaceURI, "getAllOrders");
		// Here we use POJO to upload the byte array to the Web service.
		Object[] response = client.invokeBlocking(uploadByteArrayQName, new Object[] { },
				new Class[] { String.class });
		// Here we print the results received from the Web service.
		System.out.println(response[0]);
    }
    
    public static void getOrderByName(RPCServiceClient client, String serverNameSpaceURI, String name) throws AxisFault {
    	QName uploadByteArrayQName = new QName(serverNameSpaceURI, "getOrderByName");
		// Here we use POJO to upload the byte array to the Web service.
		Object[] response = client.invokeBlocking(uploadByteArrayQName, new Object[] { name },
				new Class[] { String.class });
		// Here we print the results received from the Web service.
		System.out.println(response[0]);
    }
}