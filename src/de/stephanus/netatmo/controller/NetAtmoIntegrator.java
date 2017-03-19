package de.stephanus.netatmo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

	private static final String URL_READ_DATA = "https://api.netatmo.com/api/getstationsdata?access_token=";
	private static final String URL_OAUTH_TOKEN_ENDPOINT = "https://api.netatmo.net/oauth2/token";
	
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	private static NetAtmoIntegrator instance;

	private final Properties configuration;
	
	private CloseableHttpClient httpClient = null;
	private Authentication authentication;
	
	private NetAtmoIntegrator(Properties configuration){
		httpClient = HttpClientBuilder.create().build();
		
		this.configuration = configuration; 
	}
	
	
	public static void main(String[] args) {
		NetAtmoIntegrator integrator = new NetAtmoIntegrator(new Properties());
		List<Device> devices = integrator.getDevices();
		
		for (Device device : devices) {
			System.out.println(device.getStation_name());
		}
	}
	

	public static NetAtmoIntegrator getInstance()  {
		if(instance == null) {
			throw new RuntimeException("No instance created yet!");
		}
		return instance;
	}
	
	public static NetAtmoIntegrator newInstance(Properties configuration)  {
		instance = new NetAtmoIntegrator(configuration);
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
			deviceRequest = new HttpPost(URL_READ_DATA + URLEncoder.encode(accessToken, DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return result;
		}
        
        HttpResponse response;
		try {
			response = httpClient.execute(deviceRequest);
			HttpEntity entity = response.getEntity();

			if(response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(entity, DEFAULT_ENCODING);
				
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
        HttpPost authorizeRequest = new HttpPost(URL_OAUTH_TOKEN_ENDPOINT);
        
        HttpResponse response;
		try {
	        List <NameValuePair> credentials = new ArrayList <NameValuePair>();
	        credentials.add(new BasicNameValuePair("grant_type", configuration.getProperty(Configuration.AUTH_GRANT_TYPE)));
	        credentials.add(new BasicNameValuePair("client_id", configuration.getProperty(Configuration.AUTH_CLIENT_ID)));
	        credentials.add(new BasicNameValuePair("client_secret", configuration.getProperty(Configuration.AUTH_CLIENT_SECRET)));
	        credentials.add(new BasicNameValuePair("username", configuration.getProperty(Configuration.AUTH_USER_ID)));
	        credentials.add(new BasicNameValuePair("password", configuration.getProperty(Configuration.AUTH_USER_PASSWORD)));
	        credentials.add(new BasicNameValuePair("scope", configuration.getProperty(Configuration.AUTH_SCOPE)));
	        
	        HttpEntity authorizationBody = new UrlEncodedFormEntity(credentials);
	        authorizeRequest.setEntity(authorizationBody);

			response = httpClient.execute(authorizeRequest);
			HttpEntity entity = response.getEntity();
			
			if(response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(entity, DEFAULT_ENCODING);
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
		
		HttpPost authorizeRequest = new HttpPost(URL_OAUTH_TOKEN_ENDPOINT);
        
        HttpResponse response;
		try {
	        List <NameValuePair> credentials = new ArrayList <NameValuePair>();
	        credentials.add(new BasicNameValuePair("grant_type", "refresh_token"));
	        credentials.add(new BasicNameValuePair("refresh_token", authentication.getRefresh_token()));
	        credentials.add(new BasicNameValuePair("client_id", configuration.getProperty(Configuration.AUTH_CLIENT_ID)));
	        credentials.add(new BasicNameValuePair("client_secret", configuration.getProperty(Configuration.AUTH_CLIENT_SECRET)));
	        
	        HttpEntity authorizationBody = new UrlEncodedFormEntity(credentials);
	        authorizeRequest.setEntity(authorizationBody);

			response = httpClient.execute(authorizeRequest);
			HttpEntity entity = response.getEntity();
			
			if(response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(entity, DEFAULT_ENCODING);
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
