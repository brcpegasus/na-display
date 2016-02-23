package de.stephanus.netatmo.model;

import com.google.gson.annotations.SerializedName;

public class Alarm {

	private String area;
	private long begin;
	private long end;
	private String title;
	private String type;
	private String text_field;
	private long level;
	private String url;
	private String descr;
	private String status;
	private long alarm_id;
	@SerializedName("max level")
	private long max_level;
	private long time_generated;
	private String origin;
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public long getBegin() {
		return begin;
	}
	public void setBegin(long begin) {
		this.begin = begin;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText_field() {
		return text_field;
	}
	public void setText_field(String text_field) {
		this.text_field = text_field;
	}
	public long getLevel() {
		return level;
	}
	public void setLevel(long level) {
		this.level = level;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getAlarm_id() {
		return alarm_id;
	}
	public void setAlarm_id(long alarm_id) {
		this.alarm_id = alarm_id;
	}
	public long getMax_level() {
		return max_level;
	}
	public void setMax_level(long max_level) {
		this.max_level = max_level;
	}
	public long getTime_generated() {
		return time_generated;
	}
	public void setTime_generated(long time_generated) {
		this.time_generated = time_generated;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	
}
