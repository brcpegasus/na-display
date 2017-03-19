package de.stephanus.netatmo.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	public static final String TARGET_DEVICE_NAME = "target.device.name";
	public static final String TARGET_DEVICE_OUT_MODULE_NAME = "target.device.out.module.name";
	public static final String DESTINATION_INSIDE_FILE = "destination.inside.file";
	public static final String DESTINATION_OUTSIDE_FILE = "destination.outside.file";
	public static final String AUTH_CLIENT_ID = "auth.client.id";
	public static final String AUTH_CLIENT_SECRET = "auth.client.secret";
	public static final String AUTH_USER_ID = "auth.user.id";
	public static final String AUTH_USER_PASSWORD = "auth.user.password";
	public static final String AUTH_SCOPE = "auth.scope";
	public static final String AUTH_GRANT_TYPE = "auth.grant.type";
	
	public static final String CONFIG_DEFAULT_LOCATION = "na-display-config.cfg";
	
	public static Properties getConfiguration() {
		Properties configuration = new Properties();
		try {
			configuration.load(new FileReader("na-display-config.cfg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return configuration;
	}
}
