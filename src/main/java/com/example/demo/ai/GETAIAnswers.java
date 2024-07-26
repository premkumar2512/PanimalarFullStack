package com.example.demo.ai;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import org.aspectj.util.FileUtil;

public class GETAIAnswers {

	public static void main(String[] args) {
		
		String s ="Username is Prem, age is 29, from city Chennai and studied in Tagore engg college in Aero dept and hobbies playing critcket";
		String chatGPT = chatGPT(s);
		System.out.println("Chat "+ chatGPT);
	}

	public static String chatGPT(String prompt) {
		String url = "https://api.openai.com/v1/chat/completions";
		String apiKey = "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";
		String model = "gpt-3.5-turbo";

		try {
			URL obj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + apiKey);
			connection.setRequestProperty("Content-Type", "application/json");

			// The request body
			String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \""
					+ "Generate some about us with 250 words using these information" + prompt + "\"}]}";
			connection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(body);
			writer.flush();
			writer.close();

			// Response from ChatGPT
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;

			StringBuffer response = new StringBuffer();

			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();

			// calls the method to extract the message.
			return extractMessageFromJSONResponse(response.toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String extractMessageFromJSONResponse(String response) {
		int start = response.indexOf("content") + 11;

		int end = response.indexOf("\"", start);

		return response.substring(start, end);

	}

}