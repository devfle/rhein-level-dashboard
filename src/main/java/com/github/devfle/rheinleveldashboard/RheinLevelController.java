package com.github.devfle.rheinleveldashboard;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.List;

@Named
@ViewScoped
public class RheinLevelController implements Serializable {

    public static List<RheinLevelData> getRheinLevelData() throws URISyntaxException, IOException, InterruptedException {
        return RheinLevel.getInstance().getRheinLevel();
    }
}
