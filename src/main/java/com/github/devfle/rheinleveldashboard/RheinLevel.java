package com.github.devfle.rheinleveldashboard;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Named
@SessionScoped
public class RheinLevel implements Serializable {
    private final Map<String, String> apiStations = new HashMap<>();


    @Inject
    private SettingsController settingsController;

    @Inject
    private RheinLevelFilterController rheinLevelFilterController;

    public RheinLevel() {
        apiStations.put("Bonn", "593647aa-9fea-43ec-a7d6-6476a76ae868");
        apiStations.put("Dusseldorf", "8f7e5f92-1153-4f93-acba-ca48670c8ca9");
        apiStations.put("Konstanz", "e020e651-e422-46d3-ae28-34887c5a4a8e");
        apiStations.put("Mainz", "a37a9aa3-45e9-4d90-9df6-109f3a28a5af");
    }

    public List<List<RheinLevelData>> retrieveRheinLevelData() throws URISyntaxException, IOException, InterruptedException {
        String[] stationSettings = settingsController.getSelectedOptions();
        short minRheinLevel = rheinLevelFilterController.getFilterValue();

        List<List<RheinLevelData>> rheinLevel = new ArrayList<>();

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

            for (RheinLevelData levelData : singleLevelData) {
                if (levelData.getValue() >= minRheinLevel) {
                    rheinLevel.add(singleLevelData);
                    break;
                }
            }
        }

        return rheinLevel;
    }

    public void setSettingsView(SettingsController settingsView) {
        this.settingsController = settingsView;
    }

    public SettingsController getSettingsView() {
        return settingsController;
    }

    public Map<String, String> getApiStations() {
        return apiStations;
    }
}
