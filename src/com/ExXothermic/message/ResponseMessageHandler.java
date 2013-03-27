package com.ExXothermic.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;

import com.ExXothermic.communication.ComunicationManagement;
import com.ExXothermic.communication.Response.TypeResponse;

public class ResponseMessageHandler extends ResponseMessageHandlerAbstract {
	
	
	enum ServicesNames{ GETMYBOXCERT,LOCALVERIFY, AUTHENTICATE}
	private Logger  logger=Logger.getLogger(ComunicationManagement.class.getName());


	@Override
	public void Operation(WebSocket conn, String message, TypeResponse type) {
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
							Authenticate(parser.getData().get("myBoxChallenge").toString(),conn);
						}
						break;
					case LOCALVERIFY:
						
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
	
	private void operationGetMyBoxCert(String cert, WebSocket conn)
	{
		//validate certitificate
		ComunicationManagement.getInstance().sendMessage(conn,FactoryMessage.getInstance().Authenticate(cert));
	}
	
	private void Authenticate(String  myBoxChallenge, WebSocket conn)
	{
		
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
