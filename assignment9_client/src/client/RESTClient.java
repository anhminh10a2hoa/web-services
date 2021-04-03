package client;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientProperties;

import data.Customer;
public class RESTClient {
	private Client client;
	private String BASE_SERVICE_URL = "http://localhost:8080/example15_server-e1800956/rest/";

	private void init() {
		this.client = ClientBuilder.newClient();
	}
	    
    public static void main(String[] args) throws IOException {
    	RESTClient client = new RESTClient();
    	client.init();
    	Scanner scanner = new Scanner(System.in);
		int option = 0;
		
		do {
			System.out.println("2/ Add data");
			System.out.println("3/ Get Customer By Id");
			System.out.println("4/ Get all customer");
			System.out.println("5/ Upload data");
			System.out.println("6/ Download data");
			System.out.println("Choose your option");
			option = Integer.parseInt(scanner.nextLine());
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
				client.addCustomer(customer);
			}
			if (option == 3) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());

				client.getCustomerById(id);
			}
			if (option == 4) {
				client.getAllCustomer();
			}
			if (option == 5) {
				System.out.println("File name: ");
				String fileName = scanner.nextLine();

				client.uploadFile(fileName);
			}
			if (option == 6) {
				System.out.println("File name: ");
				String fileName = scanner.nextLine();

				client.downloadFile(fileName);
			}
		} while (option != 0);
		scanner.close();
    }
    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/example15_server-e1800956/rest").build();
    }
    
   
    public void uploadFile(String fileName) {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        Client client = ClientBuilder.newClient();
        // We set REQUEST_ENTITY_PROCESSING to CHUNKED for uploading possible large
        // files
        client.property(ClientProperties.REQUEST_ENTITY_PROCESSING, "CHUNKED");
        WebTarget target = client.target(getBaseURI());
        // Here we set reference to the file
        String filePathName = "uploads" + File.separator + fileName;
        File uploadFile = new File(filePathName);
        InputStream fileInStream = null;
        try {
            fileInStream = new FileInputStream(uploadFile);
            String contentDisposition = "attachment; filename=\"" + uploadFile.getName() + "\"";
            // Here we send the request and receive the response
            Response response = target.path("file").path("service").path("upload").path(fileName)
					.request(MediaType.APPLICATION_JSON)
					.header("Content-Disposition", contentDisposition)
					.header("Content-Length", (int) uploadFile.length())
					.post(Entity.entity(fileInStream, MediaType.APPLICATION_OCTET_STREAM_TYPE));
            System.out.println("Server response : " + response.getEntity().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void downloadFile(String fileName) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(getBaseURI());
        Response response = target.path("file").path("service").path("download").path(fileName)
				.request().accept(MediaType.APPLICATION_OCTET_STREAM).get();
        // Here we check the response status from the server and then proceed
        // Here we read the InputStream from the server
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
	        InputStream downloadInputStream = (InputStream) response.getEntity();
	        // Here we we make preparations for saving the file
	        String savePathName = "downloads" + File.separator + fileName;
	        File downloadFile = new File(savePathName);
	        FileOutputStream outputStream = null;
	        try {
	            outputStream = new FileOutputStream(downloadFile);
	            int read = 0;
	            byte[] bytes = new byte[1024];
	            // In the following we save the received content to the file permanently
	            while ((read = downloadInputStream.read(bytes)) != -1) {
	                try {
	                    outputStream.write(bytes, 0, read);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        } finally {
	            try {
	                outputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
        }
    }
    
    public void getAllCustomer() {
        GenericType<List<Customer>> genericList = new GenericType<List<Customer>>() {
		};
		List<Customer> customerList = client.target(BASE_SERVICE_URL).path("file").path("service").path("customers")
                .request().accept(MediaType.APPLICATION_JSON).get(genericList);
        
        for(Customer c: customerList) {
        	System.out.println(c.toString());
        }
    }
    
    public void getCustomerById(int id) {
        GenericType<Customer> genericCustomer = new GenericType<Customer>() {
		};
        Customer customer = client.target(BASE_SERVICE_URL).path("file").path("service").path("customers").path(id + "")
				.resolveTemplate("id", id).request(MediaType.APPLICATION_JSON).get(genericCustomer);
        
        System.out.println(customer.toString());
    }
    
    public void addCustomer(Customer customer) {
        String res = client.target(BASE_SERVICE_URL).path("file").path("service").path("addCustomers").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(customer, MediaType.APPLICATION_JSON), String.class);
        
        System.out.println(res);
    }
}