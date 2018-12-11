package de.hsma.igt.sascha.transactions;

import java.sql.*;

public class SQLcommands {

	private static final String URL = "jdbc:mysql://192.168.107.45:3306/ogm_hibernate";
	private static final String USER = "root";
	private static final String PWD = "123qwe";

	// queries SQL
	private static final String SELECT = "Select * from MyCustomer";
	private static final String DELETE = "Delete from MyCustomer";
	private static final String INSERT = "insert into customer(customer, name, idflight) values (3,'alex',3)";
	
	// procedures stored
	private static final String CALL = "call getAllCustomer";

	public static void main(String[] args) {
		try {
			// 1. Get connection to database
			Connection connection = DriverManager.getConnection(URL, USER, PWD);
			// 2. create statement
			Statement statement = connection.createStatement();
			// 3. Execute SQL query select
			// ResultSet resultSet = statement.executeQuery(SELECT);
			// 4. Process the result set
//			while (resultSet.next()) {
//				System.out.println(resultSet.getString("C_NAME"));
//			}
			statement.executeUpdate(DELETE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
