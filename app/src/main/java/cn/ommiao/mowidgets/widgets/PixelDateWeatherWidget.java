package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.requesters.PixelDateWeatherRequester;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.WeatherUtil;

public class PixelDateWeatherWidget extends BaseWidget<PixelDateWeatherRequester> {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_pixel_date_weather);
        String tmp = SPUtil.getString(context.getString(R.string.label_pixel_date_weather) + appWidgetId + "_tmp", "29");
        tmp += "℃";
        views.setTextViewText(R.id.tv_tmp, tmp);
        String condCode = SPUtil.getString(context.getString(R.string.label_pixel_date_weather) + appWidgetId + "_cond_code", "100");
        int iconRes = WeatherUtil.getIconRes(condCode);
        views.setImageViewResource(R.id.iv_weather, iconRes);
        return views;
    }

    @Override
    public boolean needRequestData() {
        return true;
    }

    @Override
    public PixelDateWeatherRequester getDataRequester(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        return new PixelDateWeatherRequester(context, appWidgetManager, appWidgetId);
    }

}
