package com.ExXothermic.communication;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class ComunicationManagementForUI extends ComunicationManager {
	private static ComunicationManagementForUI INSTANCE;
	
	
	private ComunicationManagementForUI(){
		logger= Logger.getLogger(this.getClass().getName());
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub.
		return "ComunicationManagementForUI";
	}
	
	
	/**
	 * Create the instance 
	 * @throws UnknownHostException
	 */
	private synchronized static void createInstance() throws UnknownHostException {
        if (INSTANCE == null) { 
            INSTANCE = new ComunicationManagementForUI();
        }
    }
	/**
	 * Get the instance 
	 * @return a unique instance of ComunicationManagement
	 */
	 public static ComunicationManagementForUI getInstance() {
	        try {
				createInstance();
				return INSTANCE;
			} catch (UnknownHostException e) {
				Logger logger= Logger.getLogger(ComunicationManagementForClients.class.getName());
				logger.error("Server not started",e);
				return null;
			}
	        
	 }

}
