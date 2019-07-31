package cn.ommiao.mowidgets.utils;

import java.util.Calendar;
import java.util.HashMap;

import cn.ommiao.mowidgets.R;

public class WeatherUtil {

    public static int getIconRes(String code){
        WeatherInfo weatherInfo = weatherCodeMap.get(code);
        if(weatherInfo == null){
            return isLightDay() ? R.drawable.weather_clear_day_pixel : R.drawable.weather_clear_night_pixel;
        } else if("100".equals(code) && !isLightDay()){
            return R.drawable.weather_clear_night_pixel;
        } else if("103".equals(code) && !isLightDay()){
            return R.drawable.weather_partly_cloudy_night_pixel;
        } else {
            return weatherInfo.iconRes;
        }
    }

    private static boolean isLightDay(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour > 5 && hour < 19;
    }

    private static final HashMap<String, WeatherInfo> weatherCodeMap = new HashMap<String, WeatherInfo>(){
        {
            put("100", new WeatherInfo("100", "晴	", R.drawable.weather_clear_day_pixel));
            put("101", new WeatherInfo("101", "多云", R.drawable.weather_cloudy_pixel));
            put("102", new WeatherInfo("102", "少云", R.drawable.weather_cloudy_pixel));
            put("103", new WeatherInfo("103", "晴间多云", R.drawable.weather_partly_cloudy_day_pixel));
            put("104", new WeatherInfo("104", "阴", R.drawable.weather_cloudy_pixel));
            put("200", new WeatherInfo("200", "有风", R.drawable.weather_clear_day_pixel));
            put("201", new WeatherInfo("201", "平静", R.drawable.weather_clear_day_pixel));
            put("202", new WeatherInfo("202", "微风", R.drawable.weather_clear_day_pixel));
            put("203", new WeatherInfo("203", "和风", R.drawable.weather_clear_day_pixel));
            put("204", new WeatherInfo("204", "清风", R.drawable.weather_clear_day_pixel));
            put("205", new WeatherInfo("205", "强风/劲风", R.drawable.weather_wind_pixel));
            put("206", new WeatherInfo("206", "疾风", R.drawable.weather_wind_pixel));
            put("207", new WeatherInfo("207", "大风", R.drawable.weather_wind_pixel));
            put("208", new WeatherInfo("208", "烈风", R.drawable.weather_wind_pixel));
            put("209", new WeatherInfo("209", "风暴", R.drawable.weather_wind_pixel));
            put("210", new WeatherInfo("210", "狂爆风", R.drawable.weather_wind_pixel));
            put("211", new WeatherInfo("211", "飓风", R.drawable.weather_wind_pixel));
            put("212", new WeatherInfo("212", "龙卷风", R.drawable.weather_wind_pixel));
            put("213", new WeatherInfo("213", "热带风暴", R.drawable.weather_wind_pixel));
            put("300", new WeatherInfo("300", "阵雨", R.drawable.weather_rain_pixel));
            put("301", new WeatherInfo("301", "强阵雨", R.drawable.weather_rain_pixel));
            put("302", new WeatherInfo("302", "雷阵雨", R.drawable.weather_thunderstorm_pixel));
            put("303", new WeatherInfo("303", "强雷阵雨", R.drawable.weather_thunderstorm_pixel));
            put("304", new WeatherInfo("304", "雷阵雨伴有冰雹", R.drawable.weather_thunderstorm_pixel));
            put("305", new WeatherInfo("305", "小雨", R.drawable.weather_rain_pixel));
            put("306", new WeatherInfo("306", "中雨", R.drawable.weather_rain_pixel));
            put("307", new WeatherInfo("307", "大雨", R.drawable.weather_rain_pixel));
            put("308", new WeatherInfo("308", "极端降雨", R.drawable.weather_rain_pixel));
            put("309", new WeatherInfo("309", "毛毛雨/细雨", R.drawable.weather_rain_pixel));
            put("310", new WeatherInfo("310", "暴雨", R.drawable.weather_rain_pixel));
            put("311", new WeatherInfo("311", "大暴雨", R.drawable.weather_rain_pixel));
            put("312", new WeatherInfo("312", "特大暴雨", R.drawable.weather_rain_pixel));
            put("313", new WeatherInfo("313", "冻雨", R.drawable.weather_rain_pixel));
            put("314", new WeatherInfo("314", "小到中雨", R.drawable.weather_rain_pixel));
            put("315", new WeatherInfo("315", "中到大雨", R.drawable.weather_rain_pixel));
            put("316", new WeatherInfo("316", "大到暴雨", R.drawable.weather_rain_pixel));
            put("317", new WeatherInfo("317", "暴雨到大暴雨", R.drawable.weather_rain_pixel));
            put("318", new WeatherInfo("318", "大暴雨到特大暴雨", R.drawable.weather_rain_pixel));
            put("399", new WeatherInfo("399", "雨", R.drawable.weather_rain_pixel));
            put("400", new WeatherInfo("400", "小雪", R.drawable.weather_snow_pixel));
            put("401", new WeatherInfo("401", "中雪", R.drawable.weather_snow_pixel));
            put("402", new WeatherInfo("402", "大雪", R.drawable.weather_snow_pixel));
            put("403", new WeatherInfo("403", "暴雪", R.drawable.weather_snow_pixel));
            put("404", new WeatherInfo("404", "雨夹雪", R.drawable.weather_sleet_pixel));
            put("405", new WeatherInfo("405", "雨雪天气", R.drawable.weather_sleet_pixel));
            put("406", new WeatherInfo("406", "阵雨夹雪", R.drawable.weather_sleet_pixel));
            put("407", new WeatherInfo("407", "阵雪", R.drawable.weather_snow_pixel));
            put("408", new WeatherInfo("408", "小到中雪", R.drawable.weather_snow_pixel));
            put("409", new WeatherInfo("409", "中到大雪", R.drawable.weather_snow_pixel));
            put("410", new WeatherInfo("410", "大到暴雪", R.drawable.weather_snow_pixel));
            put("499", new WeatherInfo("499", "雪", R.drawable.weather_snow_pixel));
            put("500", new WeatherInfo("500", "薄雾", R.drawable.weather_fog_pixel));
            put("501", new WeatherInfo("501", "雾", R.drawable.weather_fog_pixel));
            put("502", new WeatherInfo("502", "霾", R.drawable.weather_fog_pixel));
            put("503", new WeatherInfo("503", "扬沙", R.drawable.weather_fog_pixel));
            put("504", new WeatherInfo("504", "浮尘", R.drawable.weather_fog_pixel));
            put("507", new WeatherInfo("507", "沙尘暴", R.drawable.weather_fog_pixel));
            put("508", new WeatherInfo("508", "强沙尘暴", R.drawable.weather_fog_pixel));
            put("509", new WeatherInfo("509", "浓雾", R.drawable.weather_fog_pixel));
            put("510", new WeatherInfo("510", "强浓雾", R.drawable.weather_fog_pixel));
            put("511", new WeatherInfo("511", "中度霾", R.drawable.weather_fog_pixel));
            put("512", new WeatherInfo("512", "重度霾", R.drawable.weather_fog_pixel));
            put("513", new WeatherInfo("513", "严重霾", R.drawable.weather_fog_pixel));
            put("514", new WeatherInfo("514", "大雾", R.drawable.weather_fog_pixel));
            put("515", new WeatherInfo("515", "特强浓雾", R.drawable.weather_fog_pixel));
            put("900", new WeatherInfo("900", "热", R.drawable.weather_clear_day_pixel));
            put("901", new WeatherInfo("901", "冷", R.drawable.weather_clear_day_pixel));
            put("999", new WeatherInfo("999", "未知", R.drawable.weather_clear_day_pixel));
        }
    };
    private static class WeatherInfo{
        private String code;
        private String condTxt;
        private int iconRes;

        public WeatherInfo(String code, String condTxt, int iconRes) {
            this.code = code;
            this.condTxt = condTxt;
            this.iconRes = iconRes;
        }
    }

}
