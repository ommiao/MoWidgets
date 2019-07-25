package cn.ommiao.mowidgets.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.HashMap;

import cn.ommiao.network.BaseRequest;
import cn.ommiao.network.RequestCallBack;
import cn.ommiao.network.RequestInBase;
import cn.ommiao.network.RequestOutBase;
import okhttp3.ResponseBody;
import retrofit2.Response;

public abstract class BaseWidget extends AppWidgetProvider {

    private boolean needRequestData = false;

    private HashMap<RequestInBase, RequestCallBack<? extends RequestOutBase>> callBacks = new HashMap<>();
    private HashMap<String, BaseRequest<? extends RequestInBase, ? extends RequestOutBase>> requests = new HashMap<>();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        needRequestData = true;
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        needRequestData = false;
    }

    protected void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = getRemoteViews(context, appWidgetManager, appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
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

    protected PendingIntent getAlarmIntent(Context context){
        Intent alarmIntent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
        return PendingIntent.getActivity(context, 0, alarmIntent, 0);
    }

    private <OUT extends RequestOutBase> RequestCallBack<OUT> arrangeCallback(final String url, final RequestInBase in, final RequestCallBack<OUT> callBack) {
        RequestCallBack<OUT> temp = new RequestCallBack<OUT>() {
            @Override
            public void onSuccess(OUT result, String str, Response<ResponseBody> res) {
                callBacks.remove(in);
                requests.remove(url);
                callBack.onSuccess(result, str, res);
            }

            @Override
            public void onError(int code, String message, Throwable err) {
                callBacks.remove(in);
                requests.remove(url);
                callBack.onError(code,  message, err);
            }

            @Override
            public void onCancel() {
                callBacks.remove(in);
                requests.remove(url);
                callBack.onCancel();
            }
        };
        callBacks.put(in, temp);
        return temp;
    }

    protected  <IN extends RequestInBase, OUT extends RequestOutBase> void newCall(BaseRequest<IN, OUT> request, IN in, RequestCallBack<OUT> callBack) {
        request.params(in).build(arrangeCallback(request.getUrl(), in, callBack));
        BaseRequest old = requests.put(request.getUrl(), request.call());
        if (old != null) {
            RequestCallBack oldCallback = callBacks.remove(old.getParam());
            if (oldCallback != null) {
                //won't receive http callback.
                old.clearCallback();
                //send cancel callback.
                oldCallback.onCancel();
            }
        }
    }

    public void setNeedRequestData(boolean needRequestData) {
        this.needRequestData = needRequestData;
    }

    protected boolean needRequestData(){
        return needRequestData;
    }

}
