//This is the content of client/RPCClient.java file.
package client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import org.apache.axiom.attachments.ByteArrayDataSource;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import data.Customer;

public class RPCClient {
	public static void main(String[] args) throws Exception {
		// Here we define an ArrayList collection of customer data.
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		// Here we add some Customer objects to the ArrayList collection.
		customerList.add(new Customer("Shahpour", 100, "a.png", "doctor", new Date(), 2000.50));
		customerList
				.add(new Customer("Anushirevan", 200, "b.png", "software engineer", new Date(), 2000.50));
		customerList.add(new Customer("Keyvan", 300, "c.png", "actor", new Date(), 2000.50));
		// In the following we create a byte array of the ArrayList object.
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		// Here we write the customerList ArrayList to the
		// byteArrayOutputStream.
		objectOutputStream.writeObject(customerList);
		objectOutputStream.close();
		byteArrayOutputStream.close();

		// Here we convert the content of the byteArrayOutputStream to the
		// array of bytes.
		byte[] objectArray = byteArrayOutputStream.toByteArray();
		// Here we create a data source with the objectArray.
		ByteArrayDataSource dataSource = new ByteArrayDataSource(objectArray);
		// Here we create the DataHandler object.
		DataHandler dataHandler = new DataHandler(dataSource);

		RPCServiceClient client = new RPCServiceClient();
		Options options = new Options();
		// EndpointReference targetEndPoint = new
		// EndpointReference("http://app2.cc.puv.fi/axis2/services/mg_mixed_file_object_upload_2");
		EndpointReference targetEndPoint = new EndpointReference(
				"http://localhost:8080/axis2/services/soap_object_exchange");
		String serverNameSpaceURI = "http://service.soap";
		options.setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
		options.setTo(targetEndPoint);
		client.setOptions(options);

		String fileName = "customers.dat";
		uploadOM(customerList, client, serverNameSpaceURI, fileName, dataHandler);

		// Here we call searchData() method

		int searchCustomerId = 100;
		String searchFileName = "om_" + fileName;
		searchData(client, serverNameSpaceURI, searchCustomerId, searchFileName);

	}

	private static void searchData(RPCServiceClient client, String serverNameSpaceURI, int searchCustomerId,
			String searchFileName) throws AxisFault {
		Object[] response;
		QName searchDataQName = new QName(serverNameSpaceURI, "searchData");
		// Here we use POJO to upload the byte array to the Web service.
		response = client.invokeBlocking(searchDataQName, new Object[] { searchFileName, searchCustomerId },
				new Class[] { String.class });
		// Here we print the results received from the Web service.
		System.out.println(response[0]);
	}

	private static void uploadOM(ArrayList<Customer> customerList, RPCServiceClient client, String serverNameSpaceURI,
			String fileName, DataHandler dataHandler) throws AxisFault {

		// Upload image to server when adding
		String uploadFilePath ="uploads"+ File.separator;
		for (Customer c : customerList) {
			uploadFileOM(client, serverNameSpaceURI, uploadFilePath, c.getImage());
		}

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

	private static void uploadFileOM(RPCServiceClient client, String nameSpaceURI, String uploadFilePath,
			String fileName) {

		File uploadFile = new File(uploadFilePath + fileName);

		if (!uploadFile.exists()) {

			System.out.println(uploadFile.getAbsolutePath() + " does not exist!");
			return;

		}

		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace(nameSpaceURI, "ufo");
		OMElement methodElement = omFactory.createOMElement("uploadImageOMElement", omNameSpace);
		OMElement fileNameArgumentElement = omFactory.createOMElement("file_name", omNameSpace);
		fileNameArgumentElement.setText(fileName);
		methodElement.addChild(fileNameArgumentElement);

		// Here we creating the Data Handler for the file with FileDataSource,
		// which
		// implementation javax.activation.DataSource interface.
		DataHandler dataHandler = new DataHandler(new FileDataSource(uploadFile.getAbsolutePath()));
		// Here we create an OMText node with the above DataHandler and set
		// optimised to true.
		OMText textData = omFactory.createOMText(dataHandler, true);

		OMElement fileContentArgumentElement = omFactory.createOMElement("file_content", omNameSpace);
		fileContentArgumentElement.addChild(textData);
		methodElement.addChild(fileContentArgumentElement);

		// methodElement.addChild(textData);
		OMElement fileUploadResponseElement;
		try {
			fileUploadResponseElement = client.sendReceive(methodElement);
			System.out.println(fileUploadResponseElement.getText());

		} catch (AxisFault e) {
			e.printStackTrace();
		}

	}
}