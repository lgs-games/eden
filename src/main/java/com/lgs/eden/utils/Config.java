package com.lgs.eden.utils;

import com.lgs.eden.api.Api;

public class Config {

    private static String version; // TODO: change version type to EdenVersion
    private static String username;
    private static String language;

    public Config() {
        version = "1.0";
        username = "Raphik";
        language = "en";
        checkClientVersion(version);
    }

    /**
     * Checks if there is a new version of the application
     * @param current the current version of the app
     *
     * @return true if the client needs an update, false instead
     */
    public boolean checkClientVersion(String current) {
        System.out.println("Checking client version...");
        boolean test = !current.equals(Api.getApiVersion());
        System.out.println(test ? "Client needs an update" : "Client is up to date");
        return test;
    }


}
