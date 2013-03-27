package com.ExXothermic.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.apache.log4j.Logger;

import com.ExXothermic.message.IMessage;


/**
 * Note for developer, the original library for websocket is https://github.com/TooTallNate/Java-WebSocket
 * i need to change some properties and fork the library 
 * https://github.com/cecropia-rcordero/Java-WebSocket 
 * 
 * 
 * */
public class ComunicationServer extends WebSocketServer {
	
	
	private Logger  logger;
		
	private IMessage MessageWhenOpenConnection;
	
	

	public void setMessageWhenOpenConnection(IMessage obj)
	{
		this.MessageWhenOpenConnection=obj;
	}
	
	
	
	

	
	private String getMessageWhenOpenConnection(WebSocket conn)
	{
		return null!=this.MessageWhenOpenConnection? this.MessageWhenOpenConnection.getMessage(conn):"";
	}
	public ComunicationServer( int port ) throws UnknownHostException {
		super( new InetSocketAddress( port ) );
		logger= Logger.getLogger(this.getClass().getName());

		
	}

	public ComunicationServer( InetSocketAddress address ) {
		super( address );
		logger= Logger.getLogger(this.getClass().getName());
	}

	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {
		logger.info( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " Conection Open" );
		this.notifyObservers(new Response(Response.TypeResponse.NewConnection, conn, ""));

			this.sendMessage(getMessageWhenOpenConnection(conn), conn);

	}

	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {	
		logger.info(conn + " Connection Close");
		this.notifyObservers(new Response(Response.TypeResponse.CloseConnection, conn, ""));
	}

	@Override
	public void onMessage( WebSocket conn, String message ) {
		logger.info( conn + " send: " + message );
		setChanged();
		this.notifyObservers(new Response(Response.TypeResponse.Message, conn, message));
	}
	@Override
	public void onError( WebSocket conn, Exception ex ) {
		logger.error(conn, ex);
		if( conn != null ) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}

	public void sendMessage(String text, WebSocket connection)
	{
		
		logger.info("Connection "+connection.toString() +" message "+ text);
		connection.send( text );
	}
	
	/**
	 * Sends <var>text</var> to all currently connected WebSocket clients.
	 * 
	 * @param text
	 *            The String to send across the network.
	 * @throws InterruptedException
	 *             When socket related I/O errors occur.
	 */
	public void Brodcast( String text ) {
		Collection<WebSocket> con = connections();
		synchronized ( con ) {
			for( WebSocket c : con ) {
				c.send( text );
			}
		}
	} // END sendToAll
}