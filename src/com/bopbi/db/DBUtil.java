package com.bopbi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	public static Connection Connect() throws ClassNotFoundException,
			SQLException {
		Class.forName("com.mysql.jdbc.Driver");

		return DriverManager.getConnection("jdbc:mysql://localhost/idtweet?"
				+ "user=root&password=");
	}
	
	public static void Close(Connection connection) {
		try {

			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {

		}
	}
}
