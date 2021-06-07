package com.challenge.util;

import java.util.ArrayList;
import java.util.List;

import com.challenge.model.SatellitePosition;

public class TrilaterationUtil {

	public static boolean validateCoordinates(ArrayList<Float> distances, List<SatellitePosition> satellites,
			Double positionX, Double positionY, double toleranceError) {
		for (int i = 0; i < satellites.size(); i++) {
			Float x = positionX.floatValue();
			Float y = positionY.floatValue();
			Float a = satellites.get(i).getPosition().getX();
			Float b = satellites.get(i).getPosition().getY();

			Double punto = equationCircumference(x, y, a, b, distances.get(i));

			if (Math.abs(punto - distances.get(i)) > toleranceError) {
				return false;
			}
		}

		return true;
	}

	public static Double equationCircumference(Float x, Float y, Float a, Float b, Float r) {
		return Math.sqrt(Math.pow(x - a, 2) + Math.pow(y - b, 2));
	}

	public static Double getCoordinateY(ArrayList<Float> distances, ArrayList<Float> positionX,
			ArrayList<Float> positionY) {
		Double coordenate = ((positionX.get(1) - positionX.get(0))
				* (Math.pow(positionX.get(2), 2) + Math.pow(positionY.get(2), 2) - Math.pow(distances.get(2), 2))
				+ (positionX.get(0) - positionX.get(2)) * (Math.pow(positionX.get(1), 2) + Math.pow(positionY.get(1), 2)
						- Math.pow(distances.get(1), 2))
				+ (positionX.get(2) - positionX.get(1)) * (Math.pow(positionX.get(0), 2) + Math.pow(positionY.get(0), 2)
						- Math.pow(distances.get(0), 2)))
				/ (2 * (positionY.get(2) * (positionX.get(1) - positionX.get(0))
						+ positionY.get(1) * (positionX.get(0) - positionX.get(2))
						+ positionY.get(0) * (positionX.get(2) - positionX.get(1))));

		return roundOneDecimals(coordenate);
	}

	public static Double getCoordinateX(ArrayList<Float> distances, ArrayList<Float> positionX,
			ArrayList<Float> positionY, Double Y) {
		Double coordenate = (Math.pow(distances.get(1), 2) + Math.pow(positionX.get(0), 2)
				+ Math.pow(positionY.get(0), 2) - Math.pow(distances.get(0), 2) - Math.pow(positionX.get(1), 2)
				- Math.pow(positionY.get(1), 2) - 2 * (positionY.get(0) - positionY.get(1)) * Y)
				/ (2 * (positionX.get(0) - positionX.get(1)));

		return roundOneDecimals(coordenate);
	}

	public static double roundOneDecimals(final double value) {
		return (double) Math.round(value * 10) / 10;
	}
}
