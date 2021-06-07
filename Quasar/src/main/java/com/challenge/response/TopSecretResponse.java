package com.challenge.response;

import com.challenge.model.Position;

//import org.apache.catalina.connector.Response;
//import org.apache.catalina.connector.ResponseFacade;

public class TopSecretResponse {
	public TopSecretResponse(String message, Position position, int statusCode) {
		this.message = message;
		this.position = position;
		this.statusCode = statusCode;
	}

	public TopSecretResponse(int code, String message) {
		this.statusCode = code;
		this.message = message;
	}

	private String message;
	private Position position;
	private int statusCode;

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
