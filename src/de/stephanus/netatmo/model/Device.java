package de.stephanus.netatmo.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Device {

	@SerializedName("_id")
	private String id;
	private List<Module> modules;
	private Place place;
	@SerializedName("dashboard_data")
	private DashboardData dashboardData;
	private List<Alarm> meteo_alarms;
	private String station_name;
	private String type;
	private long wifi_status;
	private String[] data_type;
	
	private User user;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public DashboardData getDashboardData() {
		return dashboardData;
	}
	public void setDashboardData(DashboardData dashboardData) {
		this.dashboardData = dashboardData;
	}
	public List<Alarm> getMeteo_alarms() {
		return meteo_alarms;
	}
	public void setMeteo_alarms(List<Alarm> meteo_alarms) {
		this.meteo_alarms = meteo_alarms;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getWifi_status() {
		return wifi_status;
	}
	public void setWifi_status(long wifi_status) {
		this.wifi_status = wifi_status;
	}
	public String[] getData_type() {
		return data_type;
	}
	public void setData_type(String[] data_type) {
		this.data_type = data_type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
