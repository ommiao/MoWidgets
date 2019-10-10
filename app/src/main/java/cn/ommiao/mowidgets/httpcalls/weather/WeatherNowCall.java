package cn.ommiao.mowidgets.httpcalls.weather;

public class WeatherNowCall extends BaseWeatherCall {

    @Override
    protected String key() {
        return "weather/now";
    }

}
