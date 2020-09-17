package com.lifestyleapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment implements View.OnClickListener
{
    private EditText editLocation;
    private Button buttonLocation;
    private String localLocation;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CITY = "param1";
    private static final String ARG_COUNTRY = "param2";

    // TODO: Rename and change types of parameters
    private String mCity;
    private String mCountry;

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
        if (getArguments() != null)
        {
            mCity = getArguments().getString(ARG_CITY);
            mCountry = getArguments().getString(ARG_COUNTRY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        editLocation = view.findViewById(R.id.location);
        buttonLocation = (Button)view.findViewById(R.id.resetLocation);

        localLocation = mCity + ", " + mCountry;

        editLocation.setText(localLocation);

        buttonLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetLocation:
            {
                if (!editLocation.getText().toString().isEmpty())
                {
                    localLocation = editLocation.getText().toString();
                }
            }
            break;
        }
    }
}