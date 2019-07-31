package cn.ommiao.mowidgets.requesters;

import android.appwidget.AppWidgetManager;
import android.content.Context;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.entities.NowWeather;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.widgets.ColorosClockWidget;

public class ColorosClockRequester extends WeatherNowRequester<ColorosClockWidget> {

    public ColorosClockRequester(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        super(context, appWidgetManager, widgetId);
    }

    @Override
    protected ColorosClockWidget getTargetWidget() {
        return new ColorosClockWidget();
    }

    @Override
    protected String getLocation() {
        return SPUtil.getString(context.getString(R.string.label_coloros_clock) + widgetId + "_location", "北京");
    }

    @Override
    protected String getWeatherKey() {
        return SPUtil.getString(context.getString(R.string.label_coloros_clock) + widgetId + "_key", INVALID_KEY);
    }

    @Override
    protected void saveWeatherData(NowWeather nowWeather) {
        SPUtil.put(context.getString(R.string.label_coloros_clock) + widgetId + "_tmp", nowWeather.getTmp());
        SPUtil.put(context.getString(R.string.label_coloros_clock) + widgetId + "_con_txt", nowWeather.getCond_txt());
    }

}
