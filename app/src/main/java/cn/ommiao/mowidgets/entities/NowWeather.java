package cn.ommiao.mowidgets.entities;

import cn.ommiao.bean.JavaBean;

public class NowWeather extends JavaBean {

    private String cond_code;
    private String cond_txt;
    private String tmp;
    private String wind_dir, wind_sc;
    private String hum;

    public String getCond_code() {
        return cond_code;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public String getTmp() {
        return tmp;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public String getWind_sc() {
        return wind_sc;
    }

    public String getHum() {
        return hum;
    }
}
