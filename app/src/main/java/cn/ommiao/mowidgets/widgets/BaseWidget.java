package cn.ommiao.mowidgets.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.AlarmClock;
import android.widget.RemoteViews;

import java.util.Calendar;

import cn.ommiao.mowidgets.requesters.BaseRequester;

public abstract class BaseWidget<R extends BaseRequester> extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    protected void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        if(needRequestData()){
            getDataRequester(context, appWidgetManager, appWidgetId).request();
        } else {
            RemoteViews remoteViews = getRemoteViews(context, appWidgetManager, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    public abstract RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId);

    protected int getWeekNo(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    protected int getMonthNo(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    protected int getYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    protected String getYearStr(){
        return String.valueOf(getYear());
    }

    protected int getDay(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    protected String getDayWith0(){
        int d = getDay();
        return d >= 10 ? String.valueOf(d) : "0" + d;
    }

    protected int getMinute(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    protected String getMinuteWith0(){
        int m = getMinute();
        return m >= 10 ? String.valueOf(m) : "0" + m;
    }

    protected int getHour(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    protected String getDisplayWeekEn() {
        return getDisplayWeekEn(getWeekNo());
    }

    protected String getDisplayWeekEn(int w) {
        switch (w){
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 0:
                return "Sunday";
        }
        return "Null...";
    }

    protected String getDisplayWeekCn() {
        return getDisplayWeekCn(getWeekNo());
    }

    protected String getDisplayWeekCn(int w) {
        switch (w){
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 0:
                return "星期日";
        }
        return "Null...";
    }

    protected String getDisplaySimpleMonthEn(){
        return getDisplaySimpleMonthEn(getMonthNo());
    }

    protected String getDisplaySimpleMonthEn(int m) {
        switch (m){
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sept";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }
        return "Null";
    }

    protected String getDisplayMonthEn() {
        return getDisplayMonthEn(getMonthNo());
    }

    protected String getDisplayMonthEn(int m) {
        switch (m){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }
        return "Null";
    }

    protected PendingIntent getAlarmIntent(Context context){
        Intent alarmIntent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
        return PendingIntent.getActivity(context, 0, alarmIntent, 0);
    }

    public boolean needRequestData(){
        return false;
    }

    public R getDataRequester(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        return null;
    }

    protected String getColorByHex(String hex){
        String colorStr;
        if(hex.length() == 9){
            colorStr = "#" + hex.substring(3);
        } else {
            colorStr = hex;
        }
        return colorStr;
    }

    protected int getAlphaByHex(String hex){
        int alpha = 255;
        if(hex.length() == 9){
            alpha = Integer.parseInt(hex.substring(1, 3), 16);
        }
        return alpha;
    }

    protected String appendRootPath(String relativePath){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + relativePath;
    }

}
