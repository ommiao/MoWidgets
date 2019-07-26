package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;

public class PocoClockWidget extends BaseWidget {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        return new RemoteViews(context.getPackageName(), R.layout.widget_clock_poco);
    }

}
