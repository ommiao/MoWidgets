package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.httpcalls.weathernow.WeatherNowCall;
import cn.ommiao.mowidgets.httpcalls.weathernow.model.WeatherNowIn;
import cn.ommiao.mowidgets.httpcalls.weathernow.model.WeatherNowOut;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.network.SimpleRequestCallback;

public class ColorosClockWidget extends BaseWidget {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        String location = SPUtil.getString(context.getString(R.string.label_coloros_clock) + appWidgetId + "_location", "北京");
        WeatherNowIn in = new WeatherNowIn(location);
        if(needRequestData()){
            setNeedRequestData(false);
            newCall(new WeatherNowCall(), in, new SimpleRequestCallback<WeatherNowOut>() {
                @Override
                public void onSuccess(WeatherNowOut out) {
                    SPUtil.put(context.getString(R.string.label_coloros_clock) + appWidgetId + "_tmp", out.getNowWeather().getTmp());
                    SPUtil.put(context.getString(R.string.label_coloros_clock) + appWidgetId + "_con_txt", out.getNowWeather().getCon_txt());
                    updateAppWidget(context, appWidgetManager, appWidgetId);
                }

                @Override
                public void onError(int code, String error) {
                    ToastUtil.shortToast("天气获取失败：" + error);
                }
            });
        }
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock_coloros);
        String tmp = SPUtil.getString(context.getString(R.string.label_coloros_clock) + appWidgetId + "_tmp", "26");
        String conTxt = SPUtil.getString(context.getString(R.string.label_coloros_clock) + appWidgetId + "_con_txt", "晴");
        String weatherStr = conTxt + " " + tmp + "℃";
        views.setTextViewText(R.id.tv_weather, weatherStr);
        return views;
    }
}
