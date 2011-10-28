package com.bopbi.main;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.bopbi.dementor.TweetDementor;

public class TwitterMain {

	private static final String PROP_FILE = "myConfig.properties";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			File propFile = new File(new File("."), PROP_FILE);
			FileInputStream is = new FileInputStream(propFile);
			Properties prop = new Properties();
			prop.load(is);
			String proxy = prop.getProperty("proxy");
			String proxy_server = prop.getProperty("proxy_server");
			String proxy_port = prop.getProperty("proxy_port");
			String proxy_username = prop.getProperty("proxy_username");
			String proxy_password = prop.getProperty("proxy_password");
			is.close();

			DementorThread dementorThread = new DementorThread(proxy,
					proxy_server, proxy_port, proxy_username, proxy_password);
			new Thread(dementorThread).start();
			/* code to use values read from the file */
		} catch (Exception e) {
			System.out.println("Failed to read from " + PROP_FILE + " file.");
		}
	}

}

class DementorThread implements Runnable {

	String proxy;
	String proxy_server;
	String proxy_port;
	String proxy_username;
	String proxy_password;

	public DementorThread(String proxy, String proxy_server, String proxy_port,
			String proxy_username, String proxy_password) {
		this.proxy = proxy;
		this.proxy_password = proxy_password;
		this.proxy_port = proxy_port;
		this.proxy_server = proxy_server;
		this.proxy_username = proxy_username;
	}

	public void run() {
		TweetDementor tweetDementor = new TweetDementor(proxy, proxy_server,
				proxy_port, proxy_username, proxy_password);
		try {
			tweetDementor.run();

		} catch (Exception e) {
		}

	}
}
