package com.challenge.util;

import java.util.ArrayList;

import org.apache.logging.log4j.util.Strings;

public class MessageUtil {
	public static boolean validateMessageContent(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			if (Strings.isBlank(strings[i])) {
				return false;
			}
		}
		return true;

	}

	public static boolean validateMessage(ArrayList<ArrayList<String>> messagesList) {
		final int size = messagesList.get(0).size();

		if (size == 0 || size != messagesList.get(1).size() || size != messagesList.get(2).size()) {
			return false;
		}

		return true;
	}

	public static String[] determineMessage(ArrayList<ArrayList<String>> messagesList, int size) {

		String[] message = new String[size];

		for (int i = 0; i < messagesList.size(); i++) {
			for (int j = 0; j < size; j++) {
				if (Strings.isNotBlank(messagesList.get(i).get(j).toString())) {
					message[j] = messagesList.get(i).get(j).toString();
				}
			}
		}
		return message;
	}
}
