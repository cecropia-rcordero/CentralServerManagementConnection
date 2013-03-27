package com.ExXothermic.message;

import java.net.UnknownHostException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;

import com.ExXothermic.communication.ComunicationManagement;

public class FactoryMessage {
	
	private JSONMessage json= new JSONMessage();
	
	
	private static FactoryMessage INSTANCE;
	private synchronized static void createInstance()  {
        if (INSTANCE == null) { 
            INSTANCE = new FactoryMessage();
        }
    }
	
	 public static FactoryMessage getInstance() {
		 createInstance();
		 return INSTANCE;	        
	 }
	
	 
	public IMessage  GetMyBoxCert()
	{
		return new IMessage() {
			
			@Override
			public String getMessage(WebSocket conn) {
				return FactoryMessage.this.json.getEnconding("GetMyBoxCert","", new HashMap<String, String>());
			}
		};
	}
	
	public IMessage  Authenticate(String cert)
	{
		return new IMessage() {
			
			private String cert;
			@Override
			public String getMessage(WebSocket conn) {
				HashMap<String, String> hash = new HashMap<String, String>();
				//Maybe will be change
				hash.put("myBoxResponse", "algo");
				return FactoryMessage.this.json.getEnconding("Authenticate",this.cert,hash );
			}
			public IMessage setCert(String cert)
			{
				this.cert=cert;
				return this;
			}
		}.setCert(cert);
	}
	public IMessage LocalVerify()
	{
		return new IMessage() {
			
			@Override
			public String getMessage(WebSocket conn) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("myBoxChallenge", "algo");
				return FactoryMessage.this.json.getEnconding("myBoxInstallToken",conn.getName(),hash );
			}
		};
	}


	
}
