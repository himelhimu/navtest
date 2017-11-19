package test.android.sabbir.navdrawer.weather;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import test.android.sabbir.navdrawer.models.Weather;

/**
 * Created by Sabbir on 09,February,2017
 * mPower Social
 * Dhaka
 */
public class WeatherTask extends AsyncTask<Void,Void,Weather> {


    public interface WeatherDataListener{
        void onWeatherUpdate(Weather weather);
    }

    private WeatherDataListener listener;
    private Context mContext;
    private static final String OPEN_WEATHER_MAP_URL_OLD =
            "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";
    private String mLatitude,mLongitude;

   public WeatherTask(Context context,HashMap<String,String> dataMap,WeatherDataListener dataListener){
       mContext=context;
       this.listener=dataListener;
       mLatitude=dataMap.get("lat");
       mLongitude=dataMap.get("lan");
    }


    @Override
    protected Weather doInBackground(Void... voids) {
        try {
            return getWeatherDataFromJson(requestServerforData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Weather weather) {
        super.onPostExecute(weather);
        listener.onWeatherUpdate(weather);
    }

    private static String setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = "&#xf00d;";
            } else {
                icon = "&#xf02e;";
            }
        } else {
            switch(id) {
                case 2 : icon = "&#xf01e;";
                    break;
                case 3 : icon = "&#xf01c;";
                    break;
                case 7 : icon = "&#xf014;";
                    break;
                case 8 : icon = "&#xf013;";
                    break;
                case 6 : icon = "&#xf01b;";
                    break;
                case 5 : icon = "&#xf019;";
                    break;
            }
        }
       // Log.i("ICON TEXT",icon);
        return icon;
    }

    static Weather getWeatherDataFromJson(JSONObject weatherJsonData) {

        Log.i("weather json",weatherJsonData.toString());
        Weather weather;
        Gson gson;
        try {
            JSONObject weatherDetails=weatherJsonData.getJSONArray("weather").getJSONObject(0);
            JSONObject main=weatherJsonData.getJSONObject("main");
            JSONObject sys=weatherJsonData.getJSONObject("sys");
            DateFormat df = DateFormat.getDateTimeInstance();

            weather=new Weather();
            weather.setDetail(weatherDetails.getString("description").toUpperCase());
            weather.setCity(weatherJsonData.getString("name").toUpperCase());
            weather.setCurrentTemp(String.format("%.2f",main.getDouble("temp"))+"°");
            String updatedOn = df.format(new Date(weatherJsonData.getLong("dt")*1000));
            weather.setUpDateOn(updatedOn);
            weather.setSunrise(String.valueOf(sys.getLong("sunrise")*1000));
            weather.setSunset(String.valueOf(sys.getLong("sunset")*1000));
            weather.setHumidity(main.getString("humidity") + "%") ;
            weather.setPressure(main.getString("pressure") + " hPa");
           // weather.setRain(weatherJsonData.getString("rain"));

            weather.setIcon(setWeatherIcon(weatherDetails.getInt("id"),
                    weatherJsonData.getJSONObject("sys").getLong("sunrise") * 1000,
                    weatherJsonData.getJSONObject("sys").getLong("sunset") * 1000));


            return weather;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Weather.EMPTY_WEATHER;

    }

    private JSONObject  requestServerforData() throws IOException {
        String urlLastPart="&units=metric";
        String surl=WeatherConfig.OPEN_WEATHER_MAP_URL+"lat="+mLatitude+"&"+"lon="+mLongitude+urlLastPart+"&APPID="+WeatherConfig.OPENWEATHERMAP_API_KEY;
       // URL url=new URL(String.format(OPEN_WEATHER_MAP_URL_OLD,mLatitude,mLongitude));
        URL url=new URL(surl);
        //Log.i("url",url.toString());

        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();

        //Log.i("response",""+httpURLConnection.getResponseCode());

        httpURLConnection.addRequestProperty("APPID",WeatherConfig.OPENWEATHERMAP_API_KEY);

        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        StringBuilder stringBuilder=new StringBuilder();
        String tempJson;
        while ((tempJson=bufferedReader.readLine())!=null){
            stringBuilder.append(tempJson);
        }
        bufferedReader.close();

        String jsonString=stringBuilder.toString();
        JSONObject weatherObject=null;
        try {
            weatherObject  =new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherObject;
    }

}
