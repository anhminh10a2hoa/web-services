package restful.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeDao {

	private static String url, username, password;

	private static Connection con = null;
	public static void setDBFileName(String url, String username, String password) {
		EmployeeDao.url = url;
		EmployeeDao.username = username;
		EmployeeDao.password = password;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			EmployeeDao.con = DriverManager.getConnection(url, username, password);
		} catch(Exception error) {
			System.out.println(error);
		}

	}

	List<Employee> employeeList = new ArrayList<Employee>();

	
	public EmployeeDao() {
	}

	public List<Employee> getAllEmployees() {
		List<Employee> employeeList = new ArrayList<Employee>();
		String sql = "select * from employee";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Employee e = new Employee();
				
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setPhoneNumber(rs.getString(3));
				e.setPurchase(rs.getDouble(4));
				
				employeeList.add(e);
			}
		} catch(Exception error) {
			System.out.println(error);
		}
		return employeeList;
	}
	
	public List<Employee> getAllEmployeesByName(String name) {
		List<Employee> employeeList = new ArrayList<Employee>();
		String sql = "select * from employee where name='" + name + "'";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Employee e = new Employee();
				
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setPhoneNumber(rs.getString(3));
				e.setPurchase(rs.getDouble(4));
				
				employeeList.add(e);
			}
		} catch(Exception error) {
			System.out.println(error);
		}
		return employeeList;
	}
	
	public List<Employee> getAllEmployeesByPurchase(double purchase, boolean higherOrLower) {
		List<Employee> employeeList = new ArrayList<Employee>();
		String sql = "select * from employee where purchase " + (higherOrLower ? "> " : "< ") + purchase;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Employee e = new Employee();
				
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setPhoneNumber(rs.getString(3));
				e.setPurchase(rs.getDouble(4));
				
				employeeList.add(e);
			}
		} catch(Exception error) {
			System.out.println(error);
		}
		return employeeList;
	}

	public Employee getEmployee(int id) {
		String sql = "select * from employee where id=" + id;
		Employee e = new Employee();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setPhoneNumber(rs.getString(3));
				e.setPurchase(rs.getDouble(4));
			}
		} catch(Exception error) {
			System.out.println(error);
		}
		return e;
	}

	public String addEmployee(Employee employee) {
		String sql = "insert into employee values (?,?,?,?)";
		String res = "";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, employee.getId());
			st.setString(2, employee.getName());
			st.setString(3, employee.getPhoneNumber());
			st.setDouble(4, employee.getPurchase());
			st.executeUpdate();
			res += "Add sucessful";
		} catch(Exception error) {
			System.out.println(error);
		}
		return res;
	}
	
	public String updateEmployee(Employee employee) {
		String sql = "update employee set name=?, phoneNumber=?, purchase=? where id=?";
		String res = "";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1, employee.getName());
			st.setString(2, employee.getPhoneNumber());
			st.setDouble(3, employee.getPurchase());
			st.setInt(4, employee.getId());
			st.executeUpdate();
			res += "Update sucessful";
		} catch(Exception error) {
			System.out.println(error);
		}
		return res;
	}
	
	public String deleteEmployee(int id) {
		String sql = "delete from employee where id=?";
		Employee e = new Employee();
		String res = "";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
			res += "Delete sucessful";
		} catch(Exception error) {
			System.out.println(error);
		}
		return res;
	}
}