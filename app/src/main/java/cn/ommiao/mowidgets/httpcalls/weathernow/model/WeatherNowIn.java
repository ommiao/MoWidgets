package cn.ommiao.mowidgets.httpcalls.weathernow.model;

import cn.ommiao.mowidgets.Constant;
import cn.ommiao.network.RequestInBase;

public class WeatherNowIn extends RequestInBase {

    private String location;
    private String key;

    public WeatherNowIn(String location, String key) {
        this.location = location;
        this.key = key;
    }
}
