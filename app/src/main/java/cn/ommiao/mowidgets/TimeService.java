package cn.ommiao.mowidgets;

import android.accessibilityservice.AccessibilityService;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.accessibility.AccessibilityEvent;

import com.orhanobut.logger.Logger;

import cn.ommiao.mowidgets.widgets.HydrogenClockWidget;
import cn.ommiao.mowidgets.widgets.IUNIDateWidget;
import cn.ommiao.mowidgets.widgets.QTextClockWidget;

public class TimeService extends AccessibilityService {


    private TimeTickReceiver timeTickReceiver;

    @Override
    protected void onServiceConnected() {
        timeTickReceiver = new TimeTickReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(timeTickReceiver, filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(timeTickReceiver);
        super.onDestroy();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {

    }

    private class TimeTickReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.d("TimeTickReceiver: " + intent.getAction());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            int[] qTextIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, QTextClockWidget.class));
            for(int id : qTextIds){
                appWidgetManager.updateAppWidget(id, new QTextClockWidget().getRemoteViews(context, appWidgetManager, id));
            }

            int[] hydrogenIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, HydrogenClockWidget.class));
            for(int id : hydrogenIds){
                appWidgetManager.updateAppWidget(id, new HydrogenClockWidget().getRemoteViews(context, appWidgetManager, id));
            }

            int[] iuniIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IUNIDateWidget.class));
            for(int id : iuniIds){
                appWidgetManager.updateAppWidget(id, new IUNIDateWidget().getRemoteViews(context, appWidgetManager, id));
            }
        }
    }
}
