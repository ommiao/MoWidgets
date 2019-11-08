package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class PocoClockWidget extends BaseWidget {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock_poco);
        String color = SPUtil.getString(context.getString(R.string.label_poco_clock) + appWidgetId + "_color", "#ffffff");
        setTextColor(views, Color.parseColor(color), R.id.tc_time, R.id.tc_week_date);
        views.setOnClickPendingIntent(R.id.rl_poco_clock, getAlarmIntent(context));
        return views;
    }

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[]{
                context.getString(R.string.label_poco_clock) + appWidgetId + "_color"
        };
    }

}
