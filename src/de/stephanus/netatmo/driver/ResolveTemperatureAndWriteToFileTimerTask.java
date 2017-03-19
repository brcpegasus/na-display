package de.stephanus.netatmo.driver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimerTask;

import de.stephanus.netatmo.controller.Configuration;
import de.stephanus.netatmo.controller.NetAtmoIntegrator;
import de.stephanus.netatmo.model.Device;
import de.stephanus.netatmo.model.Module;

public class ResolveTemperatureAndWriteToFileTimerTask extends TimerTask {

	private String destinationFileIn;
	private String destinationFileOut;
	private String targetDeviceName;
	private String targetModuleName;

	private NumberFormat temperatureFormat;

	public ResolveTemperatureAndWriteToFileTimerTask(Properties configuration) {
		this.destinationFileOut = configuration.getProperty(Configuration.DESTINATION_OUTSIDE_FILE);
		this.destinationFileIn = configuration.getProperty(Configuration.DESTINATION_INSIDE_FILE);
		this.targetDeviceName = configuration.getProperty(Configuration.TARGET_DEVICE_NAME);
		this.targetModuleName = configuration.getProperty(Configuration.TARGET_DEVICE_OUT_MODULE_NAME);
		
		temperatureFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
		temperatureFormat.setMinimumFractionDigits(1);
		temperatureFormat.setMaximumFractionDigits(1);

		NetAtmoIntegrator.newInstance(configuration);
	}

	@Override
	public void run() {
		List<Device> devices = NetAtmoIntegrator.getInstance().getDevices();

		Device targetDevice = null;
		for (Device device : devices) {
			if (targetDeviceName.equalsIgnoreCase(device.getStation_name())) {
				targetDevice = device;
				break;
			}
		}

		if (targetDevice == null) {
			return;
		}

		Module targetModule = null;
		for (Module module : targetDevice.getModules()) {
			if (targetModuleName.equalsIgnoreCase(module.getModule_name())) {
				targetModule = module;
				break;
			}
		}

		if (targetModule == null) {
			return;
		}

		try {
			String in = temperatureFormat.format(targetDevice.getDashboardData().getTemperature());
			String out = temperatureFormat.format(targetModule.getDashboardData().getTemperature());
			in = normalizeString(in, out);
			out = normalizeString(out, in);
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(destinationFileOut, false));
			bw.write(out);
			bw.close();

			bw = new BufferedWriter(new FileWriter(destinationFileIn, false));
			bw.write(in);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String normalizeString(String first, String second) {

		int delta = first.length() - second.length();
		
		if(delta >= 0) {
			return first;
		}
		else {
			delta = delta * -1;
			char[] spaces = new char[delta];
			Arrays.fill(spaces, ' ');
			
			return new String(spaces) + first;
		}
	}

}
