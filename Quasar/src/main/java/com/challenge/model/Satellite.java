package com.challenge.model;

import java.util.ArrayList;

public class Satellite {
	public Satellite(String name, Float distance, ArrayList<String> message) {
		this.name = name;
		this.distance = distance;
		this.message = message;
	}

	private String name;
	private Float distance;

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	private ArrayList<String> message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<String> message) {
		this.message = message;
	}

}
