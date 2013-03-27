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
	public void Register(String serial)
	{
		 try {
			 
			 	logger.info("Call to RestFul service ");
			 	logger.info("Serial: "+serial);
				URL url = new URL("http://localhost:8080/MyCentralServer/gears/register?serial="+serial);
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
		 
				if (conn.getResponseCode() != 200) {
					logger.error("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}
		 
				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
		 
				String output;

				while ((output = br.readLine()) != null) {
					logger.info(output);
				}
		 
				conn.disconnect();
		 
			  } catch (MalformedURLException e) {
		 
				logger.error(e);
		 
			  } catch (IOException e) {
		 
				  logger.error(e);
		 
			  }	
	}
}