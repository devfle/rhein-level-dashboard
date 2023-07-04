package com.github.devfle.rheinleveldashboard;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class RheinLevelController {

    public static List<List<RheinLevelData>> getRheinLevelData() throws URISyntaxException, IOException, InterruptedException {
        return RheinLevel.getInstance().getRheinLevel();
    }

    public static Map<String, String> getApiStations() throws URISyntaxException, IOException, InterruptedException {
        return RheinLevel.getInstance().getApiStations();
    }
}
