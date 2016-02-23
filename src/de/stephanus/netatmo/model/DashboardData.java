package de.stephanus.netatmo.model;

import com.google.gson.annotations.SerializedName;

public class DashboardData {
	
	@SerializedName("time_utc")
	private long time;
	@SerializedName("Noise")
	private int noise;
	@SerializedName("Temperature")
	private double temperature;
	@SerializedName("temp_trend")
	private String temperatureTrend;
	@SerializedName("Humidity")
	private int humidity;
	@SerializedName("Pressure")
	private double pressure;
	@SerializedName("pressure_trend")
	private String pressureTrend;
	@SerializedName("CO2")
	private int co2;
	@SerializedName("min_temp")
	private double minTemp;
	@SerializedName("max_temp")
	private double maxTemp;
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getNoise() {
		return noise;
	}
	public void setNoise(int noise) {
		this.noise = noise;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public String getTemperatureTrend() {
		return temperatureTrend;
	}
	public void setTemperatureTrend(String temperatureTrend) {
		this.temperatureTrend = temperatureTrend;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public String getPressureTrend() {
		return pressureTrend;
	}
	public void setPressureTrend(String pressureTrend) {
		this.pressureTrend = pressureTrend;
	}
	public int getCo2() {
		return co2;
	}
	public void setCo2(int co2) {
		this.co2 = co2;
	}
	public double getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}
	public double getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}
	
	
}
