package ws.employee.service;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import ws.employee.data.Employee;

public class EmployeeSpringService {
	
	@Autowired
	private Hashtable<Integer, Employee> employees;

	public void setEmployees(Hashtable<Integer, Employee> employees) {
		this.employees = employees;
	}
	
	public  Hashtable<Integer, Employee>  getEmployees(){
        return employees;
    }
	
	public double averageSalary() {
		double averageSalary = 0;
		double totalSalary = 0;
		
		Set<Entry<Integer, Employee>> entrySet = employees.entrySet();
		Iterator<Entry<Integer, Employee>> iterator = entrySet.iterator();
		 
        while(iterator.hasNext()) {
        	
            Entry<Integer, Employee> entry1 = iterator.next();
        	double salary = entry1.getValue().getSalary();
			totalSalary += salary;
        }
        averageSalary = totalSalary/employees.size();
        return averageSalary;
	}
	
	public OMElement getEmployeeByID(OMElement element) throws XMLStreamException {
		// Here we build the xml tree using AXIOM API for the request
		// element

		element.build();
		// Here we detach the node from its parent container
		element.detach();
		// Here we get the first element, which is personToGreet .
		OMElement firstElement = element.getFirstElement();
		// Here we get the value for " personToGreet " element.
		String employeeID = firstElement.getText();

		Employee employee = new Employee();

		if (this.employees.get(Integer.parseInt(employeeID)) != null)
			employee = (Employee) this.employees.get(Integer.parseInt(employeeID));

		// In the following we build a response.
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.employee.ws/xsd",
				"getEmployeeByIDResponse");

		OMElement methodElement = omFactory.createOMElement("getEmployeeByIDResponse", omNameSpace);

		/*
		 * OMElement employeeNameElement = omFactory.createOMElement("employee_name",
		 * omNameSpace); employeeNameElement.setText(employeeID);
		 * methodElement.addChild(employeeNameElement);
		 */

		OMElement employeeElement = omFactory.createOMElement("employee", omNameSpace);
		employeeElement.setText(employee.toString());
		methodElement.addChild(employeeElement);

		return methodElement;
	}
	
	public OMElement getListEmployeesByJob(OMElement element) throws XMLStreamException {
		// Here we build the xml tree using AXIOM API for the request
		// element

		element.build();
		// Here we detach the node from its parent container
		element.detach();
		// Here we get the first element, which is personToGreet .
		OMElement firstElement = element.getFirstElement();
		// Here we get the value for " personToGreet " element.
		String jobTitle = firstElement.getText();
		String res = "";
		Set<Entry<Integer, Employee>> entrySet = employees.entrySet();
		Iterator<Entry<Integer, Employee>> iterator = entrySet.iterator();
		 
        while(iterator.hasNext()) {
 
            Entry<Integer, Employee> entry2 = iterator.next();
            if(entry2.getValue().getJob().equals(jobTitle)) {
            	res += employees.get(entry2.getValue().getEmployeeId()).toString() + "\n";
            }
        }
        
		// In the following we build a response.
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.employee.ws/xsd",
				"getListEmployeesByJobResponse");

		OMElement methodElement = omFactory.createOMElement("getListEmployeesByJobResponse", omNameSpace);
		
		OMElement listEmployeesByJobToText = omFactory.createOMElement("listEmployeesByJobToText", omNameSpace);
		listEmployeesByJobToText.setText(res);
		methodElement.addChild(listEmployeesByJobToText);

		return methodElement;
	}
	
	public OMElement updateEmployee(OMElement element) throws XMLStreamException {
		// Here we build the xml tree using AXIOM API for the request
		// element

		element.build();
		// Here we detach the node from its parent container
		element.detach();
		// Here we get the first element, which is personToGreet .
		OMElement firstElement = element.getFirstElement();
		// Here we get the value for " personToGreet " element.
		Employee employee = new Employee();

		
		
		String newEmployee = firstElement.getText();
		String[] array = newEmployee.split(",");
		
		if (this.employees.get(Integer.parseInt(array[0])) != null)
			employee = (Employee) this.employees.get(Integer.parseInt(array[0]));
		
		employee.setEmployeeName(array[1]);
		employee.setHireDate(array[2]);
		employee.setJob(array[3]);
		employee.setSalary(Double.parseDouble(array[4]));
		
        
		// In the following we build a response.
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.employee.ws/xsd",
				"updateEmployeeResponse");

		OMElement methodElement = omFactory.createOMElement("updateEmployeeResponse", omNameSpace);
		
		OMElement updateEmployeeText = omFactory.createOMElement("updateEmployeeText", omNameSpace);
		updateEmployeeText.setText("Successful to update");
		methodElement.addChild(updateEmployeeText);

		return methodElement;
	}
	
	public OMElement salaryComparisonEmployee(OMElement element) throws XMLStreamException {
		// Here we build the xml tree using AXIOM API for the request
		// element

		element.build();
		// Here we detach the node from its parent container
		element.detach();
		// Here we get the first element, which is personToGreet .
		OMElement firstElement = element.getFirstElement();
		
		String comparison = firstElement.getText();
		
		Set<Entry<Integer, Employee>> entrySet = employees.entrySet();
		Iterator<Entry<Integer, Employee>> iterator = entrySet.iterator();
        String res = "";
        while(iterator.hasNext()) {
        	 
            Entry<Integer, Employee> entry2 = iterator.next();
            double employeeSalary = entry2.getValue().getSalary();
            if(comparison.equals("1")) {
            	if(employeeSalary > averageSalary()) {
                	res += employees.get(entry2.getValue().getEmployeeId()).toString() + "\n";
                }
            } else if(comparison.equals("2")) {
            	if(employeeSalary < averageSalary()) {
                	res += employees.get(entry2.getValue().getEmployeeId()).toString() + "\n";
                }
            } else {
            	res += "Something went wrong!";
            	break;
            }
        }
        
        // In the following we build a response.
 		OMFactory omFactory = OMAbstractFactory.getOMFactory();
 		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.employee.ws/xsd",
 				"salaryComparisonEmployeeResponse");

 		OMElement methodElement = omFactory.createOMElement("salaryComparisonEmployeeResponse", omNameSpace);
 		
 		OMElement salaryComparisonEmployee = omFactory.createOMElement("salaryComparisonEmployee", omNameSpace);
 		salaryComparisonEmployee.setText(res);
 		methodElement.addChild(salaryComparisonEmployee);

 		return methodElement;
	}
}