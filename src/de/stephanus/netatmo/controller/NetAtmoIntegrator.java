package de.stephanus.netatmo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import de.stephanus.netatmo.model.Authentication;
import de.stephanus.netatmo.model.Device;
import de.stephanus.netatmo.model.Response;

public class NetAtmoIntegrator {

	private static NetAtmoIntegrator instance;
	
	private CloseableHttpClient httpClient = null;
	private Authentication authentication;
	
	private NetAtmoIntegrator(){
		httpClient = HttpClientBuilder.create().build();
	}
	
	public static void main(String[] args) {
		NetAtmoIntegrator integrator = new NetAtmoIntegrator();
		List<Device> devices = integrator.getDevices();
		
		for (Device device : devices) {
			System.out.println(device.getStation_name());
		}
	}
	

	public static NetAtmoIntegrator getInstance()  {
		if(instance == null) {
			instance = new NetAtmoIntegrator();
		}
		return instance;
	}
	
	public List<Device> getDevices() {
		ensureAuthentication();
		return getDevices(authentication.getAccess_token());
	}
	
	private List<Device> getDevices(String accessToken){
        List<Device> result = null;
		
		HttpPost deviceRequest = null;
		try {
			deviceRequest = new HttpPost("https://api.netatmo.com/api/getstationsdata?access_token=" + URLEncoder.encode(accessToken, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return result;
		}
        
        HttpResponse response;
		try {
			response = httpClient.execute(deviceRequest);
			HttpEntity entity = response.getEntity();

			if(response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(entity, "UTF-8");
				
				Gson gson = new Gson();
				Response resp = gson.fromJson(content, Response.class);
				
				result = resp.getBody().getDevices();
			}
			
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private void ensureAuthentication(){
		if(authentication == null) {
			authenticate();
		}
		
		if(authentication.isExpired()) {
			refreshAuthentication();
		}
	}
	
	private void authenticate() {
        HttpPost authorizeRequest = new HttpPost("https://api.netatmo.net/oauth2/token");
        
        HttpResponse response;
		try {
	        List <NameValuePair> credentials = new ArrayList <NameValuePair>();
	        credentials.add(new BasicNameValuePair("grant_type", "password"));
	        credentials.add(new BasicNameValuePair("client_id", ""));
	        credentials.add(new BasicNameValuePair("client_secret", ""));
	        credentials.add(new BasicNameValuePair("username", ""));
	        credentials.add(new BasicNameValuePair("password", ""));
	        credentials.add(new BasicNameValuePair("scope", "read_station"));
	        
	        HttpEntity authorizationBody = new UrlEncodedFormEntity(credentials);
	        authorizeRequest.setEntity(authorizationBody);

			response = httpClient.execute(authorizeRequest);
			HttpEntity entity = response.getEntity();
			
			if(response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(entity, "UTF-8");
				authentication = new Gson().fromJson(content, Authentication.class);
				authentication.updateExpiryDate();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void refreshAuthentication() {
        if(authentication == null) {
        	authenticate();
        	return;
        }
		
		HttpPost authorizeRequest = new HttpPost("https://api.netatmo.net/oauth2/token");
        
        HttpResponse response;
		try {
	        List <NameValuePair> credentials = new ArrayList <NameValuePair>();
	        credentials.add(new BasicNameValuePair("grant_type", "refresh_token"));
	        credentials.add(new BasicNameValuePair("refresh_token", authentication.getRefresh_token()));
	        credentials.add(new BasicNameValuePair("client_id", ""));
	        credentials.add(new BasicNameValuePair("client_secret", ""));
	        
	        HttpEntity authorizationBody = new UrlEncodedFormEntity(credentials);
	        authorizeRequest.setEntity(authorizationBody);

			response = httpClient.execute(authorizeRequest);
			HttpEntity entity = response.getEntity();
			
			if(response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(entity, "UTF-8");
				authentication = new Gson().fromJson(content, Authentication.class);
				authentication.updateExpiryDate();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
