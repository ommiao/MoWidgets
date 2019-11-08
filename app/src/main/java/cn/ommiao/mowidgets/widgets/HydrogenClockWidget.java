package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class HydrogenClockWidget extends TimingRefreshWidget {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock_hydrogen);
        String color = SPUtil.getString(context.getString(R.string.label_hydrogen_clock) + appWidgetId + "_color", "#ffffff");
        setTextColor(views, Color.parseColor(color), R.id.tv_hour, R.id.tv_colon, R.id.tv_minute, R.id.date, R.id.week);
        views.setInt(R.id.iv_line, "setColorFilter", Color.parseColor(getColorByHex(color)));
        views.setInt(R.id.iv_line, "setAlpha", getAlphaByHex(color));
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
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[]{
                context.getString(R.string.label_hydrogen_clock) + appWidgetId + "_color"
        };
    }

}
