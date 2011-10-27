package com.bopbi.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class PointSearchDB {

	public static ResultSet getAllPoint(Connection connection)
			throws SQLException {
		Statement statement = (Statement) connection.createStatement();
		return statement.executeQuery("select * from point");
	}
}
