package cn.ommiao.mowidgets.widgets.list;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.orhanobut.logger.Logger;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.entities.DailyForecast;
import cn.ommiao.mowidgets.entities.HeWeather6;
import cn.ommiao.mowidgets.utils.SPUtil;

public class WeatherForecastService extends BaseRemoteViewsService {

    @Override
    protected StringListFactory getFactory(Intent intent) {
        return new StringListFactory(this, intent);
    }

    class StringListFactory extends BaseRemoteViewsService.BaseFactory<DailyForecast> {

        StringListFactory(Context context, Intent intent) {
            super(context, intent);
        }

        @Override
        protected void initData() {
            String data = SPUtil.getString(mContext.getString(R.string.label_weather_forecast) + widgetId + "_heweather6", "{}");
            Logger.d("get: " + widgetId + "--->" + data);
            HeWeather6 heWeather6 = HeWeather6.fromJson(data, HeWeather6.class);
            if(heWeather6.getDaily_forecast() != null){
                mData.addAll(heWeather6.getDaily_forecast());
            }
        }

        @Override
        protected int getItemLayoutId(int i) {
            return R.layout.item_list;
        }

        @Override
        protected RemoteViews buildRemoteViews(RemoteViews views, DailyForecast forecast) {
            views.setTextViewText(R.id.tv_title, forecast.getDate());
            return views;
        }
    }

}
