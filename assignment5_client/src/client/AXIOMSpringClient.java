package client;
import java.util.Iterator;
import java.util.Scanner;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
public class AXIOMSpringClient {
	
	public static void getEmployeeByID(ServiceClient client) throws AxisFault {
		Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter employee id: ");
        String id = scanner.nextLine();
        
        OMFactory omFactory = OMAbstractFactory.getOMFactory();
        OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.employee.ws", "getEmployeeByID");
        OMElement methodElement = omFactory.createOMElement("getEmployeeByID", omNameSpace);
        OMElement argumentElement = omFactory.createOMElement("id", omNameSpace);
        argumentElement.setText(id);
        /*
         * argumentElement.addChild(omFactory .createOMText(argumentElement, person));
         */
        methodElement.addChild(argumentElement);
        System.out.println(methodElement.getFirstElement().getText());
        
        OMElement responseElement = client.sendReceive(methodElement);
        @SuppressWarnings("unchecked")
        Iterator<OMElement> childElements = responseElement.getChildElements();
        OMElement e;
        while (childElements.hasNext()) {
            e = childElements.next();
            System.out.println(e.getText());
        }
	}
	
	public static void getListEmployeesByJob(ServiceClient client) throws AxisFault {
		Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter job title: ");
        String jobTitle = scanner.nextLine();
        
        OMFactory omFactory = OMAbstractFactory.getOMFactory();
        OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.employee.ws", "getListEmployeesByJob");
        OMElement methodElement = omFactory.createOMElement("getListEmployeesByJob", omNameSpace);
        OMElement argumentElement = omFactory.createOMElement("jobTitle", omNameSpace);
        argumentElement.setText(jobTitle);
        /*
         * argumentElement.addChild(omFactory .createOMText(argumentElement, person));
         */
        methodElement.addChild(argumentElement);
        System.out.println(methodElement.getFirstElement().getText());
        
        OMElement responseElement = client.sendReceive(methodElement);
        @SuppressWarnings("unchecked")
        Iterator<OMElement> childElements = responseElement.getChildElements();
        OMElement e;
        while (childElements.hasNext()) {
            e = childElements.next();
            System.out.println(e.getText());
        }
	}
	
	public static void updateEmployee(ServiceClient client, Integer id, String name, String hireDate, String job, double salary) throws AxisFault {
        String newEmployee = id.toString() + "," + name + "," + hireDate + "," + job + "," + Double.toString(salary);
        
        OMFactory omFactory = OMAbstractFactory.getOMFactory();
        OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.employee.ws", "updateEmployee");
        OMElement methodElement = omFactory.createOMElement("updateEmployee", omNameSpace);
        OMElement argumentElement = omFactory.createOMElement("newEmployee", omNameSpace);
        argumentElement.setText(newEmployee);
        /*
         * argumentElement.addChild(omFactory .createOMText(argumentElement, person));
         */
        methodElement.addChild(argumentElement);
        System.out.println(methodElement.getFirstElement().getText());
        
        OMElement responseElement = client.sendReceive(methodElement);
        @SuppressWarnings("unchecked")
        Iterator<OMElement> childElements = responseElement.getChildElements();
        OMElement e;
        while (childElements.hasNext()) {
            e = childElements.next();
            System.out.println(e.getText());
        }
	}
	
	public static void salaryComparisonEmployee(ServiceClient client) throws AxisFault {
		Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the option: ");
        System.out.println("1: Bigger than the average salary ");
        System.out.println("2: Smaller than the average salary ");
        String comparison = scanner.nextLine();
        
        OMFactory omFactory = OMAbstractFactory.getOMFactory();
        OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.employee.ws", "salaryComparisonEmployee");
        OMElement methodElement = omFactory.createOMElement("salaryComparisonEmployee", omNameSpace);
        OMElement argumentElement = omFactory.createOMElement("comparison", omNameSpace);
        argumentElement.setText(comparison);
        /*
         * argumentElement.addChild(omFactory .createOMText(argumentElement, person));
         */
        methodElement.addChild(argumentElement);
        System.out.println(methodElement.getFirstElement().getText());
        
        OMElement responseElement = client.sendReceive(methodElement);
        @SuppressWarnings("unchecked")
        Iterator<OMElement> childElements = responseElement.getChildElements();
        OMElement e;
        while (childElements.hasNext()) {
            e = childElements.next();
            System.out.println(e.getText());
        }
	}
	
    public static void main(String[] args) throws AxisFault {
// Here we set the service address
        EndpointReference targetEPR = new EndpointReference(
                "http://localhost:8080/axis2/services/assignment5/AXIOMSpringEmployeeService");
        Options options = new Options();
        options.setTo(targetEPR);
        ServiceClient client = new ServiceClient();
        client.setOptions(options);
        
        Scanner scanner = new Scanner(System.in);
		int option = 0;
		do {
			System.out.println("1/ Get employee by Id");
			System.out.println("2/ Get list employees");
			System.out.println("3/ Update employee by Id");
			System.out.println("4/ Salary comparision");
			System.out.println("Choose your option");
			option = Integer.parseInt(scanner.nextLine());
			if(option == 1) {
				getEmployeeByID(client);
			}
			else if(option == 2) {
				getListEmployeesByJob(client);
			}
			else if(option == 3) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				System.out.println("Name: ");
				String name = scanner.nextLine();
				System.out.println("Hire date: ");
				String hireDate = scanner.nextLine();
				System.out.println("Job: ");
				String job = scanner.nextLine();
				System.out.println("Salary: ");
				double salary = Double.parseDouble(scanner.nextLine());
				updateEmployee(client, id, name, hireDate, job, salary);
			}
			else if(option == 4) {
				salaryComparisonEmployee(client);
			}
			else {
				System.out.println("Bye!!");
				break;
			}
		} while (option != 0);
		scanner.close();
    }
}