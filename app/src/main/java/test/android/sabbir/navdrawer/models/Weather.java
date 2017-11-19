package test.android.sabbir.navdrawer.models;

/**
 * Created by Sabbir on 09,February,2017
 * mPower Social
 * Dhaka
 */
public class Weather {
    public static final Weather EMPTY_WEATHER = new Weather(null,null,null,null,null,null,null,null,null);
   private String city,detail,currentTemp,humidity,pressure,sunrise,sunset,rain,wind,upDateOn,icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUpDateOn() {
        return upDateOn;
    }

    public void setUpDateOn(String upDateOn) {
        this.upDateOn = upDateOn;
    }

    public Weather(){}

    public Weather(String city, String detail, String currentTemp, String humidity, String pressure, String sunrise, String sunset, String rain, String wind) {
        this.city = city;
        this.detail = detail;
        this.currentTemp = currentTemp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.rain = rain;
        this.wind = wind;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
