package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import ws.customer.data.Customer;

public class FileHandler {

	private static String destinationDir, dbFileName;
	private static File fileSaveData;

	public static void setDestinationDir(String destinationDir) {
		FileHandler.destinationDir = destinationDir;
	}

	public static void setDBFileName(String dbFileName) {
		FileHandler.dbFileName = dbFileName;
		fileSaveData = new File(FileHandler.destinationDir + FileHandler.dbFileName);
	}
	
	public FileHandler() {
		File destinationDirFile = new File(destinationDir);
		if (!destinationDirFile.exists())
			destinationDirFile.mkdirs();
	}

	@SuppressWarnings({ "unchecked", "resource" })
	public ArrayList<Customer> getAllCustomer(){
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		try {
			FileInputStream in = new FileInputStream(fileSaveData);
			ObjectInputStream is = new ObjectInputStream(in);
			customerList = ((ArrayList<Customer>) is.readObject());
			is.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return customerList;
	}
	
	public Customer getCustomerById(int id) {
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		customerList = getAllCustomer();
		
		Customer customer = new Customer();

		for (Customer c : customerList) {
			if (c.getCustomerID().equals(id)) {
				customer = c;
				download(c.getImage());
				break;
			}
		}
		return customer;
	}
	
	public String addCustomer(Customer customer) {
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		customerList = getAllCustomer();
		
		customerList.add(customer);
		
		
		try {
			FileOutputStream out = new FileOutputStream(fileSaveData);
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(customerList);
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "add successfully";
	}

	public String saveFile(InputStream uploadedInputStream, String fileName) {
		StringBuilder feedback = new StringBuilder();
		File destinationFile = new File(destinationDir + fileName);

		try {
			OutputStream outpuStream = new FileOutputStream(new File(destinationDir + fileName));
			int read = 0;
			byte[] bytes = new byte[1024];
			outpuStream = new FileOutputStream(new File(destinationDir + fileName));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}

			outpuStream.close();

			feedback.append(destinationFile.getAbsolutePath() + " exists? " + destinationFile.exists() + ", size="
					+ destinationFile.length());

		} catch (FileNotFoundException e) {

			feedback.append(e.getLocalizedMessage());
		} catch (IOException e) {

			feedback.append(e.getLocalizedMessage());

		}

		return feedback.toString();
	}

//This methods returns the content of requested file as part of a Response object
	public Response download(String fileName) {

		StringBuilder feedback = new StringBuilder();

		@SuppressWarnings("unused")
		String fileLocation = destinationDir + fileName;
		Response response = null;

		// Here we define a locale independent number format
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setGroupingUsed(true);

		// Here we create a file object, which refers to the requested file
		File file = new File(destinationDir + fileName);

		if (file.exists()) {
			ResponseBuilder builder = Response.ok(file);
			builder.header("Content-Disposition", "attachment; filename=" + file.getName());
			builder.header("Content-Length", numberFormat.format(file.length()));
			response = builder.build();

		} else {

			feedback.append(fileName + " not found on the server!");

			response = Response.status(404).entity("FILE NOT FOUND: " + feedback.toString())
					.type(MediaType.APPLICATION_JSON).build();
		}

		return response;
	}

}