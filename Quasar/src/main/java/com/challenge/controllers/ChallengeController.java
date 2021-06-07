package com.challenge.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.request.TopSecretRequest;
import com.challenge.request.TopSecretSplitRequest;
import com.challenge.response.TopSecretResponse;
import com.challenge.service.ChallengeService;
import com.challenge.service.impl.ChallengeServiceImpl;

@RestController
public class ChallengeController {

	@Autowired
	private ChallengeService challengeService;

	@Bean
	protected ChallengeService getChallengeService() throws Exception {
		return new ChallengeServiceImpl();
	}

	@PostMapping(value = "topsecret", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TopSecretResponse> topsecret(@RequestBody TopSecretRequest topSecretRequest)
			throws Exception {

		TopSecretResponse response;
		try {

			response = challengeService.identifierMessage(topSecretRequest);

		} catch (Exception e) {
			return new ResponseEntity<TopSecretResponse>(new TopSecretResponse(404, e.getMessage()),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<TopSecretResponse>(response, HttpStatus.OK);
	}

	@GetMapping(value = "topsecret_split/{satellite_name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TopSecretResponse> gettopsecretSplit(@PathVariable("satellite_name") String satellite_name,
			@RequestBody TopSecretSplitRequest topSecretSplitRequest, HttpServletRequest httpServletRequest)
			throws Exception {

		TopSecretResponse topSecretResponse;
		try {
			topSecretResponse = challengeService.getInformation(httpServletRequest.getRemoteAddr());
		} catch (Exception e) {
			return new ResponseEntity<TopSecretResponse>(new TopSecretResponse(404, e.getMessage()),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<TopSecretResponse>(topSecretResponse, HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "topsecret_split/{satellite_name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TopSecretResponse> topsecretSplit(@PathVariable("satellite_name") String satelliteName,
			@RequestBody TopSecretSplitRequest topSecretSplitRequest, HttpServletRequest httpServletRequest) {

		TopSecretResponse topSecretResponse = challengeService.saveInformation(satelliteName, topSecretSplitRequest,
				httpServletRequest.getRemoteAddr());

		return new ResponseEntity<TopSecretResponse>(topSecretResponse, HttpStatus.OK);

	}

}
