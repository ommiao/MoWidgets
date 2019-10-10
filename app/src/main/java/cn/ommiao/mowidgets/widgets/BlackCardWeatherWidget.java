package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import com.orhanobut.logger.Logger;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.entities.DailyForecast;
import cn.ommiao.mowidgets.entities.HeWeather6;
import cn.ommiao.mowidgets.entities.NowWeather;
import cn.ommiao.mowidgets.requesters.BaseRequester;
import cn.ommiao.mowidgets.requesters.BlackCardWeatherRequester;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.WeatherUtil;

public class BlackCardWeatherWidget extends BaseWidget {
    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_black_card_weather);
        HeWeather6 heWeather6 = HeWeather6.fromJson(SPUtil.getString(context.getString(R.string.label_black_card_weather) + appWidgetId + "_heweather6", "{}"), HeWeather6.class);
        Logger.d(heWeather6.toJson());
        if(heWeather6.getNow() != null){
            NowWeather nowWeather = heWeather6.getNow();
            views.setTextViewText(R.id.tv_location, heWeather6.getBasic().getLocation());
            views.setTextViewText(R.id.tv_time, heWeather6.getUpdateTime());
            views.setTextViewText(R.id.tv_tmp, nowWeather.getTmp() + "°");
            views.setTextViewText(R.id.tv_weather, nowWeather.getCond_txt());
            views.setImageViewResource(R.id.iv_weather, WeatherUtil.getIconRes(nowWeather.getCond_code()));
            views.setTextViewText(R.id.tv_wind, nowWeather.getWind_dir() + nowWeather.getWind_sc() + "级");
            views.setTextViewText(R.id.tv_humidity, "相对湿度" + nowWeather.getHum() + "%");
        }
        if(heWeather6.getAir_now_city() != null){
            views.setTextViewText(R.id.tv_aqi, "AQI " + heWeather6.getAir_now_city().getQlty());
        }
        if(heWeather6.getDaily_forecast() != null && heWeather6.getDaily_forecast().size() > 0){
            DailyForecast now = heWeather6.getDaily_forecast().get(0);
            views.setTextViewText(R.id.tv_uv, "UV" + now.getUv_index());
        }
        if(heWeather6.getFirstLifestyle() != null){
            views.setTextViewText(R.id.tv_lifestyle, heWeather6.getFirstLifestyle().getDesc());
        }
        return views;
    }

    @Override
    public boolean needRequestData() {
        return true;
    }

    @Override
    public BaseRequester getDataRequester(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        return new BlackCardWeatherRequester(context, appWidgetManager, appWidgetId);
    }
}
