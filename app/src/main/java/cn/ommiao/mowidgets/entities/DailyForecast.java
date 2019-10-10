package cn.ommiao.mowidgets.entities;

import cn.ommiao.bean.JavaBean;

public class DailyForecast extends JavaBean {

    private String date;
    private String cond_txt_d, cond_txt_n;
    private String cond_code_d, cond_code_n;
    private String tmp_max, tmp_min;
    private String wind_sc, wind_dir;
    private String sr, ss;
    private String uv_index;
    private String colorCard = "#ff0000", colorText = "#ffffff";

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

    public void setDate(String date) {
        this.date = date;
    }

    public void setCond_txt_d(String cond_txt_d) {
        this.cond_txt_d = cond_txt_d;
    }

    public void setCond_txt_n(String cond_txt_n) {
        this.cond_txt_n = cond_txt_n;
    }

    public void setTmp_max(String tmp_max) {
        this.tmp_max = tmp_max;
    }

    public void setTmp_min(String tmp_min) {
        this.tmp_min = tmp_min;
    }

    public void setWind_sc(String wind_sc) {
        this.wind_sc = wind_sc;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getColorCard() {
        return colorCard;
    }

    public void setColorCard(String colorCard) {
        this.colorCard = colorCard;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public String getCond_code_d() {
        return cond_code_d;
    }

    public void setCond_code_d(String cond_code_d) {
        this.cond_code_d = cond_code_d;
    }

    public String getCond_code_n() {
        return cond_code_n;
    }

    public void setCond_code_n(String cond_code_n) {
        this.cond_code_n = cond_code_n;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getUv_index() {
        return uv_index;
    }
}
