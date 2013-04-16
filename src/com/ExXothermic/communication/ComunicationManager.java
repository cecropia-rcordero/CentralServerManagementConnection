package com.ExXothermic.communication;


import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;

import com.ExXothermic.message.IMessage;

public abstract class ComunicationManager  implements Runnable  {
	protected Logger  logger;
	private ComunicationServer server;
	
	
	/**
	 * Return the instance of server
	 * 
	 * @return
	 */
	public ComunicationServer getServer()
	{
		return server;
	}
	

	/**
	 * Set the webSocket Server
	 * 
	 * @param server
	 */
	protected void setServer(ComunicationServer server)
	{
		this.server=server;
	}
	/**
	 * 
	 * 
	 * @param o Observer for the events onMessage, onClose and open connection
	 */
	protected synchronized void addObserver(Observer o)
	{
		this.getServer().addObserver(o);
	}
	
	/**
	 * Start the server
	 * @return true if the server started fine and false if it failed
	 */

	public boolean startServer()
	{
		boolean result=true;
		try
		{
			this.getServer().start();
			logger.info( "Server "+this.getName()+"  started on port: " + this.getServer().getPort() + " on " +Calendar.getInstance().getTime().toString());	
		}
		catch ( Exception ex)
		{
			logger.error("Server "+this.getName()+"  not started",ex);
			result=false;
		}
		return result;
	}
	/**
	 * Stop the server
	 * @return False or True 
	 */
	public boolean stopServer() 
	{
		boolean result=true;
		try
		{
			this.getServer().stop();
			logger.info( "Server "+this.getName()+"  started  stop");
		}
		catch(IOException ex)
		{
			logger.error("Problem stopping server "+this.getName()+"  IO Problem",ex);
			result=false;
		}
		catch(InterruptedException ex)
		{
			logger.error("Problem stopping server "+this.getName()+"  InterruptedException",ex);
			result=false;
		}
		
		return result;
	}
	
	/**
	 * Send a message to a connetion
	 * @param conn WebSocketConnection
	 * @param message Message to send
	 */
	public void sendMessage(WebSocket conn, IMessage message)
	{
		this.getServer().sendMessage(message.getMessage(conn), conn);
		
	}
	
	/**
	 * 
	 * @param portNumber number of port
	 * @param firstMessage the message when one client is connecting
	 * @param o Observer
	 * @throws IOException
	 */
	public void prepareProcess(Integer portNumber,IMessage firstMessage,Observer... o) throws IOException {
		
	    

		this.setServer(new ComunicationServer( portNumber ));
		
        getServer().setMessageWhenOpenConnection(firstMessage);
        logger.info("Port:"+Integer.toString(portNumber));
        for(int i=0;i <o.length;i++)
        {
        	logger.info("Add observer: "+this.getName()+"  "+o[i].toString());
        	this.addObserver(o[i]);
        }
       
      
	}
	
private HashMap<String, WebSocket> clients=new HashMap<String, WebSocket>();
	
	
	
	/**
	 * Add the websocket connection to a hash 
	 * 
	 * @param value Value for the hash table, use the value.getName() to set the key 
	 */
	public void addWebsocket(WebSocket value)
	{
		this.clients.put(value.getName(), value);
	}
	/**
	 * Add to hash the websocket connection
	 * @param key
	 * @param value 
	 */
	public void addWebsocket(String key,WebSocket value)
	{
		this.clients.put(key, value);
	}
	
	
	
	/**
	 * Remove  a websocket connection
	 * @param key Identifier of the websocket  
	 */
	
	public synchronized boolean removeWebSocket(String key)
	{
		return null!=this.clients.remove(key)?true:false;	
	}
	
	/**
	 * Remove  a websocket connection
	 * @param value Remove value from the hash
	 */
	public synchronized boolean removeWebSocket(WebSocket value)
	{
		return null!=this.clients.remove(value)?true:false;	
	}
	/**
	 * Get the specific WebSocket
	 * @param key Identifier of the websocket  
	 * @return the websocket with that name
	 */
	public WebSocket getWebSocket(String key) 
	{
		return this.clients.get(key);
	}
	
	/*
	 * Return a Name of class
	 * 
	 */
	public abstract String getName();
	
	@Override
	public void run() {
		this.startServer();
		
	}
	
}
