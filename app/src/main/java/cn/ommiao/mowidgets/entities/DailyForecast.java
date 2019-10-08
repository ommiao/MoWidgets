package cn.ommiao.mowidgets.entities;

import cn.ommiao.bean.JavaBean;

public class DailyForecast extends JavaBean {

    private String date;
    private String cond_txt_d, cond_txt_n;
    private String tmp_max, tmp_min;
    private String wind_sc, wind_dir;

    public String getDate() {
        return date;
    }

    public String getCond_txt_d() {
        return cond_txt_d;
    }

    public String getCond_txt_n() {
        return cond_txt_n;
    }

    public String getTmp_max() {
        return tmp_max;
    }

    public String getTmp_min() {
        return tmp_min;
    }

    public String getWind_sc() {
        return wind_sc;
    }

    public String getWind_dir() {
        return wind_dir;
    }

}
