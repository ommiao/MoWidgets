package cn.ommiao.mowidgets.entities;

import cn.ommiao.bean.JavaBean;

public class HeWeather6 extends JavaBean {

    private String status;

    private NowWeather now;

    public NowWeather getNow() {
        return now;
    }

    public String getStatus() {
        return status;
    }
}
