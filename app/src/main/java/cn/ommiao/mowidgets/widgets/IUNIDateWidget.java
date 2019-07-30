package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;

public class IUNIDateWidget extends BaseWidget {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_iuni_date);
        views.setTextViewText(R.id.tv_week, getDisplayWeekEn());
        int day = getDay();
        String dateLarge = getDisplayMonthEn() + " " + day;
        views.setTextViewText(R.id.tv_date, dateLarge);
        String suffix = "th";
        if(day == 1 || day == 21 || day == 31){
            suffix = "st";
        } else if(day == 2 || day == 22){
            suffix = "nd";
        } else if(day == 3 || day == 23){
            suffix = "rd";
        }
        views.setTextViewText(R.id.tv_suffix, suffix);
        String dateWeek = getMonthNo() + "月" + day + "日" + "  " + getDisplayWeekCn();
        views.setTextViewText(R.id.tv_date_and_week, dateWeek);
        views.setOnClickPendingIntent(R.id.ll_iuni_date, getAlarmIntent(context));
        return views;
    }

    @Override
    protected boolean needListenTimeChanged() {
        return true;
    }
}
