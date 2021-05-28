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
        checkVersion(version);
    }


    public void checkVersion(String current) {
        boolean test = current.equals(Api.getApiVersion());
        System.out.println(test ? "Client up to date" : "Client needs an update");
    }


}
