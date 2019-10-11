package cn.ommiao.mowidgets.requesters;

import android.appwidget.AppWidgetManager;
import android.content.Context;

import com.orhanobut.logger.Logger;

import cn.ommiao.mowidgets.Constant;
import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.entities.NowWeather;
import cn.ommiao.mowidgets.httpcalls.weather.WeatherNowCall;
import cn.ommiao.mowidgets.httpcalls.weather.model.WeatherIn;
import cn.ommiao.mowidgets.httpcalls.weather.model.WeatherOut;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.BaseWidget;
import cn.ommiao.network.SimpleRequestCallback;

public abstract class WeatherNowRequester<W extends BaseWidget> extends BaseRequester<W> {

    protected static final String INVALID_KEY = "Invalid Key.";

    public WeatherNowRequester(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        super(context, appWidgetManager, widgetId);
    }

    @Override
    public void request() {
        String location = getLocation();
        String key = getWeatherKey();
        if(INVALID_KEY.equals(key)){
            //ToastUtil.shortToast("请设置和风天气的Key以获取天气");
            notifyDataRequested();
            return;
        }
        if(context.getString(R.string.key_secret).equals(key)){
            key = Constant.WEATHER_KEY;
        }
        WeatherIn in = new WeatherIn(location, key);
        newCall(new WeatherNowCall(), in, new SimpleRequestCallback<WeatherOut>() {
            @Override
            public void onSuccess(WeatherOut out) {
                saveWeatherData(out.getNowWeather());
                notifyDataRequested();
            }

            @Override
            public void onError(WeatherOut out) {
                String msg = "数据获取异常，请检查网络！";
                if(out != null){
                    msg = out.getStatus();
                }
                Logger.d("天气获取失败：" + msg);
                if("unknown location".equals(msg)){
                    ToastUtil.shortToast("天气获取失败，请确认地点准确且精确" );
                }
                notifyDataRequested();
            }
        });
    }

    protected abstract String getLocation();

    protected abstract String getWeatherKey();

    protected abstract void saveWeatherData(NowWeather nowWeather);

}
