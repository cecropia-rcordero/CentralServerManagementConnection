package com.ExXothermic.RestFul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;


public class Client {
	
	private Logger  logger=Logger.getLogger(this.getClass().getName());
	private String url="http://localhost:8080/MyCentralServer/gears/";
	
	
	public String register(String serial)
	{
		logger.info("Register Serial: "+serial);
		String path="register?serial="+serial;
		return CallToApi(path);
	}
	
	public String disconnect(String serial)
	{
		logger.info("Disconnect Serial: "+serial);
		String path="close?serial="+serial;
		return CallToApi(path);
	}
	
	private  String CallToApi(String path)
	{
		String output="";
		 try {
			 
			 	logger.info("Call to RestFul service ");
			 	
				URL url = new URL(this.url+path);
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
		 
				if (conn.getResponseCode() != 200) {
					logger.error("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}
		 
				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
		 
				

				while ((output = br.readLine()) != null) {
					logger.info(output);
				}
		 
				conn.disconnect();
		 
			  } catch (MalformedURLException e) {
		 
				logger.error(e);
		 
			  } catch (IOException e) {
		 
				  logger.error(e);
		 
			  }	
		 return output; 
	}
}