package cn.ommiao.mowidgets;

import android.accessibilityservice.AccessibilityService;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.accessibility.AccessibilityEvent;

import java.util.HashSet;

import cn.ommiao.mowidgets.widgets.BaseWidget;
import cn.ommiao.mowidgets.widgets.HydrogenClockWidget;
import cn.ommiao.mowidgets.widgets.IUNIDateWidget;
import cn.ommiao.mowidgets.widgets.JapaneseClockWidget;
import cn.ommiao.mowidgets.widgets.QTextClockWidget;
import cn.ommiao.mowidgets.widgets.pointerclock.MiuiAodWidget;

public class TimeService extends AccessibilityService {

    private static final HashSet<Class<? extends BaseWidget>> widgetsSet = new HashSet<Class<? extends BaseWidget>>(){
        {
            add(QTextClockWidget.class);
            add(HydrogenClockWidget.class);
            add(IUNIDateWidget.class);
            add(JapaneseClockWidget.class);
            add(MiuiAodWidget.class);
        }
    };

    private TimeTickReceiver timeTickReceiver;

    @Override
    protected void onServiceConnected() {
        timeTickReceiver = new TimeTickReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(timeTickReceiver, filter);
        refreshWidgets();
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

    private void refreshWidgets(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        for (Class<? extends BaseWidget> clazz : widgetsSet) {
            int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(this, clazz));
            for(int id : ids){
                try {
                    appWidgetManager.updateAppWidget(id, clazz.newInstance().getRemoteViews(this, appWidgetManager, id));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class TimeTickReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            refreshWidgets();
        }
    }
}
