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
import cn.ommiao.mowidgets.httpcalls.weather.AirNowCall;
import cn.ommiao.mowidgets.httpcalls.weather.WeatherForecastCall;
import cn.ommiao.mowidgets.httpcalls.weather.WeatherLifestyleCall;
import cn.ommiao.mowidgets.httpcalls.weather.WeatherNowCall;
import cn.ommiao.mowidgets.httpcalls.weather.model.WeatherIn;
import cn.ommiao.mowidgets.httpcalls.weather.model.WeatherOut;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.Step;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.BlackCardWeatherWidget;
import cn.ommiao.network.SimpleRequestCallback;

public class BlackCardWeatherRequester extends BaseRequester<BlackCardWeatherWidget> {

    private static final String INVALID_KEY = "Invalid Key.";

    protected HeWeather6 heWeather6 = new HeWeather6();

    public BlackCardWeatherRequester(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        super(context, appWidgetManager, widgetId);
    }

    @Override
    protected BlackCardWeatherWidget getTargetWidget() {
        return new BlackCardWeatherWidget();
    }

    @Override
    public void request() {
        String location = SPUtil.getString(context.getString(R.string.label_black_card_weather) + widgetId + "_location", "西安");
        String key = SPUtil.getString(context.getString(R.string.label_black_card_weather) + widgetId + "_key", INVALID_KEY);
        if(INVALID_KEY.equals(key)){
            //ToastUtil.shortToast("请设置和风天气的Key以获取天气");
            notifyDataRequested();
            return;
        }
        if(context.getString(R.string.key_secret).equals(key)){
            key = Constant.WEATHER_KEY;
        }

        WeatherIn in = new WeatherIn(location, key);

        Step step = new Step();
        Step.Notifier notifier = step.getNotifier();

        step.then(() -> newCall(new WeatherNowCall(), in, new SimpleRequestCallback<WeatherOut>() {
            @Override
            public void onSuccess(WeatherOut out) {
                heWeather6.setBasic(out.getHeWeather6().getBasic());
                heWeather6.setNow(out.getNowWeather());
                notifier.success();
            }

            @Override
            public void onError(WeatherOut out) {
                dataRequestError(out);
            }
        })).then(() -> newCall(new WeatherForecastCall(), in, new SimpleRequestCallback<WeatherOut>() {
            @Override
            public void onSuccess(WeatherOut out) {
                heWeather6.setDaily_forecast(out.getHeWeather6().getDaily_forecast());
                notifier.success();
            }

            @Override
            public void onError(WeatherOut out) {
                dataRequestError(out);
            }
        })).then(() -> {
            newCall(new AirNowCall(), in, new SimpleRequestCallback<WeatherOut>() {
                @Override
                public void onSuccess(WeatherOut out) {
                    heWeather6.setAir_now_city(out.getHeWeather6().getAir_now_city());
                    notifier.success();
                }

                @Override
                public void onError(WeatherOut out) {
                    dataRequestError(out);
                }
            });
        }).then(() -> {
            newCall(new WeatherLifestyleCall(), in, new SimpleRequestCallback<WeatherOut>() {
                @Override
                public void onSuccess(WeatherOut out) {
                    if(out.getHeWeather6().getLifestyle().size() > 0){
                        heWeather6.setFirstLifestyle(out.getHeWeather6().getLifestyle().get(0));
                        saveWeatherData();
                        notifyDataRequested();
                    } else {
                        dataRequestError(out);
                    }
                }
                @Override
                public void onError(WeatherOut out) {
                    dataRequestError(out);
                }
            });
        }).excute();
    }

    private void dataRequestError(WeatherOut out){
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

    private void saveWeatherData() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        heWeather6.setUpdateTime(formatter.format(new Date()));
        String data = heWeather6.toJson();
        SPUtil.put(context.getString(R.string.label_black_card_weather) + widgetId + "_heweather6", data);
        Logger.d("BlackCardWeatherRequester saved data.");
    }
}
