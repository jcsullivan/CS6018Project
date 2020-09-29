package com.lifestyleapp;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.lifestyleapp.WeatherUtilities.buildURLFromString;
import static com.lifestyleapp.WeatherUtilities.getDataFromURL;
import static com.lifestyleapp.WeatherUtilities.parseJSON;
import static org.junit.Assert.*;

public class WeatherUtilitiesTests {


    @Test
    public void getDataFromURLTest() throws IOException {
        assertNotNull(getDataFromURL(buildURLFromString("Denver,usa")));
    }

    @Test
    public void parseJSONTest() throws IOException {
        String data = getDataFromURL(buildURLFromString("Denver,usa"));
        String parsed = parseJSON(data);
        int numLines = parsed.split("\\n").length;
        assertEquals(numLines,8);
    }
}