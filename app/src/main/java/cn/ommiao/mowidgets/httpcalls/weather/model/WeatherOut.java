package cn.ommiao.mowidgets.httpcalls.weather.model;

import java.util.ArrayList;

import cn.ommiao.mowidgets.entities.HeWeather6;
import cn.ommiao.mowidgets.entities.NowWeather;
import cn.ommiao.network.RequestOutBase;

public class WeatherOut extends RequestOutBase {

    private ArrayList<HeWeather6> HeWeather6;

    public HeWeather6 getHeWeather6(){
        return HeWeather6.get(0);
    }

    public NowWeather getNowWeather(){
        return HeWeather6.get(0).getNow();
    }

    public String getStatus(){
        return HeWeather6.get(0).getStatus();
    }

}
