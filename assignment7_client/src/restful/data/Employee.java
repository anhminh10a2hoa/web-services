package restful.data;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String phoneNumber;
	private double purchase;

	public Employee() {

		id = 0;
		name = "not_known";
		phoneNumber = "not_known";
		purchase = 0.00;

	}

	public Employee(int id, String name, String phoneNumber, double purchase) {
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.purchase = purchase;
	}

	public int getId() {
		return id;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@XmlElement
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public double getPurchase() {
		return purchase;
	}

	@XmlElement
	public void setPurchase(double purchase) {
		this.purchase = purchase;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {

			return false;

		} else if (!(object instanceof Employee)) {

			return false;

		} else {

			Employee employee = (Employee) object;

			if (id == employee.getId() && name.equals(employee.getName()) && phoneNumber.equals(employee.getPhoneNumber()) && (purchase == employee.getPurchase())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return id + " " + name + " " + phoneNumber + " " + purchase;
	}

}