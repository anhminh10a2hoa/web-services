package soap.file.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import data.Customer;
import data.Product;

import java.util.ArrayList;

public class FileUploadDownloadService {
	private String url = "jdbc:mysql://mysql.cc.puv.fi:3306/e1800956_assignment8_ws";
	private String username = "e1800956";
	private String password = "xnmzsakPTzEr"; 
	private Connection con = null;
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String postOrder(byte[] data) throws ClassNotFoundException, IOException {
		connect();
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    Customer customer = (Customer) is.readObject();
	    
	    String sql = "insert into orders values (?,?,?,?,?)";
		String res = "";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1, customer.getCustomerName());
			st.setString(2, customer.getAddress());
			st.setString(3, customer.getPhoneNumber());
			st.setString(4, customer.getOrder());
			st.setDouble(5, customer.getTotal());
			st.executeUpdate();
			res += "Insert sucessful";
		} catch(Exception error) {
			res += error;
		}
		return res;
	}
	
	public String getOrderByName(String name) throws ClassNotFoundException, IOException {
		connect();
		String sql = "select * from orders where customerName='" + name + "'";
		Customer c = new Customer();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				c.setCustomerName(rs.getString(1));
				c.setAddress(rs.getString(2));
				c.setPhoneNumber(rs.getString(3));
				c.setOrder(rs.getString(4));
				c.setTotal(rs.getDouble(5));
			}
		} catch(Exception error) {
			System.out.println(error);
		}
		return c.toString();
	}
	
	public String getAllOrders() throws ClassNotFoundException, IOException {
		connect();
		List<Customer> customerList = new ArrayList<Customer>();
		String sql = "select * from orders";
		String res = "";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Customer c = new Customer();
				
				c.setCustomerName(rs.getString(1));
				c.setAddress(rs.getString(2));
				c.setPhoneNumber(rs.getString(3));
				c.setOrder(rs.getString(4));
				c.setTotal(rs.getDouble(5));
				
				customerList.add(c);
			}
		} catch(Exception error) {
			System.out.println(error);
		}
		for(Customer c: customerList) {
			res += c.toString();
		}
		return res;
	}
	
	public String getListProduct() throws ClassNotFoundException, IOException {
		connect();
		String sql = "select * from products";
		String res = "";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Product p = new Product();
				res += rs.getInt(1) + " " + rs.getString(2) + " " + rs.getDouble(3) + "€ \n";
			}
		} catch(Exception error) {
			System.out.println(error);
		}
		return res;
	}
	
	public String getProductPrice(String id) throws ClassNotFoundException, IOException {
		connect();
		int productId = Integer.parseInt(id);
		String sql = "select * from products where id = " + productId;
		String res = "";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				res = rs.getDouble(3) + "";
			}
		} catch(Exception error) {
			System.out.println(error);
		}
		return res;
	}
	
	public String getProductName(String id) throws ClassNotFoundException, IOException {
		connect();
		int productId = Integer.parseInt(id);
		String sql = "select * from products where id = " + productId;
		String res = "";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				res = rs.getString(2);
			}
		} catch(Exception error) {
			System.out.println(error);
		}
		return res;
	}
}