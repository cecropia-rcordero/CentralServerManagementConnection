package com.ExXothermic.message;

import org.java_websocket.WebSocket;

import com.ExXothermic.communication.ComunicationManagementForClients;
import com.ExXothermic.communication.ComunicationManagementForUI;
import com.ExXothermic.communication.Response.TypeResponse;

public class ResponseMessageHandlerUI extends ResponseMessageHandlerAbstract {

	@Override
	public void Operation(WebSocket conn, String message, TypeResponse type) {
		if(TypeResponse.Message==type)
		{
			JSONMessage parser=new JSONMessage();
			parser.parse(message);
			String serial=parser.getId();
			// Know if the device is connected 
			ComunicationManagementForClients server=  ComunicationManagementForClients.getInstance();
			ComunicationManagementForUI ui=  ComunicationManagementForUI.getInstance();	
			WebSocket device=server.getWebSocket(serial);
			if(null==device)
			{
				ui.sendMessage(conn,new FactoryMessageForUI().Error(serial, "Device not connected"));
			}
			else
			{
				ui.addWebsocket(serial,conn);
				server.sendMessage(device,new FactoryMessageForUI().getMessageFromJSonParser(parser) );
				
			}
			
			
		}
	}

}
