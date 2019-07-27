package cn.ommiao.mowidgets.requesters;

import android.appwidget.AppWidgetManager;
import android.content.Context;

import cn.ommiao.mowidgets.Constant;
import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.httpcalls.weathernow.WeatherNowCall;
import cn.ommiao.mowidgets.httpcalls.weathernow.model.WeatherNowIn;
import cn.ommiao.mowidgets.httpcalls.weathernow.model.WeatherNowOut;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.ColorosClockWidget;
import cn.ommiao.network.SimpleRequestCallback;

public class ColorosClockRequester extends BaseRequester<ColorosClockWidget> {

    private static final String INVALID_KEY = "Invalid Key.";

    public ColorosClockRequester(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        super(context, appWidgetManager, widgetId);
    }

    @Override
    protected ColorosClockWidget getTargetWidget() {
        return new ColorosClockWidget();
    }

    @Override
    public void request() {
        String location = SPUtil.getString(context.getString(R.string.label_coloros_clock) + widgetId + "_location", "北京");
        String key = SPUtil.getString(context.getString(R.string.label_coloros_clock) + widgetId + "_key", INVALID_KEY);
        if(INVALID_KEY.equals(key)){
            ToastUtil.shortToast("请设置和风天气的Key以获取天气");
            notifyDataRequested();
            return;
        }
        if(context.getString(R.string.key_secret).equals(key)){
            key = Constant.WEATHER_KEY;
        }
        WeatherNowIn in = new WeatherNowIn(location, key);
        newCall(new WeatherNowCall(), in, new SimpleRequestCallback<WeatherNowOut>() {
            @Override
            public void onSuccess(WeatherNowOut out) {
                SPUtil.put(context.getString(R.string.label_coloros_clock) + widgetId + "_tmp", out.getNowWeather().getTmp());
                SPUtil.put(context.getString(R.string.label_coloros_clock) + widgetId + "_con_txt", out.getNowWeather().getCon_txt());
                notifyDataRequested();
            }

            @Override
            public void onError(WeatherNowOut out) {
                ToastUtil.shortToast("天气获取失败：" + out.getStatus());
                notifyDataRequested();
            }
        });
    }


}
