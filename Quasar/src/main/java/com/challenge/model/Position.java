package com.challenge.model;

public class Position {
	public Position(Float x, Float y) {
		this.x = x;
		this.y = y;
	}

	private Float x;
	private Float y;

	public Position() {

	}

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

}
