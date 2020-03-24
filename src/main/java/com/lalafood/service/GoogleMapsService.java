package com.lalafood.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalafood.domain.dto.Coordinate;

@Service
public class GoogleMapsService {

	@Value("${google.api.key}")
	private String apiKey;

	public Integer getDistanceBetweenCoordinates(Coordinate origin, Coordinate destination)
			throws JsonMappingException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins={origin}&destinations={destination}&key={apiKey}";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, origin.getCoordinateAsString(),
				destination.getCoordinateAsString(), apiKey);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new RuntimeException("Error fetching distance from googleapi");
		}

		return extractDistanceFromResponse(response.getBody());
	}

	private Integer extractDistanceFromResponse(String responseBody)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(responseBody);
		JsonNode rowsNode = rootNode.path("rows");
		if (rowsNode.size() == 0) {
			if (rootNode.path("status").asText().equals("REQUEST_DENIED")) {
				throw new RuntimeException("The provided API key for GoogleMaps is invalid");
			}
		}
		
		JsonNode elementsNode = rowsNode.path(0).path("elements");
		JsonNode distanceNode = elementsNode.path(0).path("distance");

		String distanceText = distanceNode.path("value").asText();

		return (int) Math.round(Double.parseDouble(distanceText));
	}
}
