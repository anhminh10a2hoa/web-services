package ws.customer.service;

import ws.db.service.DBHandler;

public class DBCustomerService {

	DBHandler dbQuery = null;

	public String writeCustomerData(String userName, String password, String db, String table, String values) {
		dbQuery = new DBHandler(userName, password, db);
		if (!dbQuery.getConnectionErrorMessage().equals(""))
			return dbQuery.getConnectionErrorMessage();
		String rowNumber = dbQuery.writeData(table, values);
		dbQuery = null;
		return rowNumber;
	}

	public String getCustomerData(String userName, String password, String db, String table, String condition) {
		dbQuery = new DBHandler(userName, password, db);
		String customerData = dbQuery.readData(table, condition);
		dbQuery = null;
		return customerData;
	}


	public String updateCustomerData(String userName, String password, String db, String table, int customerId, String values ) {
		dbQuery = new DBHandler(userName, password, db);
		if (!dbQuery.getConnectionErrorMessage().equals(""))
			return dbQuery.getConnectionErrorMessage();
		String rowNumber = dbQuery.updateData(table, customerId, values);
		dbQuery = null;
		return rowNumber;
	}
	
	public String deleteCustomerData(String userName, String password, String db, String table, int customerId) {
		dbQuery = new DBHandler(userName, password, db);
		if (!dbQuery.getConnectionErrorMessage().equals(""))
			return dbQuery.getConnectionErrorMessage();
		String rowNumber = dbQuery.deleteData(table, customerId);
		dbQuery = null;
		return rowNumber;
	}
	
	public String searchNameData(String userName, String password, String db, String table, String customerName) {
		dbQuery = new DBHandler(userName, password, db);
		if (!dbQuery.getConnectionErrorMessage().equals(""))
			return dbQuery.getConnectionErrorMessage();
		String rowNumber = dbQuery.searchName(table, customerName);
		dbQuery = null;
		return rowNumber;
	}
	
	public String searchPriceData(String userName, String password, String db, String table, double lowerPrice, double higherPrice) {
		dbQuery = new DBHandler(userName, password, db);
		if (!dbQuery.getConnectionErrorMessage().equals(""))
			return dbQuery.getConnectionErrorMessage();
		String rowNumber = dbQuery.searchPrice(table, lowerPrice, higherPrice);
		dbQuery = null;
		return rowNumber;
	}
}