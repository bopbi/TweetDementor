package com.bopbi.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import twitter4j.Tweet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class UserTweetDB {

	public static void insert(Connection connection, Tweet tweet)
			throws SQLException {

		// check user if exist or not
		if (!ifExist(connection, tweet)) {
			System.out.println("insert");
			
			// insert
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement("insert into user (user_id, user_name, text, created_at) values ( ?, ?, ?, ?) ");
			

			// Parameters start with 1
			preparedStatement.setLong(1, tweet.getFromUserId());
			
			preparedStatement.setString(2, tweet.getFromUser());
			
			preparedStatement.setString(3, tweet.getText());

			preparedStatement.setTimestamp(4, new Timestamp(tweet.getCreatedAt().getTime()));
			
			preparedStatement.executeUpdate();
			
		} else {
			System.out.println("update");
			
			// update if exist
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement("update user set user_name = ?, text = ?, updated_at = ? where user_id = ?");
			// Parameters start with 1

			preparedStatement.setString(1, tweet.getFromUser());
			preparedStatement.setString(2, tweet.getText());
			preparedStatement.setTimestamp(3, new Timestamp(tweet.getCreatedAt().getTime()));
			preparedStatement.setLong(4, tweet.getFromUserId());
			preparedStatement.executeUpdate();
			
		}

	}

	static boolean ifExist(Connection connection, Tweet tweet) {
		int size = 0;

		// Result set get the result of the SQL query
		ResultSet resultSet = null;
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement("select COUNT(*) from user where user_id = ?");
			preparedStatement.setLong(1, tweet.getFromUserId());
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (resultSet.next()) {
				size = resultSet.getInt(1);
				
				if(size > 0) {
					return true;
				}
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	
	public static ResultSet getAll(Connection connection) throws SQLException {
		Statement statement = (Statement) connection.createStatement();
		return statement.executeQuery("select * from user");
	}

}
