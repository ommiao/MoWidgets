package cn.ommiao.mowidgets.entities;

import java.util.ArrayList;

import cn.ommiao.bean.JavaBean;

public class HeWeather6 extends JavaBean {

    private String status;

    private NowWeather now;

    private ArrayList<DailyForecast> daily_forecast;

    public NowWeather getNow() {
        return now;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<DailyForecast> getDaily_forecast() {
        return daily_forecast;
    }
}
