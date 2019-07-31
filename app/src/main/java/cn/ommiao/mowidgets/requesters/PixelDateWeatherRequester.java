package cn.ommiao.mowidgets.requesters;

import android.appwidget.AppWidgetManager;
import android.content.Context;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.entities.NowWeather;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.widgets.PixelDateWeatherWidget;

public class PixelDateWeatherRequester extends WeatherNowRequester<PixelDateWeatherWidget> {

    public PixelDateWeatherRequester(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        super(context, appWidgetManager, widgetId);
    }

    @Override
    protected String getLocation() {
        return SPUtil.getString(context.getString(R.string.label_pixel_date_weather) + widgetId + "_location", "北京");
    }

    @Override
    protected String getWeatherKey() {
        return SPUtil.getString(context.getString(R.string.label_pixel_date_weather) + widgetId + "_key", INVALID_KEY);
    }

    @Override
    protected void saveWeatherData(NowWeather nowWeather) {
        SPUtil.put(context.getString(R.string.label_pixel_date_weather) + widgetId + "_tmp", nowWeather.getTmp());
        SPUtil.put(context.getString(R.string.label_pixel_date_weather) + widgetId + "_cond_code", nowWeather.getCond_code());
    }

    @Override
    protected PixelDateWeatherWidget getTargetWidget() {
        return new PixelDateWeatherWidget();
    }

}
