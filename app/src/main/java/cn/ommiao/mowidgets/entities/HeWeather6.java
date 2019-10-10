package cn.ommiao.mowidgets.entities;

import java.util.ArrayList;

import cn.ommiao.bean.JavaBean;

public class HeWeather6 extends JavaBean {

    private String status;

    private Location basic;

    private NowWeather now;

    private ArrayList<DailyForecast> daily_forecast;

    private ArrayList<LifeStyle> lifestyle;

    private LifeStyle firstLifestyle;

    private AirNow air_now_city;

    private String updateTime = "2019-05-20 13:14";

    public NowWeather getNow() {
        return now;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<DailyForecast> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(ArrayList<DailyForecast> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public Location getBasic() {
        return basic;
    }

    public void setBasic(Location basic) {
        this.basic = basic;
    }

    public void setNow(NowWeather now) {
        this.now = now;
    }

    public ArrayList<LifeStyle> getLifestyle() {
        return lifestyle;
    }

    public void setFirstLifestyle(LifeStyle firstLifestyle) {
        this.firstLifestyle = firstLifestyle;
    }

    public LifeStyle getFirstLifestyle() {
        return firstLifestyle;
    }

    public AirNow getAir_now_city() {
        return air_now_city;
    }

    public void setAir_now_city(AirNow air_now_city) {
        this.air_now_city = air_now_city;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
