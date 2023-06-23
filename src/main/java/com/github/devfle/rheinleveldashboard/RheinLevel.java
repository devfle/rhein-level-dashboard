package com.github.devfle.rheinleveldashboard;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Named
@ViewScoped
public class RheinLevel implements Serializable {

    private final Gson gson;
    private final HttpClient client;
    public static RheinLevel instance = null;

    private List<RheinLevelData> rheinLevel;
    private RheinLevel() throws URISyntaxException, IOException, InterruptedException {

        // build the GET request
        HttpRequest rheinDataRequest = HttpRequest.newBuilder()
                .uri(new URI("https://www.pegelonline.wsv.de/webservices/rest-api/v2/stations/593647aa-9fea-43ec-a7d6-6476a76ae868/W/measurements.json?start=P1D"))
                .build();

        // send the GET request
        client = HttpClient.newHttpClient();
        HttpResponse<String> rheinDataResponse = client.send(rheinDataRequest, HttpResponse.BodyHandlers.ofString());

        gson = new Gson();
        rheinLevel = gson.fromJson(rheinDataResponse.body(), new TypeToken<List<RheinLevelData>>(){}.getType());

    }
    public static RheinLevel getInstance() throws URISyntaxException, IOException, InterruptedException {
        // create instance only if we really need it
        if (instance == null) {
            instance = new RheinLevel();
        }
        return instance;
    }

    public List<RheinLevelData> getRheinLevel() {
        return rheinLevel;
    }
}
