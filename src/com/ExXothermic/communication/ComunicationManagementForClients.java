package com.ExXothermic.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Observer;

import org.apache.log4j.Logger;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;

import com.ExXothermic.message.FactoryMessage;
import com.ExXothermic.message.IMessage;


/**
 * This class manage the WebSocket Server 
 * 
 * @author Rodolfo Cordero Sancho
 * @version 1.0 
 * 
 */
public class ComunicationManagementForClients  extends ComunicationManager{
	
	
	//Singleton
	
	private static ComunicationManagementForClients INSTANCE;
	
	
	/**
	 * Create the instance 
	 * @throws UnknownHostException
	 */
	private synchronized static void createInstance() throws UnknownHostException {
        if (INSTANCE == null) { 
            INSTANCE = new ComunicationManagementForClients();
        }
    }
	/**
	 * Get the instance 
	 * @return a unique instance of ComunicationManagement
	 */
	 public static ComunicationManagementForClients getInstance() {
	        try {
				createInstance();
				return INSTANCE;
			} catch (UnknownHostException e) {
				Logger logger= Logger.getLogger(ComunicationManagementForClients.class.getName());
				logger.error("Server not started",e);
				return null;
			}
	        
	 }
	
	private ComunicationManagementForClients()
	{
		logger= Logger.getLogger(this.getClass().getName());
	
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ComunicationManagementForClients";
	}
	
}
