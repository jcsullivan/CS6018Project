package com.lifestyleapp;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WeatherUtilities
{
    private static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static String APP_ID_QUERY = "&appid=";
    private static final String APP_ID=BuildConfig.OPENWEATHERMAPKEY;
    private static String UNITS = "&units=imperial";

    public static URL buildURLFromString(String location){
        URL queryURL = null;
        try{
            queryURL = new URL(BASE_URL + location + APP_ID_QUERY + APP_ID + UNITS);

        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return queryURL;
    }

    public static String getDataFromURL(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        try {
            InputStream inputStream = urlConnection.getInputStream();

            //The scanner trick: search for the next "beginning" of the input stream
            //No need to user BufferedReader
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }
            else{
                return null;
            }

        }finally {
            urlConnection.disconnect();
        }
    }

    public static String parseJSON(String json)
    {
        Gson gson = new Gson();

        WeatherData weatherData = gson.fromJson(json, WeatherData.class);

        String returnString = "";

        returnString = returnString + "Current Conditions: " + weatherData.weather.main + "\n";
        returnString = returnString + "Current Temperature: " + weatherData.main.temp + "\n";
        returnString = returnString + "Feels-Like Temperature: " + weatherData.main.feels_like + "\n";
        returnString = returnString + "Current Humidity: " + weatherData.main.humidity + "\n";
        returnString = returnString + "Current Pressure: " + weatherData.main.pressure + "\n";
        returnString = returnString + "Current Wind Speed: " + weatherData.wind.speed + "\n";
        returnString = returnString + "Current Wind Direction: " + weatherData.wind.deg + "\n";
        returnString = returnString + "Current Cloud Cover: " + weatherData.clouds.all + "\n";

        return returnString;
    }
}
