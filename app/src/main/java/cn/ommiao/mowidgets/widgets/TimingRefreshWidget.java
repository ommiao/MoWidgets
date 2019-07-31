package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import cn.ommiao.mowidgets.requesters.BaseRequester;

public abstract class TimingRefreshWidget<R extends BaseRequester> extends BaseWidget<R> {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_TIME_CHANGED.equals(intent.getAction())){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(new ComponentName(context, this.getClass())));
        } else {
            super.onReceive(context, intent);
        }
    }
}
