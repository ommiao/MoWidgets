package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.opengl.Visibility;
import android.view.View;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class IUNIDateWidget extends TimingRefreshWidget {

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

        boolean showChinese = SPUtil.getBoolean(context.getString(R.string.label_iuni_date) + appWidgetId + "_show_chinese", true);
        if(showChinese){
            String dateWeek = getMonthNo() + "月" + day + "日" + "  " + getDisplayWeekCn();
            views.setTextViewText(R.id.tv_date_and_week, dateWeek);
        } else {
            views.setInt(R.id.tv_date_and_week, "setVisibility", View.GONE);
        }

        views.setOnClickPendingIntent(R.id.ll_iuni_date, getAlarmIntent(context));
        return views;
    }

}
