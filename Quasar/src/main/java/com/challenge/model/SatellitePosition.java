package com.challenge.model;

public class SatellitePosition {
	public SatellitePosition(String name, Position position) {
		this.name = name;
		this.position = position;
	}

	public SatellitePosition(String satellite) {
		this.name = satellite.split(" ")[0];
		this.position = new Position(Float.parseFloat(satellite.split(" ")[1]),
				Float.parseFloat(satellite.split(" ")[2]));
	}

	private String name;
	private Position position;

	public SatellitePosition() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
