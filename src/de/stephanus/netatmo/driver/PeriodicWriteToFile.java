package de.stephanus.netatmo.driver;

import java.util.Properties;
import java.util.Timer;

import de.stephanus.netatmo.controller.Configuration;

public class PeriodicWriteToFile {

	private static final int DELAY_BETWEEN_EXECUTIONS = 10 * 60 * 1000;
	
	public static void main(String[] args) {
		Properties configuration = Configuration.getConfiguration();
		
		Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new ResolveTemperatureAndWriteToFileTimerTask(configuration), 0, DELAY_BETWEEN_EXECUTIONS);
	}
	
}
