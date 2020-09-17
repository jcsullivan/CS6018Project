package com.lifestyleapp;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherUtilities
{
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String APP_ID_QUERY = "&app_id=";
    private static final String APP_ID="bb0621113ef205977446e632e5858ce0";

    public static URL buildURLFromString(String location){
        URL queryURL = null;
        try{
            queryURL = new URL(BASE_URL + location + APP_ID_QUERY + APP_ID);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return queryURL;
    }
}
