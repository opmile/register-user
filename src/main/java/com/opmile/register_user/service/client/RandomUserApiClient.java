package com.opmile.register_user.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opmile.register_user.dto.fetch_api.FetchResponseDTO;
import com.opmile.register_user.dto.fetch_api.UserApiDTO;
import com.opmile.register_user.exceptions.FetchErrorException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class RandomUserApiClient {

    private static final String BASE_URL = "https://randomuser.me/api";

    private final HttpClient client;
    private final ObjectMapper mapper;

    public RandomUserApiClient(HttpClient client, ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    public List<UserApiDTO> fetchApi(int quantity) throws FetchErrorException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://randomuser.me/api/" + "?results="  + quantity))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            FetchResponseDTO readResponse = mapper.readValue(response.body(), FetchResponseDTO.class);

            return readResponse.results();
        } catch (IOException | InterruptedException e) {
            throw new FetchErrorException("Falha ao buscar dados na API", e);
        }
    }
}
