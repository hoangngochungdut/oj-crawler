package com.hung.project.API;

import java.net.URI;
import com.google.gson.JsonArray;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.github.cdimascio.dotenv.Dotenv;

public class GeminiAnalyzer {
	private static final Dotenv dotenv = Dotenv.configure().load();
	
    private static final String API_KEY = dotenv.get("GEMINI_API_KEY");

    private static final String API_URL = dotenv.get("GEMINI_BASE_URL") + API_KEY;

    private HttpClient client;

    private Gson gson;

    public GeminiAnalyzer() {

        client =
            HttpClient.newHttpClient();

        gson =
            new Gson();
    }

    public AnalysisResult analyzeCode(
    	    String sourceCode
    	) {

    	    try {

    	        String prompt =
    	            """
    	            Analyze this competitive programming source code.

    	            Return ONLY raw JSON.

    	            No markdown.
    	            No explanation.
    	            No extra text.

    	            JSON schema:

    	            {
    	              "data_structure_rate": double,
    	              "data_structure_analyse": string,

    	              "algorithm_rate": double,
    	              "algorithm_analyse": string,

    	              "using_ai_rate": double,
    	              "using_ai_analyse": string
    	            }

    	            Rules:
    	            - All rates must be between 0 and 10.
    	            - Response must be valid JSON.
    	            - Do not wrap JSON in markdown.

    	            Source code:

    	            %s
    	            """
    	            .formatted(sourceCode);

    	        // BUILD JSON BODY SAFELY

    	        JsonObject textObj =
    	            new JsonObject();

    	        textObj.addProperty(
    	            "text",
    	            prompt
    	        );

    	        JsonArray parts =
    	            new JsonArray();

    	        parts.add(textObj);

    	        JsonObject content =
    	            new JsonObject();

    	        content.add(
    	            "parts",
    	            parts
    	        );

    	        JsonArray contents =
    	            new JsonArray();

    	        contents.add(content);

    	        JsonObject requestBody =
    	            new JsonObject();

    	        requestBody.add(
    	            "contents",
    	            contents
    	        );

    	        String body =
    	            gson.toJson(requestBody);

    	        HttpRequest request =
    	            HttpRequest.newBuilder()
    	                .uri(
    	                    URI.create(API_URL)
    	                )
    	                .header(
    	                    "Content-Type",
    	                    "application/json"
    	                )
    	                .POST(
    	                    HttpRequest.BodyPublishers
    	                        .ofString(body)
    	                )
    	                .build();

    	        HttpResponse<String> response =
    	            client.send(
    	                request,
    	                HttpResponse.BodyHandlers
    	                    .ofString()
    	            );

    	        String responseBody =
    	            response.body();

    	        System.out.println(
    	            responseBody
    	        );

    	        JsonObject root =
    	            JsonParser
    	                .parseString(responseBody)
    	                .getAsJsonObject();

    	        // HANDLE API ERROR

    	        if (root.has("error")) {

    	            System.out.println(
    	                root.get("error")
    	            );

    	            return null;
    	        }

    	        String text = root
    	                .getAsJsonArray("candidates")
    	                .get(0)
    	                .getAsJsonObject()
    	                .getAsJsonObject("content")
    	                .getAsJsonArray("parts")
    	                .get(0)
    	                .getAsJsonObject()
    	                .get("text")
    	                .getAsString();

    	        text = text
    	                .replace("```json", "")
    	                .replace("```", "")
    	                .trim();

    	        System.out.println(text);

    	        return gson.fromJson(
    	            text,
    	            AnalysisResult.class
    	        );

    	    } catch (Exception e) {

    	        e.printStackTrace();
    	    }

    	    return null;
    	}
}