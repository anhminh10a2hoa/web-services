package service.soap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.xml.stream.XMLStreamException;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

public class Assignment4 {

	private static String destinationDir = "";

	private String init() {

		String result = "ok";

		try {
			ResourceBundle bundle = ResourceBundle.getBundle("conf.settings", Locale.ROOT);
			Assignment4.destinationDir = bundle.getString("destination_dir");

			File file = new File(Assignment4.destinationDir);

			if (file.exists())
				return result;

			new File(Assignment4.destinationDir).mkdirs();

		} catch (Exception e) {
			result += "Error with destination dir:" + System.lineSeparator() + e.getMessage();
		}

		return result;
	}

	private static String getFileSizeKiloBytes(File file) {
		return (double) file.length() / 1024 + "  kb";
	}

	public OMElement getFileList(OMElement element) {

		String dirInfo = init();

		// In the following we build a response.
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.file/xsd", "fs");
		OMElement methodElement = omFactory.createOMElement("getFileListResponse", omNameSpace);
		OMElement responseElement = omFactory.createOMElement("getFileList", omNameSpace);

		if (!dirInfo.equalsIgnoreCase("ok")) {
			responseElement.setText(dirInfo);
			methodElement.addChild(responseElement);

			return methodElement;
		}

		try {
			String date = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(new Date());
			String files = "";

			File file = new File(Assignment4.destinationDir);
			String[] fileList = file.list();

			for (String f : fileList)
				files += f + "\n";

			responseElement.setText("List of available files under " + file.getAbsolutePath() + ":" + "\n" + files);

		} catch (Exception e) {
			responseElement.setText(e.getMessage());
		}

		methodElement.addChild(responseElement);
		return methodElement;
	}

	public OMElement writeToFile(OMElement element) throws XMLStreamException {
		String dirInfo = init();
		String file_name_element_name = "file_name";
		String file_content_element_name = "file_content";

		// Here we build the xml tree using AXIOM API for the request
		// element
		element.build();
		// Here we detach the node from its parent container
		element.detach();

		String fileName = ((OMElement) element.getChildrenWithLocalName(file_name_element_name).next()).getText();
		String fileContent = ((OMElement) element.getChildrenWithLocalName(file_content_element_name).next()).getText();

		// In the following we build a response.
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.file/xsd", "fs");
		OMElement methodElement = omFactory.createOMElement("writeToFileResponse", omNameSpace);
		OMElement responseElement = omFactory.createOMElement("writeToFile", omNameSpace);

		methodElement.addChild(responseElement);

		if (!dirInfo.equalsIgnoreCase("ok")) {
			responseElement.setText(dirInfo);

			return methodElement;
		}

		try {
			File file = new File(Assignment4.destinationDir + fileName);

			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(fileContent);
			writer.close();

			responseElement.setText("Size of this file: " + file.getAbsolutePath() + " is: " + getFileSizeKiloBytes(file));

		} catch (IOException e) {
			responseElement.setText(e.getMessage());
		}

		methodElement.addChild(responseElement);
		return methodElement;
	}

	public OMElement findText(OMElement element) throws XMLStreamException {
		String dirInfo = init();
		String file_name_element_name = "file_name";
		String search_word_element_name = "search_word";

		// Here we build the xml tree using AXIOM API for the request
		// element
		element.build();
		// Here we detach the node from its parent container
		element.detach();

		String fileName = ((OMElement) element.getChildrenWithLocalName(file_name_element_name).next()).getText();
		String searchWord = ((OMElement) element.getChildrenWithLocalName(search_word_element_name).next()).getText();

		// In the following we build a response.
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.file/xsd", "fs");
		OMElement methodElement = omFactory.createOMElement("findTextResponse", omNameSpace);
		OMElement responseElement = omFactory.createOMElement("findText", omNameSpace);
		
		if (!dirInfo.equalsIgnoreCase("ok")) {
			responseElement.setText(dirInfo);

			return methodElement;
		}

		try {
			File file = new File(Assignment4.destinationDir + fileName);

			String[] words = null;
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s;
			int count = 0;
			while ((s = br.readLine()) != null) {
				words = s.split(" ");
				for (String word : words) {
					if (word.equals(searchWord)) {
						count++;
					}
				}
			}
			if (count != 0) {
				responseElement.setText("The word " + searchWord + " appeared :" + count + " times");
			} else {
				responseElement.setText("The word " + searchWord + " not found!!");
			}
		} catch (IOException e) {
			responseElement.setText(e.getMessage());
		}
		methodElement.addChild(responseElement);
		return methodElement;
	}
}