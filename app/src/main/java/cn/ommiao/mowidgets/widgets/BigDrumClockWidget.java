package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class BigDrumClockWidget extends BaseWidget {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock_big_drum);
        int colorHour = Color.parseColor(SPUtil.getString(context.getString(R.string.label_big_drum_clock) + appWidgetId + "_color_hour", "#000000"));
        int colorMinute = Color.parseColor(SPUtil.getString(context.getString(R.string.label_big_drum_clock) + appWidgetId + "_color_minute", "#9D1237"));
        int colorWeek = Color.parseColor(SPUtil.getString(context.getString(R.string.label_big_drum_clock) + appWidgetId + "_color_week", "#000000"));
        views.setTextColor(R.id.tc_hour, colorHour);
        views.setTextColor(R.id.tc_minute, colorMinute);
        views.setTextColor(R.id.tc_week, colorWeek);
        String colorLine = SPUtil.getString(context.getString(R.string.label_big_drum_clock) + appWidgetId + "_color_line", "#000000");
        views.setInt(R.id.iv_line2, "setColorFilter", Color.parseColor(getColorByHex(colorLine)));
        views.setInt(R.id.iv_line2, "setAlpha", getAlphaByHex(colorLine));
        return views;
    }

}
