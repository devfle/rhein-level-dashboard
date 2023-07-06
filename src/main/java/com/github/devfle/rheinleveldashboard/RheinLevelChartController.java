package com.github.devfle.rheinleveldashboard;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class RheinLevelChartController {
    private LineChartModel lineModel;

    @Inject
    private RheinLevel rheinLevel;

    @PostConstruct
    public void init() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();

        List<List<RheinLevelData>> rheinLevelData;
        List<String> labels = new ArrayList<>();
        List<String> colorCodes = List.of("#002A40", "#00547F", "#007FBF", "#1BB8FA");

        try {
            rheinLevelData = rheinLevel.retrieveRheinLevelData();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean allowLabel = true;
        byte index = 0;
        for (List<RheinLevelData> rheinDataList : rheinLevelData) {

            LineChartDataSet dataSet = new LineChartDataSet();
            List<Object> chartValues = new ArrayList<>();

            for (RheinLevelData rheinData : rheinDataList) {
                String rheinLevelTimeStamp = rheinData.getTimestamp();
                OffsetDateTime parsedTimeStamp = OffsetDateTime.parse(rheinLevelTimeStamp);
                LocalDateTime localDateTime = parsedTimeStamp.toLocalDateTime();

                if (0 != localDateTime.getMinute()) {
                    continue;
                }

                chartValues.add(rheinData.getValue());

                if (!allowLabel) {
                    continue;
                }

                labels.add(String.format("%02d:00" ,localDateTime.getHour()));
            }

            // after first iteration disable label adding new values
            allowLabel = false;

            dataSet.setBorderColor(colorCodes.get(index));
            dataSet.setBackgroundColor(colorCodes.get(index));

            dataSet.setData(chartValues);
            dataSet.setLabel(rheinDataList.get(0).getStationName().toUpperCase());
            data.addChartDataSet(dataSet);
            data.setLabels(labels);

            LineChartOptions options = new LineChartOptions();

            lineModel.setOptions(options);
            lineModel.setData(data);
            index++;
        }
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }
}
