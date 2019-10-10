package cn.ommiao.mowidgets.httpcalls.weather;

public class AirNowCall extends BaseWeatherCall {

    @Override
    protected String key() {
        return "air/now";
    }

}
