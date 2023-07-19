package com.github.devfle.rheinleveldashboard;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class NavigationController {
    private String dashboardEndpoint = "index.xhtml";
    private String settingsEndpoint = "settings.xhtml";

    public String getDashboardEndpoint() {
        return dashboardEndpoint;
    }

    public String getSettingsEndpoint() {
        return settingsEndpoint;
    }
}
