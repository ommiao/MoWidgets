package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;

public class HydrogenClockWidget extends BaseWidget {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock_hydrogen);
        String year = getYearStr();
        String month = getDisplaySimpleMonthEn();
        String day = getDayWith0();
        String date = month + "." + day + "." + year;
        String week = getDisplayWeekEn();
        views.setTextViewText(R.id.date, date);
        views.setTextViewText(R.id.week, week);
        views.setOnClickPendingIntent(R.id.tv_hour, getAlarmIntent(context));
        views.setOnClickPendingIntent(R.id.tv_colon, getAlarmIntent(context));
        views.setOnClickPendingIntent(R.id.tv_minute, getAlarmIntent(context));
        return views;
    }

    @Override
    protected boolean needListenTimeChanged() {
        return true;
    }
}
