package com.bopbi.dementor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.bopbi.db.DBUtil;
import com.bopbi.db.PointSearchDB;
import com.bopbi.db.UserTweetDB;
import com.mysql.jdbc.Connection;

public class TweetDementor {

	private Connection connection;
	private String proxy;
	private String proxy_server;
	private String proxy_port;
	private String proxy_username;
	private String proxy_password;

	public TweetDementor(String proxy, String proxy_server, String proxy_port,
			String proxy_username, String proxy_password) {
		this.proxy = proxy;
		this.proxy_password = proxy_password;
		this.proxy_port = proxy_port;
		this.proxy_server = proxy_server;
		this.proxy_username = proxy_username;
	}

	public void run() throws InterruptedException {
		try {

			try {
				connection = (Connection) DBUtil.Connect();

				ResultSet resultSet = PointSearchDB.getAllPoint(connection);

				while (resultSet.next()) {

					double lat = resultSet.getDouble("lat");
					double lng = resultSet.getDouble("lng");
					int radius = resultSet.getInt("radius");
					String unit = resultSet.getString("unit");
					int point_id = resultSet.getInt("id");

					searchTweet(lat, lng, radius, unit, point_id);

					Thread.sleep(25000);
				}

			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}

		try {
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	void searchTweet(double lat, double lng, int radius, String unit, int point_id)
			throws TwitterException {
		Twitter twitter;
		// if proxy is ITB
		if (proxy.equals("1")) {
			twitter = setITBProxy();
		} else {
			twitter = new TwitterFactory().getInstance();
		}
		

		GeoLocation geoLocation = new GeoLocation(lat, lng);

		Query query = new Query();
		query.setGeoCode(geoLocation, radius, unit);
		query.rpp(100);

		QueryResult result = twitter.search(query);
		List<Tweet> tweets = result.getTweets();
		for (Tweet tweet : tweets) {
			System.out.println(tweet.getFromUserId() + " -- @"
					+ tweet.getFromUser() + " - " + tweet.getCreatedAt() + " "
					+ tweet.getText());

			try {
				UserTweetDB.insert(connection, tweet, point_id);
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

	}

	/*
	 * ITB Proxy Configuration
	 */
	Twitter setITBProxy() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setHttpProxyHost(proxy_server).setHttpProxyPort(Integer.parseInt(proxy_port))
				.setHttpProxyUser(proxy_username)
				.setHttpProxyPassword(proxy_password);
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}

}
