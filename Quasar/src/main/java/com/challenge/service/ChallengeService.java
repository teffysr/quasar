package com.challenge.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.challenge.model.Position;
import com.challenge.request.TopSecretRequest;
import com.challenge.request.TopSecretSplitRequest;
import com.challenge.response.TopSecretResponse;

public interface ChallengeService {

	public HashMap<String, ArrayList<?>> TransformData(TopSecretRequest request) throws Exception;

	public Position GetLocation(ArrayList<Float> arrayList) throws Exception;

	public String GetMessage(ArrayList<ArrayList<String>> messageslist) throws Exception;

	public TopSecretResponse saveInformation(String satelliteName, TopSecretSplitRequest request, String ipAddress);

	public TopSecretResponse getInformation(String remoteAddr) throws Exception;

	public TopSecretResponse identifierMessage(TopSecretRequest request) throws Exception;
}
