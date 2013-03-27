package com.ExXothermic.message;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;  

import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;

import com.ExXothermic.communication.Response;
 
/* this is Event Handler */

public abstract class ResponseMessageHandlerAbstract implements Observer  {
	
	Logger logger=Logger.getLogger(this.getClass().getName());
	private String resp;
	@Override
	public void update(Observable o, Object arg) {	
		try
		{
			if (arg instanceof Response) {
				Response response=(Response)arg;
				logger.info(response.getTypeRespose().toString()+" Received  "+response.getMessage() + " From: "+ response.getConn().toString());
				Operation(response.getConn(),response.getMessage(),response.getTypeRespose());
	        }
		}
		catch(Exception ex)
		{
			logger.error("Error with the observer ",ex);
		}
	}
	
	
	public abstract void Operation(WebSocket conn, String message,Response.TypeResponse type );
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
	
	
	
	


}
