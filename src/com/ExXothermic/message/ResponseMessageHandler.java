package com.ExXothermic.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.ExXothermic.RestFul.Client;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;

import com.ExXothermic.communication.ComunicationManagementForClients;
import com.ExXothermic.communication.ComunicationManagementForUI;
import com.ExXothermic.communication.Response.TypeResponse;

public class ResponseMessageHandler extends ResponseMessageHandlerAbstract {
	
	
	enum ServicesNames{ GETMYBOXCERT,LOCALVERIFY, AUTHENTICATE,SETCHANNELINFO,GETCHANNELINFO}
	private Logger  logger=Logger.getLogger(ComunicationManagementForClients.class.getName());


	@Override
	public void Operation(WebSocket conn, String message, TypeResponse type) {
		if(TypeResponse.CloseConnection==type)
		{

			onCloseConnection(conn);
		}
		if(TypeResponse.Message==type)
		{
			
			JSONMessage parser=new JSONMessage();
			parser.parse(message);
			try
			{
				ServicesNames s=ServicesNames.valueOf(parser.getAction().toUpperCase());
				List<String> arr;
				switch (s)
				{
					case GETMYBOXCERT:
							arr=new ArrayList<String>();
							arr.add("cert");
							if (validateParams(arr,parser.getData().keySet(),conn,parser.getAction(),true))
							{
								operationGetMyBoxCert(parser.getData().get("cert").toString(),conn);
							}
							
						break;
					case AUTHENTICATE:
						arr=new ArrayList<String>();
						arr.add("myBoxChallenge");
						if (validateParams(arr,parser.getData().keySet(),conn,parser.getAction(),true))
						{
							Authenticate(parser,conn);
						}
						break;
					case LOCALVERIFY:
						arr=new ArrayList<String>();
						arr.add("myBoxIpAddress");
						if (validateParams(arr,parser.getData().keySet(),conn,parser.getAction(),true))
						{
							LocalVerify(parser.getData().get("myBoxIpAddress").toString(),conn);
						}
						break;
					case SETCHANNELINFO:
							setChannelnfo(parser,conn);
						break;
					case GETCHANNELINFO:
							getChannelnfo(parser,conn);
						break;
						
					default:
						
						break;
						
				}
			}
			catch(IllegalArgumentException ex)
			{
				logger.error("Unknow action:"+parser.getAction(), ex);
			}

		}
	}
	
	private void getChannelnfo(JSONMessage parser, WebSocket conn) {
		ComunicationManagementForUI ui= ComunicationManagementForUI.getInstance();
		WebSocket socket=ui.getWebSocket(parser.getTransaction());
		ui.sendMessage(socket, new  FactoryMessageForUI().getMessageFromJSonParser(parser));
	}

	private void setChannelnfo(JSONMessage parser, WebSocket conn) {
		ComunicationManagementForUI ui= ComunicationManagementForUI.getInstance();
		WebSocket socket=ui.getWebSocket(parser.getTransaction());
		ui.sendMessage(socket, new  FactoryMessageForUI().getMessageFromJSonParser(parser));		
	}

	private void onCloseConnection(WebSocket conn) {
		//Client clientRes=new Client();
		//clientRes.disconnect(conn.getName());
		ComunicationManagementForClients server=  ComunicationManagementForClients.getInstance();
		server.removeWebSocket(conn.getName());
		
	}

	private void LocalVerify(String string, WebSocket conn) {
		
	}

	private void operationGetMyBoxCert(String cert, WebSocket conn)
	{
		//validate certitificate
		ComunicationManagementForClients.getInstance().sendMessage(conn,FactoryMessage.getInstance().Authenticate(cert));
	}
	
	private void Authenticate(JSONMessage  parser, WebSocket conn)
	{
		//validate is necesary
		Client clientRes=new Client();
		clientRes.register(parser.getId());
		conn.setName(parser.getId());
		ComunicationManagementForClients.getInstance().addWebsocket( conn);
	}
	
	
	private boolean validateParams(List<String> params, Set<String> keys, WebSocket con, String action,boolean closeConnection)
	{
	
		Iterator<String> i =params.iterator();
		while (i.hasNext())
		{
			String si=i.next();
			if(!keys.contains(si))
			{
				logger.warn("Action: "+action + " Socket: "+con.getName()+" missing param:"+si );
				if(closeConnection)
				{
					con.close();
				}
				return false;
			}
		}
		return Boolean.TRUE;
	}

}
