package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;

public class GoogleNowWidget extends BaseWidget {
    @Override
    protected RemoteViews update(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        return new RemoteViews(context.getPackageName(), R.layout.widget_google_now);
    }
}
