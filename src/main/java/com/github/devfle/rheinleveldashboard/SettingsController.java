package com.github.devfle.rheinleveldashboard;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class SettingsController implements Serializable {
    private String[] selectedOptions = { "Bonn", "Dusseldorf", "Konstanz", "Mainz" };
    private List<String> stations = new ArrayList<>();

    @Inject
    private RheinLevel rheinLevel;

    @PostConstruct
    public void init() {

        Map<String, String> stationData;

        stationData = rheinLevel.getApiStations();

        for (Map.Entry<String, String> singleStationData : stationData.entrySet()) {
            stations.add(singleStationData.getKey());
        }
    }

    public List<String> getStations() {
        return stations;
    }

    public String[] getSelectedOptions() {
        return selectedOptions;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public void setSelectedOptions(String[] selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
}
