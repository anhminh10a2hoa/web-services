package client;

import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class Client {
	
	public static void writeToFile(String fileName, String content) throws AxisFault {
		EndpointReference targetEPR = new EndpointReference(
				"http://localhost:8080/axis2/services/Assignment4");
		Options options = new Options();
		options.setTo(targetEPR);
		ServiceClient client = new ServiceClient();
		client.setOptions(options);
		
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.file/xsd", "fs");
		
		OMElement methodElement = omFactory.createOMElement("writeToFile", omNameSpace);
		OMElement fileNameArgumentElement = omFactory.createOMElement("file_name", omNameSpace);
		fileNameArgumentElement.setText(fileName);

		OMElement fileContentArgumentElement = omFactory.createOMElement("file_content", omNameSpace);
		fileContentArgumentElement.setText(content);

		methodElement.addChild(fileNameArgumentElement);
		methodElement.addChild(fileContentArgumentElement);
		OMElement responseElement = client.sendReceive(methodElement);
		
		System.out.println(responseElement.getFirstElement().getText());
	}
	
	public static void getListFiles() throws AxisFault {
		EndpointReference targetEPR = new EndpointReference(
				"http://localhost:8080/axis2/services/Assignment4");
		Options options = new Options();
		options.setTo(targetEPR);
		ServiceClient client = new ServiceClient();
		client.setOptions(options);
		
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.file/xsd", "fs");
		
		OMElement methodElement = omFactory.createOMElement("getFileList", omNameSpace);
		OMElement responseElement = client.sendReceive(methodElement);
		responseElement = client.sendReceive(methodElement);

		System.out.println(responseElement.getFirstElement().getText());
	}
	
	public static void searchWord(String fileName, String word) throws AxisFault {
		EndpointReference targetEPR = new EndpointReference(
				"http://localhost:8080/axis2/services/Assignment4");
		Options options = new Options();
		options.setTo(targetEPR);
		ServiceClient client = new ServiceClient();
		client.setOptions(options);
		
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.file/xsd", "fs");
		
		OMElement methodElement = omFactory.createOMElement("findText", omNameSpace);
		OMElement fileNameArgumentElement = omFactory.createOMElement("file_name", omNameSpace);
		fileNameArgumentElement.setText(fileName);

		OMElement searchWordArgumentElement = omFactory.createOMElement("search_word", omNameSpace);
		searchWordArgumentElement.setText(word);

		methodElement.addChild(fileNameArgumentElement);
		methodElement.addChild(searchWordArgumentElement);
		OMElement responseElement = client.sendReceive(methodElement);
		
		System.out.println(responseElement.getFirstElement().getText());
	}
	
	public static void main(String[] args) throws AxisFault {

		Scanner scanner = new Scanner(System.in);
		int option = 0;
		do {
			System.out.println("1/ Get list of files");
			System.out.println("2/ Write to file");
			System.out.println("3/ Find word on file");
			System.out.println("Choose your option");
			option = Integer.parseInt(scanner.nextLine());
			if(option == 1) {
				getListFiles();
			}
			else if(option == 2) {
				System.out.println("File name: ");
				String fileName = scanner.nextLine();
				System.out.println("Your content: ");
				String content = scanner.nextLine();
				writeToFile(fileName, content);
			}
			else if(option == 3) {
				System.out.println("File name: ");
				String fileName = scanner.nextLine();
				System.out.println("Word: ");
				String word = scanner.nextLine();
				searchWord(fileName, word);
			}
			else {
				System.out.println("Bye!!");
				break;
			}
		} while (option != 0);
		scanner.close();

	}
}