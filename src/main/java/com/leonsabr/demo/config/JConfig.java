package com.leonsabr.demo.config;

public class JConfig {

    public static final JConfig INSTANCE = new JConfig();

    private final String seleniumHub;

    private final String browserName;

    private final String teamcityBaseURL;

    private JConfig() {
        seleniumHub = System.getProperty("seleniumHub");
        browserName = System.getProperty("browserName");
        teamcityBaseURL = "http://" + System.getProperty("hostname") + ":" + System.getProperty("teamcityServerPort");
    }

    public String getSeleniumHub() {
        return seleniumHub;
    }

    public String getBrowserName() {
        return browserName;
    }

    public String getTeamcityBaseURL() {
        return teamcityBaseURL;
    }
}
