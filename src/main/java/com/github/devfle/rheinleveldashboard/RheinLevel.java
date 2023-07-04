package com.github.devfle.rheinleveldashboard;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Named
@RequestScoped
public class RheinLevel {
    public static RheinLevel instance = null;
    private final Map<String, String> apiStations = new HashMap<>();
    private final List<List<RheinLevelData>> rheinLevel = new ArrayList<>();
    private RheinLevel() throws URISyntaxException, IOException, InterruptedException {

        apiStations.put("Bonn", "593647aa-9fea-43ec-a7d6-6476a76ae868");
        apiStations.put("Dusseldorf", "8f7e5f92-1153-4f93-acba-ca48670c8ca9");
        apiStations.put("Konstanz", "e020e651-e422-46d3-ae28-34887c5a4a8e");
        apiStations.put("Mainz", "a37a9aa3-45e9-4d90-9df6-109f3a28a5af");

        String[] stationSettings = SettingsView.instance.getSelectedOptions();

        for (Map.Entry<String, String> apiEntry : apiStations.entrySet()) {

            if (!Arrays.asList(stationSettings).contains(apiEntry.getKey())) {
                continue;
            }

            // build the GET request
            HttpRequest rheinDataRequest = HttpRequest.newBuilder()
                    .uri(new URI(String.format("https://www.pegelonline.wsv.de/webservices/rest-api/v2/stations/%s/W/measurements.json?start=P1D", apiEntry.getValue())))
                    .build();

            // send the GET request
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> rheinDataResponse = client.send(rheinDataRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            List<RheinLevelData> singleLevelData = gson.fromJson(rheinDataResponse.body(), new TypeToken<List<RheinLevelData>>(){}.getType());

            for (RheinLevelData levelData : singleLevelData) {
                levelData.setStationName(apiEntry.getKey());
            }

            rheinLevel.add(singleLevelData);
        }

    }
    public static RheinLevel getInstance() throws URISyntaxException, IOException, InterruptedException {
        // create instance only if we really need it
        if (instance == null) {
            instance = new RheinLevel();
        }
        return instance;
    }

    public Map<String, String> getApiStations() {
        return apiStations;
    }

    public List<List<RheinLevelData>> getRheinLevel() {
        return rheinLevel;
    }
}
