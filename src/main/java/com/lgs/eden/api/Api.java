package com.lgs.eden.api;


public class Api {

    private static String apiVersion;

    public Api(){
        apiVersion = "1.0";
    }

    public static String getApiVersion() { return apiVersion; }

}
