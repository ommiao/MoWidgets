package cn.ommiao.mowidgets.entities;

import cn.ommiao.bean.JavaBean;

public class MonthCalendarDay extends JavaBean {

    public enum ViewType {
        WEEK_ROW, NONE, DAY, WEEK_COL
    }

    private ViewType viewType = ViewType.DAY;

    private String data;

    private boolean highLight;

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isHighLight() {
        return highLight;
    }

    public void setHighLight(boolean highLight) {
        this.highLight = highLight;
    }
}
