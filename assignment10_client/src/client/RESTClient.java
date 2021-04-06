package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.sse.SseFeature;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import data.Customer;

public class RESTClient {
	private static Client client;

	private static String BASE_SERVICE_URL = "https://localhost:8443/assignment10_server-e1800956/rest/";

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		String trustStorePathName = "C:/Program Files/Apache Software Foundation/Tomcat 8.5/conf/my_truststore123".replace('/',
				File.separatorChar);
		String trustStorePassword = "123456";
		String username = "Jimmy";
		String password = "T1000";

		// The following is one way of setting the path, name and password of
		// the trust store used by the application

		System.setProperty("javax.net.ssl.trustStore", trustStorePathName);
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

		// The following is one way to create and initialise the SSLContext object. This
		// method
		// would require setting javax.net.ssl.trustStore and
		// javax.net.ssl.trustStorePassword
		// properties.
		// SSLContext sslContext = SSLContext.getInstance("TLSv1");
		// sslContext.init(null , null, new java.security.SecureRandom());

		// We can set up SSL connection for particular sites in the trust store in the
		// following way
		// Here we define a SSslConfigurator, which will refer to the custom trust store
		SslConfigurator sslConfigigurator = SslConfigurator.newInstance().trustStoreFile(trustStorePathName)
				.trustStorePassword(trustStorePassword);

		// Here we create a SSLContext object
		SSLContext sslContext = sslConfigigurator.createSSLContext();

		// Here we create a Client with SSE feature, which is a JAX-RS feature
		// that enables Server-Sent Events support.
		Client client = ClientBuilder.newBuilder().sslContext(sslContext).register(SseFeature.class).build();

		// Here we define an HttpAuthenticationFeature object, which holds the
		// authentication
		// required by the server
		HttpAuthenticationFeature auth = HttpAuthenticationFeature.basic(username, password);
		// Here we re-initialise the Client object so tha t it also contains required
		// authentication feature.
		client = ClientBuilder.newBuilder().sslContext(sslContext).register(SseFeature.class).register(auth).build();

		do {
			System.out.println("1/ Delete Customer by id");
			System.out.println("2/ Add data");
			System.out.println("3/ Get Customer By Id");
			System.out.println("4/ Get all customer");
			System.out.println("5/ Upload data");
			System.out.println("6/ Download data");
			System.out.println("6/ Update data");
			System.out.println("Choose your option");
			option = Integer.parseInt(scanner.nextLine());
			if (option == 1) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());
				deleteCustomer(id, client);
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
				addCustomer(customer, client);
			}
			if (option == 3) {
				System.out.println("Id: ");
				int id = Integer.parseInt(scanner.nextLine());

				getCustomerById(id, client);
			}
			if (option == 4) {
				getAllCustomer(client);
			}
			if (option == 5) {
				System.out.println("File name: ");
				String fileName = scanner.nextLine();

				uploadFile(fileName, client);
			}
			if (option == 6) {
				System.out.println("File name: ");
				String fileName = scanner.nextLine();

				downloadFile(fileName, client);
			}
			if (option == 7) {
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
				updateCustomer(customer, client);
			}
		} while (option != 0);
		scanner.close();
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("https://localhost:8443/assignment10_server-e1800956/rest").build();
	}

	public static void uploadFile(String fileName, Client client) {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
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
					.request(MediaType.APPLICATION_JSON).header("Content-Disposition", contentDisposition)
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

	public static void downloadFile(String fileName, Client client) {
		WebTarget target = client.target(getBaseURI());
		Response response = target.path("file").path("service").path("download").path(fileName).request()
				.accept(MediaType.APPLICATION_OCTET_STREAM).get();
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

	public static void getAllCustomer(Client client) {
		GenericType<List<Customer>> genericList = new GenericType<List<Customer>>() {
		};
		List<Customer> customerList = client.target(BASE_SERVICE_URL).path("file").path("service").path("customers")
				.request().accept(MediaType.APPLICATION_JSON).get(genericList);

		for (Customer c : customerList) {
			System.out.println(c.toString());
		}
	}

	public static void getCustomerById(int id, Client client) {
		GenericType<Customer> genericCustomer = new GenericType<Customer>() {
		};
		Customer customer = client.target(BASE_SERVICE_URL).path("file").path("service").path("customers").path(id + "")
				.resolveTemplate("id", id).request(MediaType.APPLICATION_JSON).get(genericCustomer);
		downloadFile(customer.getImage(), client);
		System.out.println(customer.toString());
	}

	public static void addCustomer(Customer customer, Client client) {
		String res = client.target(BASE_SERVICE_URL).path("file").path("service").path("addCustomers")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(customer, MediaType.APPLICATION_JSON), String.class);
		if (res.equals("add successfully")) {
			uploadFile(customer.getImage(), client);
		}
		System.out.println(res);
	}
	
	public static void deleteCustomer(int id, Client client) {
		String res = client.target(BASE_SERVICE_URL).path("file").path("service").path("deleteCustomer").path(id + "")
				.resolveTemplate("id", id).request(MediaType.APPLICATION_JSON).delete(String.class);
		System.out.println(res);
	}
	
	public static void updateCustomer(Customer customer, Client client) {
		String res = client.target(BASE_SERVICE_URL).path("file").path("service").path("updateCustomer")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(customer, MediaType.APPLICATION_JSON), String.class);
		System.out.println(res);
	}
}