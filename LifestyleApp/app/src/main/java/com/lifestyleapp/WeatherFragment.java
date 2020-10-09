package com.lifestyleapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class WeatherFragment extends Fragment implements View.OnClickListener
{
    private EditText editLocation;
    private Button buttonLocation, buttonLifestyle;
    private String localLocation;
    private TextView weatherInfo;

    private static final String ARG_CITY = "";
    private static final String ARG_COUNTRY = "";

    private WeatherViewModel mWeatherViewModel;

    ProfilePageFragment.OnLifePressListener lifePressListener;

    public interface OnLifePressListener {
        public void onLifeBtnPress();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            lifePressListener = (ProfilePageFragment.OnLifePressListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnLifePressListener");
        }
    }

    public WeatherFragment()
    {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance(String city, String country)
    {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        args.putString(ARG_COUNTRY, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        editLocation = view.findViewById(R.id.location);
        buttonLocation = (Button)view.findViewById(R.id.resetLocation);
        buttonLifestyle = (Button)view.findViewById(R.id.backToLifestyle);
        weatherInfo = view.findViewById(R.id.weatherInfo);

        buttonLocation.setOnClickListener(this);
        buttonLifestyle.setOnClickListener(this);

        //Create the view model
        mWeatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);

        //Set the observer
        mWeatherViewModel.getData().observe(this, weatherObserver);

        return view;
    }

    final Observer<WeatherData> weatherObserver  = new Observer<WeatherData>() {
        @Override
        public void onChanged(@Nullable final WeatherData weatherData) {
            // Update the UI if this data variable changes
            if(weatherData!=null)
            {
                if(weatherData.weather[0].main.equals("ERROR"))
                {
                    weatherInfo.setText("Please enter a location in '[City], [Country Abbreviation] format.");
                }
                else
                {
                    String weatherString = "";

                    weatherString = weatherString + "Current Conditions: " + weatherData.weather[0].main + "\n";
                    weatherString = weatherString + "Current Temperature: " + weatherData.main.temp + "°\n";
                    weatherString = weatherString + "Feels-Like Temperature: " + weatherData.main.feels_like + "°\n";
                    weatherString = weatherString + "Current Humidity: " + weatherData.main.humidity + "%\n";
                    weatherString = weatherString + "Current Pressure: " + weatherData.main.pressure + "mbar\n";
                    weatherString = weatherString + "Current Wind Speed: " + weatherData.wind.speed + "mph\n";
                    weatherString = weatherString + "Current Wind Direction: " + weatherData.wind.deg + "°\n";
                    weatherString = weatherString + "Current Cloud Cover: " + weatherData.clouds.all + "%\n";

                    weatherInfo.setText(weatherString);
                }
            }
        }
    };

    @Override
    //public void onViewCreated(View view, Bundle savedInstanceState)
    public void onStart()
    {
        super.onStart();
        if(!UserKt.getDefaultUser().getCity().isEmpty() && !UserKt.getDefaultUser().getCountry().isEmpty())
        {
            editLocation.setText(UserKt.getDefaultUser().getCity() + ", " + UserKt.getDefaultUser().getCountry());
            localLocation = editLocation.getText().toString();
            String locationForURL = localLocation.replaceAll(",\\s+", ",").trim();
            locationForURL = locationForURL.replaceAll("\\s+", "%20").trim();

            loadWeatherData(locationForURL);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetLocation:
            {

                localLocation = editLocation.getText().toString();
                String locationForURL = localLocation.replaceAll(",\\s+", ",").trim();
                locationForURL = locationForURL.replaceAll("\\s+", "%20").trim();

                loadWeatherData(locationForURL);
                break;
            }
            case R.id.backToLifestyle:
            {
                lifePressListener.onLifeBtnPress();
                break;
            }
        }
    }

    void loadWeatherData(String location){
        //pass the location in to the view model
        mWeatherViewModel.setLocation(location);
    }

//    private class fetchWeatherStuff extends AsyncTask<String,Void,String>
//    {
//        @Override
//        protected String doInBackground(String... inputStringArray)
//        {
//            String location = inputStringArray[0];
//            URL weatherDataURL = WeatherUtilities.buildURLFromString(location);
//            String jsonWeatherData = null;
//            try{
//                jsonWeatherData = WeatherUtilities.getDataFromURL(weatherDataURL);
//                return jsonWeatherData;
//            }catch(Exception e){
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String jsonWeatherData)
//        {
//            if (jsonWeatherData!=null){
//                String weatherToShow = WeatherUtilities.parseJSON(jsonWeatherData);
//
//                weatherInfo.setText(weatherToShow);
//            }
//        }
//    }
}