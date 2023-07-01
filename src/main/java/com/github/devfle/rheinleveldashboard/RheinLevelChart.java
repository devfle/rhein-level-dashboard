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
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
        List<String> labels = new ArrayList<>();

        try {
            rheinLevelData = RheinLevelController.getRheinLevelData();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (RheinLevelData rheinData : rheinLevelData) {
            String rheinLevelTimeStamp = rheinData.getTimestamp();
            OffsetDateTime parsedTimeStamp = OffsetDateTime.parse(rheinLevelTimeStamp);
            LocalDateTime localDateTime = parsedTimeStamp.toLocalDateTime();

            if (0 != localDateTime.getMinute()) {
                continue;
            }

            chartValues.add(rheinData.getValue());
            labels.add(String.format("%02d:00" ,localDateTime.getHour()));
        }

        dataSet.setData(chartValues);
        dataSet.setFill(true);
        dataSet.setLabel("Cologne");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setTension(0.1);
        data.addChartDataSet(dataSet);

        data.setLabels(labels);

        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Rhein Level");
        options.setTitle(title);

        lineModel.setOptions(options);
        lineModel.setData(data);
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }
}
