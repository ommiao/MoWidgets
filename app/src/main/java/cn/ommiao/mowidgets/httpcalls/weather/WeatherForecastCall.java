package cn.ommiao.mowidgets.httpcalls.weather;

public class WeatherForecastCall extends BaseWeatherCall {

    @Override
    protected String key() {
        return "forecast";
    }
}
