package de.stephanus.netatmo.driver;

import java.util.Properties;

import de.stephanus.netatmo.controller.Configuration;

public class SingleWriteToFile {

	public static void main(String[] args) {
		Properties configuration = Configuration.getConfiguration();
		
		new ResolveTemperatureAndWriteToFileTimerTask(configuration).run();
	}

}
