package com.lgs.eden.api;

import java.util.ArrayList;
import java.util.List;

public class Api {

    private static String apiVersion;

    public Api(){
        apiVersion = "1.0";
    }

    public static String getApiVersion() {
        return apiVersion;
    }

}
