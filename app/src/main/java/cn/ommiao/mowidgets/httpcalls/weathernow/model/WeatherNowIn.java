package cn.ommiao.mowidgets.httpcalls.weathernow.model;

import cn.ommiao.mowidgets.Constant;
import cn.ommiao.network.RequestInBase;

public class WeatherNowIn extends RequestInBase {

    private String location;
    private String key = Constant.WEATHER_KEY;

    public WeatherNowIn(String location) {
        this.location = location;
    }
}
