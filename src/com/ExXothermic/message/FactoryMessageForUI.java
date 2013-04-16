package com.ExXothermic.message;
import java.util.HashMap;

import org.java_websocket.WebSocket;
public class FactoryMessageForUI {
	private JSONMessage json= new JSONMessage();
	public IMessage Error(String serial,String transaction,String msj)
	{
		
		return new IMessage() {
			
			private String serial;
			private String msj;
			private String transaction;
			public IMessage setSerialAndMsj(String serial,String transaction,String msj)
			{
				this.transaction=transaction;
				this.serial=serial;
				this.msj=msj;
				return this;
			}

			
			
			@Override
			public String getMessage(WebSocket conn) {
				HashMap<String, String> data=new HashMap<String, String>();
				data.put("message",msj );
				return FactoryMessageForUI.this.json.getEnconding("Error", this.serial,this.transaction, new HashMap<String, String>(),data);
				
			}
		}.setSerialAndMsj(serial,transaction ,msj);
		
	}
	
	public IMessage getMessageFromJSonParser(JSONMessage json)
	{
		return new IMessage() {
			
			String body;
			
			public IMessage setBody(JSONMessage json)
			{
				this.body=json.getBody();
				return this;
			}
			@Override
			
			public String getMessage(WebSocket conn) {
				// TODO Auto-generated method stub
				return body;
			}
		}.setBody(json);
	}
}
