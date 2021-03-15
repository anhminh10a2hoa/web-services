package client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import org.apache.axiom.attachments.ByteArrayDataSource;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMText;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import data.Customer;

public class RPCClient {
	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(System.in);
		int option = 0;

		RPCServiceClient client = new RPCServiceClient();
		Options options = new Options();
		// EndpointReference targetEndPoint = new
		// EndpointReference("http://app2.cc.puv.fi/axis2/services/mg_mixed_file_object_upload_2");
		EndpointReference targetEndPoint = new EndpointReference(
				"http://localhost:8080/axis2/services/soap_object_exchange");

		options.setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
		options.setTo(targetEndPoint);
		client.setOptions(options);

		String fileName = "customers.dat";
		String serverNameSpaceURI = "http://service.soap";
		String searchFileName = "ba_" + fileName;
		String downloadFilePath = "downloads" + File.separator;

		do {
			System.out.println("6/ Download file data");
			System.out.println("1/ Get all data");
			System.out.println("2/ Add data");
			System.out.println("3/ Search data");
			System.out.println("4/ Update data");
			System.out.println("5/ Delete data");
			System.out.println("Choose your option");
			option = Integer.parseInt(scanner.nextLine());
			if (option == 6) {
				downloadFileOM(client, serverNameSpaceURI, downloadFilePath, searchFileName);
			}
			if (option == 1) {
				ArrayList<Customer> customerList = readDataFromFiles(downloadFilePath, searchFileName);
				for (Customer c : customerList) {
					System.out.println(c.toString());
				}
			}
			if (option == 2) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				System.out.println("Name: ");
				String name = scanner.nextLine();
				System.out.println("Image: ");
				String image = scanner.nextLine();
				System.out.println("Job: ");
				String job = scanner.nextLine();
				System.out.println("Salary: ");
				double salary = Double.parseDouble(scanner.nextLine());
				System.out.println("Hire data: ");
				String date = scanner.nextLine();

				Customer customer = new Customer(name, id, image, job, date, salary);
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
				uploadByteArray(client, serverNameSpaceURI, fileName, objectArray, customer);
			}
			if (option == 3) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				String imageName = getImageName(client, serverNameSpaceURI, id, searchFileName);
				searchData(client, serverNameSpaceURI, id, searchFileName);
				downloadFileOM(client, serverNameSpaceURI, downloadFilePath, imageName);
			}
			if (option == 4) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				System.out.println("Name: ");
				String name = scanner.nextLine();
				System.out.println("Image: ");
				String image = scanner.nextLine();
				System.out.println("Job: ");
				String job = scanner.nextLine();
				System.out.println("Salary: ");
				double salary = Double.parseDouble(scanner.nextLine());
				System.out.println("Hire data: ");
				String date = scanner.nextLine();

				Customer customer = new Customer(name, id, image, job, date, salary);
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
				updateData(client, serverNameSpaceURI, fileName, objectArray, customer);
			}
			if (option == 5) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				
				deleteData(client, serverNameSpaceURI, fileName, id);
			}
		} while (option != 0);
		scanner.close();
	}

	private static void searchData(RPCServiceClient client, String serverNameSpaceURI, int searchCustomerId,
			String searchFileName) throws AxisFault {
		Object[] response;
		QName searchDataQName = new QName(serverNameSpaceURI, "searchData");
		// Here we use POJO to upload the byte array to the Web service.
		response = client.invokeBlocking(searchDataQName, new Object[] { searchFileName, searchCustomerId },
				new Class[] { String.class });
		System.out.println(response[0]);
	}
	
	private static String getImageName(RPCServiceClient client, String serverNameSpaceURI, int searchCustomerId,
			String searchFileName) throws AxisFault {
		Object[] response;
		QName searchDataQName = new QName(serverNameSpaceURI, "returnImage");
		// Here we use POJO to upload the byte array to the Web service.
		response = client.invokeBlocking(searchDataQName, new Object[] { searchFileName, searchCustomerId },
				new Class[] { String.class });
		return (String) response[0];
	}
	
	private static void deleteData(RPCServiceClient client, String serverNameSpaceURI, String fileName,
			Integer id) throws AxisFault {
		QName deleteCustomerQName = new QName(serverNameSpaceURI, "deleteCustomer");
		// Here we use POJO to upload the byte array to the Web service.
		Object[] response = client.invokeBlocking(deleteCustomerQName, new Object[] { id, fileName },
				new Class[] { String.class });
		// Here we print the results received from the Web service.
		System.out.println(response[0]);
	}

	private static void uploadOM(RPCServiceClient client, String serverNameSpaceURI, String fileName,
			DataHandler dataHandler) throws AxisFault {
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace(serverNameSpaceURI, "uom");
		OMElement methodElement = omFactory.createOMElement("uploadOMElement", omNameSpace);
		OMElement argumentElement = omFactory.createOMElement("argument", omNameSpace);

		argumentElement.addAttribute("file_name", fileName, omNameSpace);

		// In the following we add the DataHandler object to the OMElement,
		// send it to the Web service and print the results received from the
		// server.
		OMText objectTextData = omFactory.createOMText(dataHandler, true);
		argumentElement.addChild(objectTextData);
		methodElement.addChild(argumentElement);
		OMElement responseElement = client.sendReceive(methodElement);
		System.out.println(responseElement.getText());
	}

	private static void uploadByteArray(RPCServiceClient client, String serverNameSpaceURI, String fileName,
			byte[] objectArray, Customer customer) throws AxisFault {
		String uploadFilePath = "uploads" + File.separator;
		
		
		uploadFileDataHandlerImage(client, serverNameSpaceURI, uploadFilePath, customer.getImage());
		
		
		QName uploadByteArrayQName = new QName(serverNameSpaceURI, "uploadByteArray");
		// Here we use POJO to upload the byte array to the Web service.
		Object[] response = client.invokeBlocking(uploadByteArrayQName, new Object[] { objectArray, fileName },
				new Class[] { String.class });
		// Here we print the results received from the Web service.
		System.out.println(response[0]);
	}
	
	private static void updateData(RPCServiceClient client, String serverNameSpaceURI, String fileName,
			byte[] objectArray, Customer customer) throws AxisFault {
		String uploadFilePath = "uploads" + File.separator;
		uploadFileDataHandlerImage(client, serverNameSpaceURI, uploadFilePath, customer.getImage());
		QName updateCusotmerQName = new QName(serverNameSpaceURI, "updateCusotmer");
		// Here we use POJO to upload the byte array to the Web service.
		Object[] response = client.invokeBlocking(updateCusotmerQName, new Object[] { objectArray, fileName },
				new Class[] { String.class });
		// Here we print the results received from the Web service.
		System.out.println(response[0]);
	}
	
	
	
	
	private static void uploadDataHandler(RPCServiceClient client, String serverNameSpaceURI, String fileName,
			DataHandler dataHandler) throws AxisFault {
		// Here we call a non-AXIOM method
		QName uploadDatahandlerQName = new QName(serverNameSpaceURI, "uploadDatahandler");

		Object[] response = client.invokeBlocking(uploadDatahandlerQName, new Object[] { dataHandler, fileName },
				new Class[] { String.class });
		// Here we print the results received from the Web service.
		System.out.println(response[0]);
	}

	private static void uploadFileDataHandlerImage(RPCServiceClient client, String nameSpaceURI, String uploadFilePath,
			String fileName) {

		File uploadFile = new File(uploadFilePath + fileName);

		if (!uploadFile.exists()) {

			System.out.println(uploadFile.getAbsolutePath() + " does not exist!");
			return;

		}

		QName uploadDatahandlerQName = new QName(nameSpaceURI, "uploadDatahandlerImage");
		DataHandler dh = new DataHandler(new FileDataSource(uploadFile.getAbsolutePath()));

		try {
			Object[] response = client.invokeBlocking(uploadDatahandlerQName, new Object[] { dh, fileName },
					new Class[] { String.class });

			System.out.println(response[0]);

		} catch (AxisFault e) {
			e.printStackTrace();
		}

	}

	private static ArrayList<Customer> readDataFromFiles(String serverDir, String fileName) {
		File file = new File(serverDir + fileName);
		if (!file.exists()) {
			return new ArrayList<Customer>();
		}

		FileInputStream fileInputStream = null;

		try {

			// Here we read object data from the data handler
			fileInputStream = new FileInputStream(file);

			byte[] bytes = new byte[fileInputStream.available()];
			fileInputStream.read(bytes);
			// Here we create an ObjectInputStream for reading objects
			// from an array of bytes.
			// ObjectInputStream objectInputSream = new ObjectInputStream(is);
			ObjectInputStream objectInputSream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			@SuppressWarnings("unchecked")
			ArrayList<Customer> customerList = (ArrayList<Customer>) objectInputSream.readObject();
			return customerList;
			// confirmation += file.getAbsolutePath() + " " + file.exists() +
			// " length:" + file.length();
		} catch (IOException e) {
			throw new Error(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new Error(e.getMessage());
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					throw new Error(e.getMessage());
				}
			}
		}
	}

	private static void downloadFileOM(RPCServiceClient client, String nameSpaceURI, String downloadFilePath,
			String downloadFileName) throws AxisFault {

		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace(nameSpaceURI, "fd");
		OMElement methodElement = omFactory.createOMElement("downloadFile", omNameSpace);
		OMElement fileNameArgumentElement = omFactory.createOMElement("file_name", omNameSpace);
		fileNameArgumentElement.setText(downloadFileName);
		methodElement.addChild(fileNameArgumentElement);

		// methodElement.addChild(textData);
		OMElement fileDownloadResponseElement = client.sendReceive(methodElement);
		// Here we get the file name element from the response OMElement
		OMElement fileNameElement = (OMElement) fileDownloadResponseElement.getChildrenWithLocalName("file_name")
				.next();
		String serverNameSpaceURI = fileNameElement.getNamespace().getNamespaceURI();
		String erroMessage = fileNameElement.getAttributeValue(new QName(serverNameSpaceURI, "IOError"));

		if (erroMessage != null) {
			System.out.println(erroMessage);
			return;
		}

		OMNode dataOMNode = ((OMElement) fileDownloadResponseElement.getChildrenWithLocalName("file_content").next())
				.getFirstOMChild();

		File downloadFile = new File(downloadFilePath + downloadFileName);

		FileOutputStream fileOutputStream = null;
		DataHandler dataHandler = null;
		try {
			fileOutputStream = new FileOutputStream(downloadFile);
			if (dataOMNode instanceof OMText) {
				OMText txt = (OMText) dataOMNode;
				if (txt.isOptimized()) {
					dataHandler = (DataHandler) txt.getDataHandler();

				} else {

					byte[] data = Base64.getDecoder().decode(txt.getText());
					dataHandler = new DataHandler(new ByteArrayDataSource(data));

				}

				dataHandler.writeTo(fileOutputStream);

			}

			System.out.println(downloadFile.getAbsolutePath() + " downloaded? " + downloadFile.exists() + " length:"
					+ downloadFile.length());

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}