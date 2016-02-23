package de.stephanus.netatmo.model;

import com.google.gson.annotations.SerializedName;

public class Module {

	@SerializedName("_id")
	private String id;
	@SerializedName("dashboard_data")
	private DashboardData dashboardData;
	private String module_name;
	private String type;
	private long rf_status;
	private String battery_vp;
	private String[] data_type;
	private int battery_percent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DashboardData getDashboardData() {
		return dashboardData;
	}
	public void setDashboardData(DashboardData dashboardData) {
		this.dashboardData = dashboardData;
	}
	public String getModule_name() {
		return module_name;
	}
	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getRf_status() {
		return rf_status;
	}
	public void setRf_status(long rf_status) {
		this.rf_status = rf_status;
	}
	public String getBattery_vp() {
		return battery_vp;
	}
	public void setBattery_vp(String battery_vp) {
		this.battery_vp = battery_vp;
	}
	public String[] getData_type() {
		return data_type;
	}
	public void setData_type(String[] data_type) {
		this.data_type = data_type;
	}
	public int getBattery_percent() {
		return battery_percent;
	}
	public void setBattery_percent(int battery_percent) {
		this.battery_percent = battery_percent;
	}

}
