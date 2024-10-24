package com.github.danilobandeira29.easy_funds.transfer_authorization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class AuthorizationAdapter implements IAuthorizationAdapter {
    private HttpClient client;
    private final String baseUrl = "https://util.devi.tools/api/v2";
    private final ObjectMapper objectMapper;

    public AuthorizationAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        client = HttpClient.newHttpClient();
    }

    @Override
    public boolean isAuthorized() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/authorize"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200) {
                return false;
            }
            JsonNode jsonNode = objectMapper.readTree(resp.body());
            return jsonNode.path("data").path("authorization").asBoolean();
        } catch (IOException | InterruptedException e) {
            System.out.println("error when trying to authorize transaction " + e.getMessage());
            return false;
        }
    }
}
