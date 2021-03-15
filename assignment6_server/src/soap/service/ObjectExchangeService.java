package soap.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
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
import data.Customer;
import java.util.Base64;

public class ObjectExchangeService {
	private ResourceBundle bundle;
	String repositoryPath;
	private SimpleDateFormat dateFormat;
	private File repositoryFile;
	private String errorMessage = "";
	private final String lineSeparator = System.lineSeparator();
	final private String nameSpaceURI = "http://soap.service/xsd";

	public ObjectExchangeService() {
		try {
			bundle = ResourceBundle.getBundle("conf.settings", Locale.ROOT);
			// Here we initialise the date format.
			// Here we access the installation directory of Tomcat server and append the
			// destination directory to it
			this.repositoryPath = System.getProperty("catalina.home") + File.separator
					+ bundle.getString("repository_path");
			repositoryFile = new File(repositoryPath);
			if (!repositoryFile.exists())
				repositoryFile.mkdirs();
		} catch (Exception e) {
			errorMessage = "Error with repository path: " + repositoryPath + lineSeparator + e.getMessage();
		}
	}

	public String uploadDatahandler(DataHandler dataHandler, String fileName) {
		// Here we return error message in case there is a problem with the properties
		// file or with the destination directory
		if (!errorMessage.isEmpty())
			return this.errorMessage;

		StringBuilder confirmation = new StringBuilder("uploadDatahandler():" + lineSeparator);
		fileName = "dh_" + fileName;
		File file = new File(repositoryPath + fileName);

		if (!file.exists()) {

		}

		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			dataHandler.writeTo(fileOutputStream);
			confirmation.append("The Object was uploaded successfully to " + file.getAbsolutePath() + ". File size="
					+ file.length() + lineSeparator);
			confirmation.append("------------------------" + lineSeparator);
			/*
			 * confirmation+= file.getAbsolutePath() + " " + file.exists()
			 *
			 * + " length:" + file.length();
			 */
		} catch (IOException e) {
			confirmation.append(e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					confirmation.append(e.getMessage());
				}
			}
		}
		return confirmation.toString();
	}

	@SuppressWarnings({ "resource", "unchecked" })
	public String uploadByteArray(byte[] data, String fileName) throws ClassNotFoundException, IOException {

		// Here we return error message in case there is a problem with the properties
		// file or with the destination directory
		if (!errorMessage.isEmpty())
			return this.errorMessage;

		fileName = "ba_" + fileName;
		File file = new File(repositoryPath + fileName);
		
		StringBuilder confirmation = new StringBuilder("uploadByteArray():" + lineSeparator);
		FileOutputStream fileOutputStream = null;
		
		// Byte to Object customer
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    Customer customer = (Customer) is.readObject();

	    // Check file exist or not
		ArrayList<Customer> customerList;
		if (file.exists()) {
			FileInputStream fileInputStream = null;
		    fileInputStream = new FileInputStream(file);
			byte[] bytes = new byte[fileInputStream.available()];
			fileInputStream.read(bytes);
			// Here we create an ObjectInputStream for reading objects
			// from an array of bytes.
			// ObjectInputStream objectInputSream = new ObjectInputStream(is);
			ObjectInputStream objectInputSream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			customerList = (ArrayList<Customer>) objectInputSream.readObject();
		} else {
			customerList = new ArrayList<Customer>();
		}
		
		customerList.add(customer);

		try {
			// convert to byte
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    ObjectOutputStream os = new ObjectOutputStream(out);
		    os.writeObject(customerList);
		    byte[] newData = out.toByteArray();
			
		    // write to file
			fileOutputStream = new FileOutputStream(file);
			ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(newData);
			DataHandler dataHandler = new DataHandler(byteArrayDataSource);
			dataHandler.writeTo(fileOutputStream);

			confirmation.append("The Object was uploaded successfully to " + file.getAbsolutePath() + ". File size="
					+ file.length() + lineSeparator);
			confirmation.append("------------------------" + lineSeparator);

			// confirmation += file.getAbsolutePath() + " " + file.exists() +
			// " length:" + file.length();
		} catch (IOException e) {
			confirmation.append(e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					confirmation.append(e.getMessage());
				}
			}
		}
		return confirmation.toString();
	}
	
	@SuppressWarnings({ "resource", "unchecked" })
	public String updateCusotmer(byte[] data, String fileName) throws ClassNotFoundException, IOException {

		// Here we return error message in case there is a problem with the properties
		// file or with the destination directory
		if (!errorMessage.isEmpty())
			return this.errorMessage;

		fileName = "ba_" + fileName;
		File file = new File(repositoryPath + fileName);
		
		StringBuilder confirmation = new StringBuilder("uploadByteArray():" + lineSeparator);
		FileOutputStream fileOutputStream = null;
		
		// Byte to Object customer
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    Customer customer = (Customer) is.readObject();

		ArrayList<Customer> customerList;
		
		FileInputStream fileInputStream = null;
	    fileInputStream = new FileInputStream(file);
		byte[] bytes = new byte[fileInputStream.available()];
		fileInputStream.read(bytes);
		// Here we create an ObjectInputStream for reading objects
		// from an array of bytes.
		// ObjectInputStream objectInputSream = new ObjectInputStream(is);
		ObjectInputStream objectInputSream = new ObjectInputStream(new ByteArrayInputStream(bytes));
		customerList = (ArrayList<Customer>) objectInputSream.readObject();
		
		for(Customer c : customerList) {
			if(c.getCustomerID().equals(customer.getCustomerID())) {
				c.setCustomerName(customer.getCustomerName());
				c.setHireDate(customer.getHireDate());
				c.setImage(customer.getImage());
				c.setJob(customer.getJob());
				c.setSalary(customer.getSalary());
			}
		}

		try {
			// convert to byte
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    ObjectOutputStream os = new ObjectOutputStream(out);
		    os.writeObject(customerList);
		    byte[] newData = out.toByteArray();
			
		    // write to file
			fileOutputStream = new FileOutputStream(file);
			ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(newData);
			DataHandler dataHandler = new DataHandler(byteArrayDataSource);
			dataHandler.writeTo(fileOutputStream);

			confirmation.append("The Object was uploaded successfully to " + file.getAbsolutePath() + ". File size="
					+ file.length() + lineSeparator);
			confirmation.append("------------------------" + lineSeparator);

			// confirmation += file.getAbsolutePath() + " " + file.exists() +
			// " length:" + file.length();
		} catch (IOException e) {
			confirmation.append(e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					confirmation.append(e.getMessage());
				}
			}
		}
		return confirmation.toString();
	}

	@SuppressWarnings({ "resource", "unchecked" })
	public String deleteCustomer(Integer id, String fileName) throws ClassNotFoundException, IOException {

		// Here we return error message in case there is a problem with the properties
		// file or with the destination directory
		if (!errorMessage.isEmpty())
			return this.errorMessage;

		fileName = "ba_" + fileName;
		File file = new File(repositoryPath + fileName);
		
		StringBuilder confirmation = new StringBuilder("uploadByteArray():" + lineSeparator);
		FileOutputStream fileOutputStream = null;
		

		ArrayList<Customer> customerList;
		FileInputStream fileInputStream = null;
	    fileInputStream = new FileInputStream(file);
		byte[] bytes = new byte[fileInputStream.available()];
		fileInputStream.read(bytes);
		// Here we create an ObjectInputStream for reading objects
		// from an array of bytes.
		// ObjectInputStream objectInputSream = new ObjectInputStream(is);
		ObjectInputStream objectInputSream = new ObjectInputStream(new ByteArrayInputStream(bytes));
		customerList = (ArrayList<Customer>) objectInputSream.readObject();
		
		Customer deleteCustomer = new Customer();
		for(Customer c : customerList) {
			if(c.getCustomerID().equals(id)) {
				deleteCustomer = c;
			}
		}
		customerList.remove(deleteCustomer);

		try {
			// convert to byte
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    ObjectOutputStream os = new ObjectOutputStream(out);
		    os.writeObject(customerList);
		    byte[] newData = out.toByteArray();
			
		    // write to file
			fileOutputStream = new FileOutputStream(file);
			ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(newData);
			DataHandler dataHandler = new DataHandler(byteArrayDataSource);
			dataHandler.writeTo(fileOutputStream);

			confirmation.append("The Object was uploaded successfully to " + file.getAbsolutePath() + ". File size="
					+ file.length() + lineSeparator);
			confirmation.append("------------------------" + lineSeparator);

			// confirmation += file.getAbsolutePath() + " " + file.exists() +
			// " length:" + file.length();
		} catch (IOException e) {
			confirmation.append(e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					confirmation.append(e.getMessage());
				}
			}
		}
		return confirmation.toString();
	}
	
	
	public OMElement uploadOMElement(OMElement element) {

		StringBuilder confirmation = new StringBuilder("uploadOMElement():" + lineSeparator);
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace(this.nameSpaceURI, "uom");
		OMElement methodElement = omFactory.createOMElement("uploadOMElementResponse", omNameSpace);

		// Here we return error message in case there is a problem with the properties
		// file or with the destination directory
		if (!this.errorMessage.isEmpty()) {
			methodElement.addChild(omFactory.createOMText(methodElement, errorMessage));
			return methodElement;
		}

		element.detach();
		element.build();

		OMNode dataOMNode = element.getFirstElement().getFirstOMChild();
		String nameSpaceURI = element.getFirstElement().getNamespace().getNamespaceURI();
		String fileName = "om_" + element.getFirstElement().getAttributeValue(new QName(nameSpaceURI, "file_name"));

		File file = new File(repositoryPath + fileName);
		FileOutputStream fileOutputStream = null;

		DataHandler dataHandler = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			if (dataOMNode instanceof OMText) {
				OMText txt = (OMText) dataOMNode;
				if (txt.isOptimized()) {
					dataHandler = (DataHandler) txt.getDataHandler();
				} else {
					byte[] data = Base64.getDecoder().decode(txt.getText());
					dataHandler = new DataHandler(new ByteArrayDataSource(data));
				}
				// Here we write data to file permanently
				dataHandler.writeTo(fileOutputStream);

				confirmation.append("The Object was uploaded successfully to " + file.getAbsolutePath() + ". File size="
						+ file.length() + lineSeparator);
				confirmation.append("------------------------" + lineSeparator);
			}

			// confirmation += file.getAbsolutePath() + " " + file.exists() +
			// " length:" + file.length() + "\n" ;
			// Here we read object data from the data handler
		} catch (IOException e) {
			confirmation.append(e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
					// writer.close();
				} catch (IOException e) {
					confirmation.append(e.getMessage());
				}
			}
		}
		methodElement.addChild(omFactory.createOMText(methodElement, confirmation.toString()));
		return methodElement;
	}

	public String searchData(String fileName, int customerID) {

		File file = new File(repositoryPath + fileName);

		StringBuilder confirmation = new StringBuilder("Searching file: " + file.getAbsolutePath()
				+ " for customer with id=" + customerID + ": " + lineSeparator);
		if (!file.exists()) {
			confirmation.append(file.getAbsolutePath() + " does not exist!");
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
			confirmation.append("The information of customer with id " + customerID + ": ");
			for (Customer c : customerList) {
				confirmation.append(c.getCustomerInfo(customerID));
			}

			confirmation.append(lineSeparator + "-----------------" + lineSeparator);
			// confirmation += file.getAbsolutePath() + " " + file.exists() +
			// " length:" + file.length();
		} catch (IOException e) {
			confirmation.append(e.getMessage());
		} catch (ClassNotFoundException e) {
			confirmation.append(e.getMessage());
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					confirmation.append(e.getMessage());
				}
			}
		}
		return confirmation.toString();
	}
	
	public String returnImage(String fileName, int customerID) {

		File file = new File(repositoryPath + fileName);
		if (!file.exists()) {
			throw new Error(file.getAbsolutePath() + " does not exist!");
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
			for (Customer c : customerList) {
				if(c.getCustomerID() == customerID) {
					return c.getImage();
				}
			}
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
		return "";
	}
	
	public String uploadDatahandlerImage(DataHandler data, String fileName) {
        File file = new File(repositoryPath + fileName);
        FileOutputStream fileOutputStream = null;
        String confirmation = "";
        try {
            fileOutputStream = new FileOutputStream(file);
            data.writeTo(fileOutputStream);
            confirmation = file.getAbsolutePath() + " uploaded? " + file.exists() + " length:" + file.length();
        } catch (IOException e) {
            confirmation = e.getMessage();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                confirmation = e.getMessage();
            }
        }
        return confirmation;
    }
	
	public OMElement downloadFile(OMElement element) throws Exception {
        element.detach();
        element.build();
        String fileName = ((OMElement) element.getChildrenWithLocalName("file_name").next()).getText();
    
        OMFactory omFactory = OMAbstractFactory.getOMFactory();
        OMNamespace omNameSpace = omFactory.createOMNamespace(this.nameSpaceURI, "ns");
        OMElement responseElement = omFactory.createOMElement("fileDownloadResponse", omNameSpace);
        OMElement fileNameElement = omFactory.createOMElement("file_name", omNameSpace);
        fileNameElement.setText(fileName);
        responseElement.addChild(fileNameElement);
        OMElement fileContentElement = omFactory.createOMElement("file_content", omNameSpace);
    
        File downloadFile = new File(this.repositoryPath + fileName);
        if (!downloadFile.exists()) {
            String exceptionMsg = "Server info: " + downloadFile.getAbsolutePath() + " exists? "
                    + downloadFile.exists();
            fileNameElement.addAttribute("IOError", exceptionMsg, omNameSpace);
            return responseElement;
        }
        //Here we creating the Data Handler for the file with FileDataSource,
        //which implementation javax.activation.DataSource interface.
        DataHandler dataHandler = new DataHandler(new FileDataSource(repositoryPath + fileName));
        //Here we create an OMText node with the above DataHandler and set
        //optimised to true.
        OMText textData = omFactory.createOMText(dataHandler, true);
        
        fileContentElement.addChild(textData);
        responseElement.addChild(fileContentElement);
        
        return responseElement;
    }
}