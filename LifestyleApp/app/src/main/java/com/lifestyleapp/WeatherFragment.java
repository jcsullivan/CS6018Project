package com.lifestyleapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment implements View.OnClickListener
{
    private EditText editLocation;
    private Button buttonLocation, buttonLifestyle;
    private String localLocation;
    private TextView weatherInfo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CITY = "";
    private static final String ARG_COUNTRY = "";

    // TODO: Rename and change types of parameters
    private String mCity;
    private String mCountry;
    private User currentUser;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param city Parameter 1.
     * @param country Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        currentUser = UserKt.getDefaultUser();

        mCity = currentUser.getCity();
        mCountry = currentUser.getCountry();

        editLocation = view.findViewById(R.id.location);
        buttonLocation = (Button)view.findViewById(R.id.resetLocation);
        buttonLifestyle = (Button)view.findViewById(R.id.backToLifestyle);
        weatherInfo = view.findViewById(R.id.weatherInfo);

        buttonLocation.setOnClickListener(this);
        buttonLifestyle.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        String test = UserKt.getDefaultUser().getCity();
        if(!UserKt.getDefaultUser().getCity().isEmpty() && !UserKt.getDefaultUser().getCountry().isEmpty())
        {
            editLocation.setText(UserKt.getDefaultUser().getCity() + ", " + UserKt.getDefaultUser().getCountry());
            localLocation = editLocation.getText().toString();
            String locationForURL = localLocation.replaceAll(",\\s+", ",").trim();
            locationForURL = locationForURL.replaceAll("\\s+", "%20").trim();

            new fetchWeatherStuff().execute(locationForURL);
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

                new fetchWeatherStuff().execute(locationForURL);
                break;
            }
            case R.id.backToLifestyle:
            {
                lifePressListener.onLifeBtnPress();
                break;
            }
        }
    }

    private class fetchWeatherStuff extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... inputStringArray)
        {
            String location = inputStringArray[0];
            URL weatherDataURL = WeatherUtilities.buildURLFromString(location);
            String jsonWeatherData = null;
            try{
                jsonWeatherData = WeatherUtilities.getDataFromURL(weatherDataURL);
                return jsonWeatherData;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String jsonWeatherData)
        {
            if (jsonWeatherData!=null){
                String weatherToShow = WeatherUtilities.parseJSON(jsonWeatherData);

                weatherInfo.setText(weatherToShow);
            }
        }
    }
}