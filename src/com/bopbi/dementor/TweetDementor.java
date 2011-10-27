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

	public void test() throws InterruptedException {
		try {

			try {
				connection = (Connection) DBUtil.Connect();

				ResultSet resultSet = PointSearchDB.getAllPoint(connection);

				while (resultSet.next()) {

					double lat = resultSet.getDouble("lat");
					double lng = resultSet.getDouble("lng");
					int radius = resultSet.getInt("radius");
					String unit = resultSet.getString("unit");

					searchTweet(lat, lng, radius, unit);
					
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

	void searchTweet(double lat, double lng, int radius, String unit)
			throws TwitterException {

		// if proxy is ITB
		Twitter twitter = setITBProxy();

		// if not using Proxy
		// Twitter twitter = new TwitterFactory().getInstance();
		GeoLocation geoLocation = new GeoLocation(lat, lng);

		Query query = new Query();
		query.setGeoCode(geoLocation, radius, unit);
		query.rpp(100);

		QueryResult result = twitter.search(query);
		List<Tweet> tweets = result.getTweets();
		for (Tweet tweet : tweets) {
			System.out.println(tweet.getFromUserId() + " -- @"
					+ tweet.getFromUser() + " - "
					+ tweet.getCreatedAt() + " "
					+ tweet.getText());

			try {
				UserTweetDB.insert(connection, tweet);
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
		cb.setHttpProxyHost("cache.itb.ac.id").setHttpProxyPort(8080)
				.setHttpProxyUser("prabowo.bobby")
				.setHttpProxyPassword("memasukilagi");
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}

}
