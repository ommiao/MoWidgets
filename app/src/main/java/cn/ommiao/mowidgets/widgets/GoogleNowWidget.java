package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class GoogleNowWidget extends BaseWidget {
    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_google_now);
        String slogan = SPUtil.getString(context.getString(R.string.label_google_now) + appWidgetId + "_word", context.getString(R.string.slogan_google_now));
        views.setTextViewText(R.id.tv_slogan, slogan);
        return views;
    }
}
