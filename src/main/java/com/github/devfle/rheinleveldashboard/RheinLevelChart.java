package com.github.devfle.rheinleveldashboard;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class RheinLevelChart implements Serializable {
    private LineChartModel lineModel;

    @PostConstruct
    public void init() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> chartValues = new ArrayList<>();

        List<RheinLevelData> rheinLevelData = null;

        try {
            rheinLevelData = RheinLevelController.getRheinLevelData();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (RheinLevelData rheinData : rheinLevelData) {
            chartValues.add(rheinData.getValue());
        }

        dataSet.setData(chartValues);
        dataSet.setFill(true);
        dataSet.setLabel("Cologne");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setTension(0.1);
        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();

        for (byte time = 0; time < 24; time++) {
            labels.add(String.format("%02d:00", time));
        }

        data.setLabels(labels);

        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Line Chart");
        options.setTitle(title);

        lineModel.setOptions(options);
        lineModel.setData(data);
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }
}
