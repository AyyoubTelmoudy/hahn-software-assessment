package com.hahn.software.service;



import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hahn.software.model.Ticket;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class TicketService {

    private static final String API_URL = "http://localhost:9090/api/v1/it-support/all-ticket-list";
    private static final String AUTH_TOKEN = "YOUR_ACCESS_TOKEN"; // Set dynamically after login
    private static final OkHttpClient client = new OkHttpClient();

    public static List<Ticket> getAllTickets() {
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + AUTH_TOKEN)
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.body().string(), JsonObject.class);
                return gson.fromJson(jsonResponse.getAsJsonArray("tickets"), new TypeToken<List<Ticket>>() {}.getType());
            } else {
                System.err.println("Failed to fetch tickets: HTTP " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of(); // Return an empty list in case of failure
    }
}
