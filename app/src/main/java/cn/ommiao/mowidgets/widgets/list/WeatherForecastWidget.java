package cn.ommiao.mowidgets.widgets.list;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.requesters.WeatherForecastRequester;

public class WeatherForecastWidget extends BaseListWidget<WeatherForecastService, WeatherForecastRequester> {

    @Override
    protected Class<WeatherForecastService> classOfS() {
        return WeatherForecastService.class;
    }

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list);
        views.setRemoteAdapter(R.id.list, new Intent(context, classOfS()).putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId));
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
