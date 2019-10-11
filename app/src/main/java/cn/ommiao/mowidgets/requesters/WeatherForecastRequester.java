package cn.ommiao.mowidgets.requesters;

import android.appwidget.AppWidgetManager;
import android.content.Context;

import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.ommiao.mowidgets.Constant;
import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.entities.HeWeather6;
import cn.ommiao.mowidgets.httpcalls.weather.WeatherForecastCall;
import cn.ommiao.mowidgets.httpcalls.weather.model.WeatherIn;
import cn.ommiao.mowidgets.httpcalls.weather.model.WeatherOut;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.widgets.list.WeatherForecastWidget;
import cn.ommiao.network.SimpleRequestCallback;

public class WeatherForecastRequester extends BaseRequester<WeatherForecastWidget> {

    protected static final String INVALID_KEY = "Invalid Key.";

    public WeatherForecastRequester(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        super(context, appWidgetManager, widgetId);
    }

    @Override
    protected WeatherForecastWidget getTargetWidget() {
        return new WeatherForecastWidget();
    }

    @Override
    public void request() {
        String location = SPUtil.getString(context.getString(R.string.label_weather_forecast) + widgetId + "_location", "北京");
        String key = SPUtil.getString(context.getString(R.string.label_weather_forecast) + widgetId + "_key", INVALID_KEY);
        if(INVALID_KEY.equals(key)){
            //ToastUtil.shortToast("请设置和风天气的Key以获取天气");
            notifyDataRequested();
            return;
        }
        if(context.getString(R.string.key_secret).equals(key)){
            key = Constant.WEATHER_KEY;
        }
        WeatherIn in = new WeatherIn(location, key);
        newCall(new WeatherForecastCall(), in, new SimpleRequestCallback<WeatherOut>() {
            @Override
            public void onSuccess(WeatherOut out) {
                saveWeatherData(out);
                notifyDataRequested();
            }

            @Override
            public void onError(WeatherOut out) {
                String msg = "数据获取异常，请检查网络！";
                if(out != null){
                    msg = out.getStatus();
                }
                Logger.d("天气获取失败：" + msg);
                notifyDataRequested();
            }
        });
    }

    private void saveWeatherData(WeatherOut out) {
        HeWeather6 heWeather6 = out.getHeWeather6();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        heWeather6.setUpdateTime(formatter.format(new Date()));
        String data = heWeather6.toJson();
        Logger.d("set: " + widgetId + " forecast --->" + data);
        SPUtil.put(context.getString(R.string.label_weather_forecast) + widgetId + "_heweather6", data);
    }
}
