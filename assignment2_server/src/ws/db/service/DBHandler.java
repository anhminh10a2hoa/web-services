package ws.db.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DBHandler {
	/*
	 *
	 * Here we initializae tools for making the database connection
	 *
	 * and reading from the database
	 */
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	// Here we declare ResultSetMetaData object to get
	// data about the result set
	ResultSetMetaData resultSetMetaData = null;

	private StringBuilder connectionErrorMessage = new StringBuilder();

	public DBHandler(String userName, String password, String db) {
		try {
			// Here we load the database driver for Oracle database
			// Class.forName("oracle.jdbc.driver.OracleDriver");
			// For mySQL database the above code would look like this:
			Class.forName("com.mysql.jdbc.Driver");
			// Here we set the JDBC URL for the Oracle database
			// String url = "jdbc:oracle:thin:@db.cc.puv.fi:1521:ora817";
			// For mySQL database the above code would look like
			// something this.

			String url;

			// Here we create a ResourceBundle object, which is initialized with
			// settings.properties file
			// which is under conf package.
			/*
			 * Here we get the equivalent of 'db_url' from the resource bundle file. If the
			 * resource is not from the bundle, MissingResourceException will be thrown and
			 * its relevant error message will be created.
			 */
			url = "jdbc:mysql://mysql.cc.puv.fi:3306/" + db;

			// Notice that here we are accessing mg_db database
			// String url = dbURL + db;
			// Here we create a connection to the database
			// connection = DriverManager.getConnection(url, userName,
			// password);
			// For mg_db mySQL database the above code would look like
			// the following:
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			connectionErrorMessage.append(e.getMessage() + System.lineSeparator());
		} catch (ClassNotFoundException e) {
			connectionErrorMessage.append(e.getMessage() + System.lineSeparator());
		} catch (MissingResourceException e) {
			connectionErrorMessage.append(e.getMessage() + System.lineSeparator());
		}
	}

	// We use this method to return possible connection error message
	public String getConnectionErrorMessage() {

		if (connectionErrorMessage.length() > 0)
			return connectionErrorMessage.toString();

		return "";

	}

	public String writeData(String table, String values) {
		String[] colValues = values.split(",");
		String query = "insert into " + table + " values( ";
		for (int i = 0; i < colValues.length; i++) {
			if (colValues[i].toString().equalsIgnoreCase("true")
					|| (colValues[i].toString().equalsIgnoreCase("false"))) {
				query += colValues[i];
			} else {
				// Here we check whether the value can be converted into an
				// integer
				// or a double number
				try {
					Integer.parseInt(colValues[i].toString());
					query += colValues[i];
				} catch (Exception e1) {
					try {
						Double.parseDouble(colValues[i].toString());
						query += colValues[i];
					} catch (Exception e2) {
						query += "'" + colValues[i] + "'";
					}
				}
			}
			if (i != (colValues.length - 1))
				query += ", ";
		}
		query += " )";
		try {
			// Herey we create the statement object for executing SQL commands.
			statement = connection.createStatement();
			int rows = statement.executeUpdate(query);
			return rows + "";
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			try {
				// Here we close all open streams
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException sqlexc) {
				return sqlexc.getMessage();
			}
		}
	}

	public String readData(String table, String condition) {
		String query = "select * from " + table;
		if (condition != null && !condition.isEmpty())
			query += " where " + condition;
		String columnHeadings = "", columnValues = "";
		try {
			// Here we create the statement object for executing SQL commands.
			statement = connection.createStatement();
			// Here we execute the SQL query and save the results to a ResultSet
			// object
			resultSet = statement.executeQuery(query);

			// Here we get the metadata of the query results
			resultSetMetaData = resultSet.getMetaData();
			// Here we calculate the number of columns
			int columns = resultSetMetaData.getColumnCount();
			// Here we print column names in table header cells
			// Pay attention that the column index starts with 1
			for (int i = 1; i <= columns; i++) {
				columnHeadings += resultSetMetaData.getColumnName(i) + "\t";
			}
			columnHeadings += System.lineSeparator();
			// for (int i = 1; i <= columns; i++)
			// columnHeadings += "-------";
			//
			// columnHeadings += "\n";
			while (resultSet.next()) {
				// Here we print the value of each column
				for (int i = 1; i <= columns; i++)
					columnValues += resultSet.getObject(i) + "\t";
				columnValues += "\n";
			}
		} catch (Exception ex) {
			return ex.getMessage();
		} finally {
			try {
				// Here we close all open streams
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException sqlexc) {
				return sqlexc.getMessage();
			}
		}
		return columnHeadings + columnValues;
	}
	
	public String updateData(String table, int customerId, String values) {
		String[] colValues = values.split(",");
		String checkUserExistQuery = "Select * from " + table + " Where customerId = " + customerId;
		String[] columnsName = {"customerId", "customerName", "shoppingAmount", "privileged", "discountPercentage"};
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(checkUserExistQuery);
			if(!resultSet.next()) {
				return "Does not exist id = " + customerId;
			}
			else {
				String query = "UPDATE " + table + " SET ";
				for (int i = 0; i < colValues.length; i++) {
					if (colValues[i].toString().equalsIgnoreCase("true")
							|| (colValues[i].toString().equalsIgnoreCase("false"))) {
						query += columnsName[i] + "=" + colValues[i] + ", ";
					} else {
						try {
							Integer.parseInt(colValues[i].toString());
							if (i != (colValues.length - 1)) {
								query += columnsName[i] + "=" + colValues[i] + ", ";
							}
							else {
								query += columnsName[i] + "=" + colValues[i];
							}

						} catch (Exception e1) {
							try {
								Double.parseDouble(colValues[i].toString());
								if (i != (colValues.length - 1)) {
									query += columnsName[i] + "=" + colValues[i] + ", ";
								}
								else {
									query += columnsName[i] + "=" + colValues[i];
								}
							} catch (Exception e2) {
								if (i != (colValues.length - 1)) {
									query += columnsName[i] + "='" + colValues[i] + "', ";
								}
								else {
									query += columnsName[i] + "='" + colValues[i] + "', ";
								}
							}
						}
					}
					
				}
				query += " Where customerId=" + customerId;
				try {
					// Herey we create the statement object for executing SQL commands.
					statement = connection.createStatement();
					int rows = statement.executeUpdate(query);
					return query;
				} catch (Exception e) {
					return e.getMessage() + query;
				} finally {
					try {
						// Here we close all open streams
						if (statement != null)
							statement.close();
						if (connection != null)
							connection.close();
					} catch (SQLException sqlexc) {
						return sqlexc.getMessage() + query;
					}
				}
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public String deleteData(String table, int customerId) {
		String checkUserExistQuery = "Select * from " + table + " Where customerId = " + customerId;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(checkUserExistQuery);
			if(!resultSet.next()) {
				return "Does not exist id = " + customerId;
			} else {
				String query = "DELETE FROM  " + table + " Where customerId=" + customerId;
				try {
					statement = connection.createStatement();
					statement.executeUpdate(query);
					return "Delete successful";
				}
				catch (Exception e1) {
					return e1.getMessage();
				}	
			}
		} catch (Exception e) {
			return e.getMessage();
		}	
	}
	
	public String searchName(String table, String customerName) {
		String checkUserExistQuery = "Select * from " + table + " Where customerName = '" + customerName + "'";
		String columnValues = "";
		String columnHeadings = "";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(checkUserExistQuery);
			if(!resultSet.next()) {
				return "Does not exist name = " + customerName;
			} else {
				statement = connection.createStatement();
				// Here we execute the SQL query and save the results to a ResultSet
				// object
				resultSet = statement.executeQuery(checkUserExistQuery);

				// Here we get the metadata of the query results
				resultSetMetaData = resultSet.getMetaData();
				// Here we calculate the number of columns
				int columns = resultSetMetaData.getColumnCount();
				// Here we print column names in table header cells
				// Pay attention that the column index starts with 1
				for (int i = 1; i <= columns; i++) {
					columnHeadings += resultSetMetaData.getColumnName(i) + "\t";
				}
				columnHeadings += System.lineSeparator();
				// for (int i = 1; i <= columns; i++)
				// columnHeadings += "-------";
				//
				// columnHeadings += "\n";
				while (resultSet.next()) {
					// Here we print the value of each column
					for (int i = 1; i <= columns; i++)
						columnValues += resultSet.getObject(i) + "\t";
					columnValues += "\n";
				}
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return columnHeadings + columnValues;
	}
	
	public String searchPrice(String table, double lowerPrice, double higherPrice) {
		String checkUserExistQuery = "Select * from " + table + " Where shoppingAmount<" + higherPrice + " and shoppingAmount> " + lowerPrice;
		String columnValues = "";
		String columnHeadings = "";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(checkUserExistQuery);
			if(!resultSet.next()) {
				return "Dont have any customer have shopping amount > " + lowerPrice + " and <" + higherPrice;
			} else {
				statement = connection.createStatement();
				// Here we execute the SQL query and save the results to a ResultSet
				// object
				resultSet = statement.executeQuery(checkUserExistQuery);

				// Here we get the metadata of the query results
				resultSetMetaData = resultSet.getMetaData();
				// Here we calculate the number of columns
				int columns = resultSetMetaData.getColumnCount();
				// Here we print column names in table header cells
				// Pay attention that the column index starts with 1
				for (int i = 1; i <= columns; i++) {
					columnHeadings += resultSetMetaData.getColumnName(i) + "\t";
				}
				columnHeadings += System.lineSeparator();
				// for (int i = 1; i <= columns; i++)
				// columnHeadings += "-------";
				//
				// columnHeadings += "\n";
				while (resultSet.next()) {
					// Here we print the value of each column
					for (int i = 1; i <= columns; i++)
						columnValues += resultSet.getObject(i) + "\t";
					columnValues += "\n";
				}
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return columnHeadings + columnValues;
	}
}