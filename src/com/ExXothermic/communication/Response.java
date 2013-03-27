package com.ExXothermic.communication;

import org.java_websocket.WebSocket;

public class Response {
	public enum TypeResponse{NewConnection,Message, CloseConnection}
	
	private TypeResponse typeRespose;
	private WebSocket conn;
	private String message;
	
	
	
	public WebSocket getConn() {
		return conn;
	}
	public String getMessage() {
		return message;
	}
	public TypeResponse getTypeRespose() {
		return typeRespose;
	}
	private void setConn(WebSocket conn) {
		this.conn = conn;
	}
	private void setMessage(String message) {
		this.message = message;
	}
	private void setTypeRespose(TypeResponse typeRespose) {
		this.typeRespose = typeRespose;
	}
	public Response(TypeResponse typeRespose,WebSocket conn,String message) {
		this.setConn(conn);
		this.setMessage(message);
		this.setTypeRespose(typeRespose);
	}
}
