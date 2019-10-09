package cn.ommiao.mowidgets.widgets.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.widget.RemoteViews;

import com.orhanobut.logger.Logger;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.entities.DailyForecast;
import cn.ommiao.mowidgets.entities.HeWeather6;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.WeatherUtil;

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
            if(heWeather6.getDaily_forecast() != null && heWeather6.getDaily_forecast().size() >= 3){
                mData.addAll(heWeather6.getDaily_forecast());
                mData.get(0).setColorCard("#FFD200");
                mData.get(1).setColorCard("#E2E2E2");
                mData.get(2).setColorCard("#1C1E21");
                mData.get(0).setColorText("#000000");
                mData.get(1).setColorText("#000000");
                mData.get(2).setColorText("#FFFFFF");
            } else {
                DailyForecast dailyForecast = new DailyForecast();
                dailyForecast.setDate("美好的一天");
                mData.add(dailyForecast);
            }
        }

        @Override
        protected int getItemLayoutId(int i) {
            return R.layout.item_weather_forecast;
        }

        @Override
        protected RemoteViews buildRemoteViews(RemoteViews views, DailyForecast forecast) {
            setColorForText(views, Color.parseColor(forecast.getColorText()), R.id.tv_date, R.id.tv_weather_desc, R.id.tv_sun_desc, R.id.tv_wind_desc);
            String date = forecast.getDate();
            String[] yyyymmdd = date.split("-");
            String sep = " / ";
            String month = yyyymmdd[1];
            String day = yyyymmdd[2];
            String all = day + sep + month;
            SpannableString spannableString = new SpannableString(all);
            int dayLength = day.length();
            spannableString.setSpan(new AbsoluteSizeSpan(18, true), 0, dayLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            views.setTextViewText(R.id.tv_date, spannableString);
            views.setInt(R.id.iv_mask, "setColorFilter", Color.parseColor(forecast.getColorCard()));
            String weatherDesc = "白天" + forecast.getCond_txt_d() + "   " + "夜间" + forecast.getCond_txt_n() + "   " + "气温" + forecast.getTmp_min() + "° - " + forecast.getTmp_max() + "°";
            views.setTextViewText(R.id.tv_weather_desc, weatherDesc);
            views.setImageViewResource(R.id.iv_weather, WeatherUtil.getIconRes(forecast.getCond_code_d()));
            views.setTextViewText(R.id.tv_sun_desc, "日出" + forecast.getSr() + "   " + "日落" + forecast.getSs());
            views.setTextViewText(R.id.tv_wind_desc, forecast.getWind_sc() + "级" + forecast.getWind_dir());
            return views;
        }

        private void setColorForText(RemoteViews views, int color, int... ids){
            for (int id : ids) {
                views.setTextColor(id, color);
            }
        }
    }

}
