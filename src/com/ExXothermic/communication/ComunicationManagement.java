package com.ExXothermic.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Observer;

import org.apache.log4j.Logger;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;

import com.ExXothermic.message.FactoryMessage;
import com.ExXothermic.message.IMessage;


public class ComunicationManagement {
	
	private Logger  logger;
	private ComunicationServer server;
	//Singleton
	
	private static ComunicationManagement INSTANCE;
	
	private synchronized static void createInstance() throws UnknownHostException {
        if (INSTANCE == null) { 
            INSTANCE = new ComunicationManagement();
        }
    }
	
	 public static ComunicationManagement getInstance() {
	        try {
				createInstance();
				return INSTANCE;
			} catch (UnknownHostException e) {
				Logger logger= Logger.getLogger(ComunicationManagement.class.getName());
				logger.error("Server not started",e);
				return null;
			}
	        
	 }
	
	private ComunicationServer getServer()
	{
		return server;
	}
	
	private void setServer(ComunicationServer server)
	{
		this.server=server;
	}
	

	
	private ComunicationManagement() throws UnknownHostException
	{
		logger= Logger.getLogger(this.getClass().getName());
	
	}
	
	public synchronized void addObserver(Observer o)
	{
		this.getServer().addObserver(o);
	}
	
	public boolean startServer()
	{
		boolean result=true;
		try
		{
			
			this.getServer().start();
			logger.info( "Server started on port: " + this.getServer().getPort() + " on " +Calendar.getInstance().getTime().toString());
			
		}
		catch ( Exception ex)
		{
			logger.error("Server not started",ex);
			result=false;
		}
		return result;
	}
	
	public boolean stopServer() 
	{
		boolean result=true;
		try
		{
			this.getServer().stop();
			logger.info( "Server started stop");
		}
		catch(IOException ex)
		{
			logger.error("Problem stopping server IO Problem",ex);
			result=false;
		}
		catch(InterruptedException ex)
		{
			logger.error("Problem stopping server InterruptedException",ex);
			result=false;
		}
		
		return result;
	}
	
	
	
	public void startProcess(Integer portNumber,Observer... o) throws IOException {
		
		WebSocketImpl.DEBUG = false;        

		this.setServer(new ComunicationServer( portNumber ));
        getServer().setMessageWhenOpenConnection(FactoryMessage.getInstance().GetMyBoxCert());
        logger.info("Port:"+Integer.toString(portNumber));
        for(int i=0;i <o.length;i++)
        {
        	logger.info("Add observer "+o[i].toString());
        	this.addObserver(o[i]);
        }
        Thread thread =new Thread(getServer());
        thread.run();
	}
	
	public void sendMessage(WebSocket conn, IMessage message)
	{
		this.getServer().sendMessage(message.getMessage(conn), conn);
		
	}
	
	
}
