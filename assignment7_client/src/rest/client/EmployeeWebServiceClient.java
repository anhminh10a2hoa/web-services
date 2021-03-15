package rest.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import restful.data.Employee;

public class EmployeeWebServiceClient {

	private Client client;
	private String BASE_SERVICE_URL = "http://app3.cc.puv.fi/assignment7_e1800956/rest/empService/";

	private void init() {
		this.client = ClientBuilder.newClient();
	}

	public static void main(String[] args) {

		EmployeeWebServiceClient employeeWSClient = new EmployeeWebServiceClient();
		employeeWSClient.init();
		employeeWSClient.getSupportedOperations();
		Scanner scanner = new Scanner(System.in);
		int option = 0;

		do {
			System.out.println("1/ Get all data");
			System.out.println("2/ Add data");
			System.out.println("3/ Search data by Id");
			System.out.println("4/ Update data");
			System.out.println("5/ Delete data");
			System.out.println("6/ Search data by name");
			System.out.println("7/ Search data by purchase");
			System.out.println("Choose your option");
			option = Integer.parseInt(scanner.nextLine());
			if (option == 1) {
				employeeWSClient.getAllEmployeesJSON();
			}

			if (option == 2) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				System.out.println("Name: ");
				String name = scanner.nextLine();
				System.out.println("Phone number: ");
				String phoneNumber = scanner.nextLine();
				System.out.println("Purchase: ");
				double purchase = Double.parseDouble(scanner.nextLine());
				employeeWSClient.addEmployeeObject(new Employee(id, name, phoneNumber, purchase));
			}

			if (option == 3) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				employeeWSClient.getEmployee(id);
			}

			if (option == 4) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				System.out.println("Name: ");
				String name = scanner.nextLine();
				System.out.println("Phone number: ");
				String phoneNumber = scanner.nextLine();
				System.out.println("Purchase: ");
				double purchase = Double.parseDouble(scanner.nextLine());
				employeeWSClient.updateEmployee(id, name, phoneNumber, purchase);
			}

			if (option == 5) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				employeeWSClient.deleteEmployee(id);
			}

			if (option == 6) {
				System.out.println("Name: ");
				String name = scanner.nextLine();
				employeeWSClient.getAllEmployeesByNameJSON(name);
			}

			if (option == 7) {
				System.out.println("Purchase: ");
				double purchase = Double.parseDouble(scanner.nextLine());
				System.out.println("Higher (true) Lower (false): ");
				boolean higherOrLower = Boolean.parseBoolean(scanner.nextLine());
				employeeWSClient.getAllEmployeesByPurchaseJSON(purchase, higherOrLower);
			}

		} while (option != 0);
		scanner.close();

	}

	private void getSupportedOperations() {
		String result = client.target(BASE_SERVICE_URL).path("operations").request(MediaType.TEXT_XML)
				.options(Response.class).getHeaderString("Operations").toString();

		System.out.println("Supported services: " + result);

	}

	private void getAllEmployeesJSON() {

		GenericType<List<Employee>> list = new GenericType<List<Employee>>() {
		};

		List<Employee> employees = client.target(BASE_SERVICE_URL).path("all_employees_json")
				.request(MediaType.APPLICATION_JSON).get(list);

		System.out.println("JSON List of all users:");
		for (Employee emp : employees)
			System.out.println(emp);
	}

	private void getAllEmployeesByNameJSON(String name) {

		GenericType<List<Employee>> list = new GenericType<List<Employee>>() {
		};

		List<Employee> employees = client.target(BASE_SERVICE_URL).path("all_employees_json_by_name")
				.path("{employeeName}").resolveTemplate("employeeName", name).request(MediaType.APPLICATION_JSON)
				.get(list);

		System.out.println("JSON List of all users:");
		for (Employee emp : employees)
			System.out.println(emp);
	}

	private void getAllEmployeesByPurchaseJSON(double purchase, boolean higherOrLower) {

		GenericType<List<Employee>> list = new GenericType<List<Employee>>() {
		};

		List<Employee> employees = client.target(BASE_SERVICE_URL).path("all_employees_json_by_purchase")
				.path("{purchase}").resolveTemplate("purchase", purchase).path("{higherOrLower}")
				.resolveTemplate("higherOrLower", higherOrLower).request(MediaType.APPLICATION_JSON).get(list);

		System.out.println("JSON List of all users:");
		for (Employee emp : employees)
			System.out.println(emp);
	}

	private void getEmployee(int employeeID) {
		Employee sampleEmployee = new Employee();
		sampleEmployee.setId(employeeID);
		Employee employee = client.target(BASE_SERVICE_URL).path("employees").path("{employeeID}")
				.resolveTemplate("employeeID", employeeID).request(MediaType.APPLICATION_JSON).get(Employee.class);

		System.out.println("Employee with id=" + employeeID + ": " + employee.toString());
	}

	private void updateEmployee(int id, String newName, String newPhoneNumber, double newPurchase) {
		Form form = new Form();
		form.param("id", "" + id);
		form.param("name", newName);
		form.param("phoneNumber", newPhoneNumber);
		form.param("purchase", "" + newPurchase);

		String result = client.target(BASE_SERVICE_URL).path("update_employee").request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);

		System.out.println("Results of employee updtae: " + result);
	}

	private void addEmployeeObject(Employee emp) {

		String result = client.target(BASE_SERVICE_URL).path("addEmployeeObject").request(MediaType.APPLICATION_XML)
				.post(Entity.entity(emp, MediaType.APPLICATION_XML), String.class);

		System.out.println("Results of adding employee object: " + result);
	}

	private void deleteEmployee(int id) {
		String result = client.target(BASE_SERVICE_URL).path("employees").path("{employeeID}")
				.resolveTemplate("employeeID", id).request(MediaType.APPLICATION_XML).delete(String.class);

		System.out.println("Results of deleting employee: " + result);
	}

}