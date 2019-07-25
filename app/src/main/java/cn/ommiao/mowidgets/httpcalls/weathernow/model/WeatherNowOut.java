package cn.ommiao.mowidgets.httpcalls.weathernow.model;

import java.util.ArrayList;

import cn.ommiao.mowidgets.entities.HeWeather6;
import cn.ommiao.mowidgets.entities.NowWeather;
import cn.ommiao.network.RequestOutBase;

public class WeatherNowOut extends RequestOutBase {

    private ArrayList<HeWeather6> HeWeather6;

    public NowWeather getNowWeather(){
        return HeWeather6.get(0).getNow();
    }

}
