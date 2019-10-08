package cn.ommiao.mowidgets.httpcalls.weather.model;

import cn.ommiao.network.RequestInBase;

public class WeatherIn extends RequestInBase {

    private String location;
    private String key;

    public WeatherIn(String location, String key) {
        this.location = location;
        this.key = key;
    }
}
