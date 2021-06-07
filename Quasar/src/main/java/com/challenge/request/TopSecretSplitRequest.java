package com.challenge.request;

import java.util.ArrayList;

public class TopSecretSplitRequest {

	private ArrayList<String> message;
	private Float distance;

	public TopSecretSplitRequest(ArrayList<String> message, Float distance) {
		this.message = message;
		this.distance = distance;
	}

	public ArrayList<String> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<String> message) {
		this.message = message;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

}
