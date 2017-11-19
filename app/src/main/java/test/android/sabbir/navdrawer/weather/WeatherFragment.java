package test.android.sabbir.navdrawer.weather;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.fragments.BaseFragment;
import test.android.sabbir.navdrawer.models.Weather;
import test.android.sabbir.navdrawer.utilites.Helper;


/**
 * @author sabbir ,sabbir@mpower-social.com
 * */
public class WeatherFragment extends BaseFragment implements WeatherTask.WeatherDataListener{
    private TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field,weatherIcon, updatedField;
    String lat="",lon="";

    @Override
    protected int getFragmentLayout() {
        return R.layout.activity_weather;
    }

    @Override
    protected void onViewReady(View view, @Nullable Bundle savedInstanceState) {
        Typeface weatherFace = Typeface.createFromAsset(getActivity().getAssets(), "weathericons-regular-webfont.ttf");

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...Please Wait");
        progressDialog.setCancelable(false);

        cityField=(TextView) view.findViewById(R.id.city_field);
        detailsField=(TextView) view.findViewById(R.id.details_field);
        currentTemperatureField=(TextView) view.findViewById(R.id.current_temperature_field);
        humidity_field=(TextView) view.findViewById(R.id.humidity_field);
        pressure_field=(TextView) view.findViewById(R.id.pressure_field);
        weatherIcon=(TextView) view.findViewById(R.id.weather_icon);
        updatedField=(TextView) view.findViewById(R.id.updated_field);

        weatherIcon.setTypeface(weatherFace);

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        String lat=preferences.getString("lat","");
        String lon=preferences.getString("lan","");
        //  lat=preferences.getString("latitude","");
        //lon=preferences.getString("longitude","");
        if (!lat.equalsIgnoreCase("") && !lon.equalsIgnoreCase("") && !lat.equalsIgnoreCase("0.0") && !lon.equalsIgnoreCase("0.0") && Helper.isConnected(getContext())){
           // new SetWeatherData().execute();
            HashMap<String ,String > map=new HashMap<>();
            map.put("lat",lat);
            map.put("lan",lon);
            WeatherTask weatherTask=new WeatherTask(getContext(),map,this);
            weatherTask.execute();
        }else {
            Toast.makeText(getContext(),"Internet not available or weather data not available",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onWeatherUpdate(Weather weather) {
        String s=weather.getCity();
        String city=s.equalsIgnoreCase("Sāmāir") ? "Dhaka":s;
        //Log.i("city",city);
        cityField.setText(city);
        detailsField.setText(weather.getDetail());
        currentTemperatureField.setText(weather.getCurrentTemp());
        humidity_field.setText(weather.getHumidity());
        pressure_field.setText(weather.getPressure());
        updatedField.setText(weather.getUpDateOn());
        //detailsField.setText(weather.getDetail());
        Log.i("icon",weather.getIcon());
        weatherIcon.setText(Html.fromHtml(weather.getIcon()));
    }

}
