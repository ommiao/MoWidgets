package cn.ommiao.mowidgets.widgets.list;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.requesters.WeatherForecastRequester;
import cn.ommiao.mowidgets.utils.SPUtil;

public class WeatherForecastWidget extends BaseListWidget<WeatherForecastService, WeatherForecastRequester> {

    @Override
    protected Class<WeatherForecastService> classOfS() {
        return WeatherForecastService.class;
    }

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather_forecast);
        int alignment = SPUtil.getInt(context.getString(R.string.label_weather_forecast) + appWidgetId + "_alignment", Gravity.TOP);
        if(alignment == Gravity.TOP){
            views.setViewVisibility(R.id.iv_top, View.GONE);
            views.setViewVisibility(R.id.iv_bottom, View.VISIBLE);
        } else if(alignment == Gravity.BOTTOM){
            views.setViewVisibility(R.id.iv_top, View.VISIBLE);
            views.setViewVisibility(R.id.iv_bottom, View.GONE);
        } else {
            views.setViewVisibility(R.id.iv_top, View.VISIBLE);
            views.setViewVisibility(R.id.iv_bottom, View.VISIBLE);
        }
        views.setRemoteAdapter(R.id.list, new Intent(context, classOfS()).putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId));
        views.setPendingIntentTemplate(R.id.list, getRefreshIntent(context, appWidgetId));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list);
        return views;
    }

    @Override
    public boolean needRequestData() {
        return true;
    }

    @Override
    public WeatherForecastRequester getDataRequester(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        return new WeatherForecastRequester(context, appWidgetManager, appWidgetId);
    }
}
