package com.ExXothermic.message;

import org.java_websocket.WebSocket;

public interface IMessage {

	String getMessage(WebSocket conn);
		
}
