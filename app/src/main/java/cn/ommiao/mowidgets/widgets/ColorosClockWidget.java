package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.requesters.ColorosClockRequester;
import cn.ommiao.mowidgets.utils.SPUtil;

public class ColorosClockWidget extends BaseWidget<ColorosClockRequester> {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock_coloros);
        String tmp = SPUtil.getString(context.getString(R.string.label_coloros_clock) + appWidgetId + "_tmp", "26");
        String conTxt = SPUtil.getString(context.getString(R.string.label_coloros_clock) + appWidgetId + "_con_txt", "晴");
        String weatherStr = conTxt + " " + tmp + "℃";
        views.setTextViewText(R.id.tv_weather, weatherStr);
        return views;
    }

    @Override
    public boolean needRequestData() {
        return true;
    }

    @Override
    public ColorosClockRequester getDataRequester(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        return new ColorosClockRequester(context, appWidgetManager, appWidgetId);
    }
}
