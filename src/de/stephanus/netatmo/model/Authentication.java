package de.stephanus.netatmo.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Authentication {

	private static final int INACCURACY_IN_SEC = 60;
	
	private String access_token;
	private int expires_in;
	private String refresh_token;
	private Date expiryDate;
	
	public Authentication(){
	}
	
	public void updateExpiryDate() {
		Calendar greg = GregorianCalendar.getInstance();
		greg.add(Calendar.SECOND, expires_in);
		
		this.expiryDate = greg.getTime();
	}
	
	public boolean isExpired(){
		boolean result = true;
		
		Calendar greg = GregorianCalendar.getInstance();
		greg.add(Calendar.SECOND, INACCURACY_IN_SEC);
		
		if(expiryDate != null && expiryDate.after(greg.getTime())) {
			result = false;
		}
		
		return result;
	}
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	
	
}
