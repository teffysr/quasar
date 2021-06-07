package com.challenge.request;

import java.util.List;

import com.challenge.model.Satellite;

public class TopSecretRequest {
	private List<Satellite> satellites;

	public List<Satellite> getSatellites() {
		return satellites;
	}

	public void setSatellites(List<Satellite> satellites) {
		this.satellites = satellites;
	}
}
