package com.challenge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import com.challenge.model.Position;
import com.challenge.model.Satellite;
import com.challenge.model.SatellitePosition;
import com.challenge.request.TopSecretRequest;
import com.challenge.request.TopSecretSplitRequest;
import com.challenge.response.TopSecretResponse;
import com.challenge.service.ChallengeService;
import com.challenge.util.MessageUtil;
import com.challenge.util.TrilaterationUtil;
import lombok.Getter;
import lombok.Setter;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
@Setter
public class ChallengeServiceImpl implements ChallengeService {

	@Setter
	private Map<String, TopSecretRequest> cache = new HashMap<String, TopSecretRequest>();

	private static final int LIMIT_TRILATERATION = 3;
	private static final double TOLERANCE_ERROR = 0.1;

	@Value("#{'${satellites.prueba}'.split(';')}")
	private List<SatellitePosition> satellites;

	private ArrayList<Float> distances = new ArrayList<Float>();
	private ArrayList<Float> positionX = new ArrayList<Float>();
	private ArrayList<Float> positionY = new ArrayList<Float>();
	private ArrayList<ArrayList<String>> messages = new ArrayList<>();

	@Override
	public HashMap<String, ArrayList<?>> TransformData(@RequestBody TopSecretRequest topSecretRequest)
			throws Exception {

		positionX.clear();
		positionY.clear();
		distances.clear();
		messages.clear();

		HashMap<String, ArrayList<?>> data = new HashMap<String, ArrayList<?>>();

		int numberOfSatellites = 0;

		if (LIMIT_TRILATERATION == topSecretRequest.getSatellites().size()) {
			for (SatellitePosition sp : satellites) {
				for (Satellite s : topSecretRequest.getSatellites()) {
					if (sp.getName().contains(s.getName())) {
						distances.add(s.getDistance());
						positionX.add(sp.getPosition().getX());
						positionY.add(sp.getPosition().getY());
						messages.add(s.getMessage());
						numberOfSatellites++;
					}
				}
			}

			data.put("distances", distances);
			data.put("positionX", positionX);
			data.put("positionY", positionY);
			data.put("messages", messages);

			if (numberOfSatellites == LIMIT_TRILATERATION) {
				return data;
			}
		}

		throw new Exception(
				"Para poder obtener la ubicacion de la nave se debe enviar el mensaje a los 3 satelites registrados en el sistema");
	}

	@Override
	public Position GetLocation(@RequestBody ArrayList<Float> distances) throws Exception {

		Double coordinateX;
		Double coordinateY;
		positionX.clear();
		positionY.clear();

		for (int i = 0; i < satellites.size(); i++) {
			positionX.add(satellites.get(i).getPosition().getX());
			positionY.add(satellites.get(i).getPosition().getY());
		}

		coordinateY = TrilaterationUtil.getCoordinateY(distances, positionX, positionY);
		coordinateX = TrilaterationUtil.getCoordinateX(distances, positionX, positionY, coordinateY);

		if (TrilaterationUtil.validateCoordinates(distances, satellites, coordinateX, coordinateY, TOLERANCE_ERROR)) {
			return new Position(coordinateX.floatValue(), coordinateY.floatValue());
		}

		throw new Exception("No se ha podido determinar la posision de la nave");

	}

	@Override
	public String GetMessage(ArrayList<ArrayList<String>> messagesList) throws Exception {

		final int size = messagesList.get(0).size();
		String[] message = new String[size];

		if (!MessageUtil.validateMessage(messagesList)) {
			throw new Exception("No se ha podido determinar el contenido del mensaje");
		}

		message = MessageUtil.determineMessage(messagesList, size);

		if (!MessageUtil.validateMessageContent(message)) {
			throw new Exception("No se ha podido determinar el contenido del mensaje");
		}

		return String.join(" ", message).trim();
	}

	@Override
	public TopSecretResponse saveInformation(final String satelliteName, final TopSecretSplitRequest request,
			final String remoteAddr) {

		Satellite newSatellite = new Satellite(satelliteName, request.getDistance(), request.getMessage());

		if (Objects.isNull(cache) || cache.size() == 0) {
			TopSecretRequest topSecretRequest = new TopSecretRequest();
			topSecretRequest.setSatellites(new ArrayList<>());
			topSecretRequest.getSatellites().add(newSatellite);
			cache.put(remoteAddr, topSecretRequest);

		} else {
			TopSecretRequest topSecretRequest = cache.get(remoteAddr);

			for (int i = 0; i < topSecretRequest.getSatellites().size(); i++) {
				if (topSecretRequest.getSatellites().get(i).getName().contains(satelliteName)) {
					cache.get(remoteAddr).getSatellites().remove(i);
				}
			}

			topSecretRequest.getSatellites().add(newSatellite);
			cache.put(remoteAddr, topSecretRequest);
		}

		return new TopSecretResponse(200, "ok");
	}

	@Override
	public TopSecretResponse getInformation(String remoteAddr) throws Exception {

		TopSecretRequest topSecretRequest = cache.get(remoteAddr);

		if (Objects.isNull(topSecretRequest) || topSecretRequest.getSatellites().size() != 3) {
			throw new Exception("No hay suficiente informacion");
		}

		return identifierMessage(topSecretRequest);
	}

	@Override
	public TopSecretResponse identifierMessage(TopSecretRequest topSecretRequest) throws Exception {

		String message;
		Position position;

		try {
			HashMap<String, ArrayList<?>> data = TransformData(topSecretRequest);

			@SuppressWarnings("unchecked")
			ArrayList<Float> distanceList = (ArrayList<Float>) data.get("distances");
			position = GetLocation(distanceList);

			@SuppressWarnings("unchecked")
			ArrayList<ArrayList<String>> messagesList = (ArrayList<ArrayList<String>>) data.get("messages");
			message = GetMessage(messagesList);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return new TopSecretResponse(message, position, 200);

	}

}
