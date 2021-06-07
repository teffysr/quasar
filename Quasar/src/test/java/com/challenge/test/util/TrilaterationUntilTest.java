package com.challenge.test.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.challenge.model.Position;
import com.challenge.model.SatellitePosition;
import com.challenge.util.TrilaterationUtil;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TrilaterationUntilTest {

	private float[] distances;
	private static final double TOLERANCE_ERROR = 0.1;

	@Test
	void validateCoordinatesTest() {
		ArrayList<Float> distances = new ArrayList<Float>();
		List<SatellitePosition> satellites = new ArrayList<SatellitePosition>();
		Double positionX = -100.0;
		Double positionY = 75.5;
		distances.add(485.69f);
		distances.add(266.08f);
		distances.add(600.5f);
		satellites.add(new SatellitePosition("kenobi", new Position(-500.0f,-200.0f)));
		satellites.add(new SatellitePosition("skywalker", new Position(100.0f,-100.0f)));
		satellites.add(new SatellitePosition("sato", new Position(500.0f,100.0f)));
		
		boolean valid = TrilaterationUtil.validateCoordinates(distances, satellites, positionX, positionY, TOLERANCE_ERROR);
		
		assertTrue(valid,"ok");
	}

	@Test
	void equationCircumferenceTest() {
		Float x = -100f;
		Float y = 75.5f;
		Float a = -500f;
		Float b = -200f;
		Float r = 485.69f;
		Double equation = TrilaterationUtil.equationCircumference(x, y, a, b, r);
		assertFalse(Math.abs(equation - r) > TOLERANCE_ERROR, "ok");
	}

	@Test
	void getCoordinateYTest() {
		ArrayList<Float> distances = new ArrayList<Float>();
		ArrayList<Float> positionX = new ArrayList<Float>();
		ArrayList<Float> positionY = new ArrayList<Float>();
		distances.add(485.69f);
		distances.add(266.08f);
		distances.add(600.5f);
		positionX.add(-500f);
		positionX.add(100f);
		positionX.add(500f);
		positionY.add(-200f);
		positionY.add(-100f);
		positionY.add(100f);

		assertEquals(75.5, TrilaterationUtil.getCoordinateY(distances, positionX, positionY));
	}

	@Test
	void getCoordinateXTest() {
		ArrayList<Float> distances = new ArrayList<Float>();
		ArrayList<Float> positionX = new ArrayList<Float>();
		ArrayList<Float> positionY = new ArrayList<Float>();
		Double Y = 75.5;
		distances.add(485.69f);
		distances.add(266.08f);
		distances.add(600.5f);
		positionX.add(-500f);
		positionX.add(100f);
		positionX.add(500f);
		positionY.add(-200f);
		positionY.add(-100f);
		positionY.add(100f);

		assertEquals(-100.0, TrilaterationUtil.getCoordinateX(distances, positionX, positionY, Y));
	}

	@Test
	void roundOneDecimalsTest() {
		distances = new float[] { 485.69f, 266.08f, 600.5f };
		assertEquals(75.5, TrilaterationUtil.roundOneDecimals(75.456665894));
		assertEquals(485.7, TrilaterationUtil.roundOneDecimals(485.6978));
		assertEquals(266.1, TrilaterationUtil.roundOneDecimals(266.083163691f));
		assertEquals(485.7, TrilaterationUtil.roundOneDecimals(485.6956351461273f));
	}

}
