package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class QTextClockWidget extends TimingRefreshWidget {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_q_text_clock);
        views.setTextViewText(R.id.tv_hour, getTextHourEn());
        views.setTextViewText(R.id.tv_minute, getTextMinuteEn());
        views.setOnClickPendingIntent(R.id.ll_q_text_clock, getAlarmIntent(context));
        views.setTextColor(R.id.tv_it_s, Color.parseColor(SPUtil.getString(context.getString(R.string.label_q_text_clock) + appWidgetId + "_color_it_s", context.getString(R.string.default_color_q_text_it_s))));
        views.setTextColor(R.id.tv_hour, Color.parseColor(SPUtil.getString(context.getString(R.string.label_q_text_clock) + appWidgetId + "_color_time", context.getString(R.string.default_color_q_text_time))));
        views.setTextColor(R.id.tv_minute, Color.parseColor(SPUtil.getString(context.getString(R.string.label_q_text_clock) + appWidgetId + "_color_time", context.getString(R.string.default_color_q_text_time))));
        views.setTextViewTextSize(R.id.tv_it_s, TypedValue.COMPLEX_UNIT_SP, SPUtil.getInt(context.getString(R.string.label_q_text_clock) + appWidgetId + "_size_text", 24));
        views.setTextViewTextSize(R.id.tv_hour, TypedValue.COMPLEX_UNIT_SP, SPUtil.getInt(context.getString(R.string.label_q_text_clock) + appWidgetId + "_size_text", 24));
        views.setTextViewTextSize(R.id.tv_minute, TypedValue.COMPLEX_UNIT_SP, SPUtil.getInt(context.getString(R.string.label_q_text_clock) + appWidgetId + "_size_text", 24));
        int topPadding = SPUtil.getInt(context.getString(R.string.label_q_text_clock) + appWidgetId + "_top_padding", 0);
        int leftPadding = SPUtil.getInt(context.getString(R.string.label_q_text_clock) + appWidgetId + "_left_padding", 0);
        int linePadding = SPUtil.getInt(context.getString(R.string.label_q_text_clock) + appWidgetId + "_line_padding", 0);
        views.setViewPadding(R.id.ll_q_text_clock, leftPadding, topPadding, 0, 0);
        views.setViewPadding(R.id.tv_it_s, 0, 0, 0, linePadding);
        views.setViewPadding(R.id.tv_hour, 0, 0, 0, linePadding);
        return views;
    }

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[]{
                context.getString(R.string.label_q_text_clock) + appWidgetId + "_color_it_s",
                context.getString(R.string.label_q_text_clock) + appWidgetId + "_color_time",
                context.getString(R.string.label_q_text_clock) + appWidgetId + "_color_time",
                context.getString(R.string.label_q_text_clock) + appWidgetId + "_size_text",
                context.getString(R.string.label_q_text_clock) + appWidgetId + "_size_text",
                context.getString(R.string.label_q_text_clock) + appWidgetId + "_size_text",
                context.getString(R.string.label_q_text_clock) + appWidgetId + "_top_padding",
                context.getString(R.string.label_q_text_clock) + appWidgetId + "_left_padding",
                context.getString(R.string.label_q_text_clock) + appWidgetId + "_line_padding"
        };
    }

    private String getTextHourEn(){
        int hour = getHour();
        switch (hour){
            case 0:
                return "Zero";
            case 1:
                return "One";
            case 2:
                return "Two";
            case 3:
                return "Three";
            case 4:
                return "Four";
            case 5:
                return "Five";
            case 6:
                return "Six";
            case 7:
                return "Seven";
            case 8:
                return "Eight";
            case 9:
                return "Nine";
            case 10:
                return "Ten";
            case 11:
                return "Eleven";
            case 12:
                return "Twelve";
            case 13:
                return "Thirteen";
            case 14:
                return "Fourteen";
            case 15:
                return "Fifteen";
            case 16:
                return "Sixteen";
            case 17:
                return "Seveteen";
            case 18:
                return "Eighteen";
            case 19:
                return "Nineteen";
            case 20:
                return "Twenty";
            case 21:
                return "Twenty-One";
            case 22:
                return "Twenty-Two";
            case 23:
                return "Twenty-Three";
            case 24:
                return "Twenty-Four";
            default:
                return "Null...";

        }
    }

    private String getTextMinuteEn(){
        int minute = getMinute();
        switch (minute){
            case 0:
                return "o’clock";
            case 1:
                return "One";
            case 2:
                return "Two";
            case 3:
                return "Three";
            case 4:
                return "Four";
            case 5:
                return "Five";
            case 6:
                return "Six";
            case 7:
                return "Seven";
            case 8:
                return "Eight";
            case 9:
                return "Nine";
            case 10:
                return "Ten";
            case 11:
                return "Eleven";
            case 12:
                return "Twelve";
            case 13:
                return "Thirteen";
            case 14:
                return "Fourteen";
            case 15:
                return "Fifteen";
            case 16:
                return "Sixteen";
            case 17:
                return "Seveteen";
            case 18:
                return "Eighteen";
            case 19:
                return "Nineteen";
            case 20:
                return "Twenty";
            case 30:
                return "Thirty";
            case 40:
                return "Forty";
            case 50:
                return "Fifty";
            case 60:
                return "Sixty";

        }
        if(minute > 50){
            return "Fifty-" + getTextNumberEn(minute - 50);
        } else if(minute > 40){
            return "Forty-" + getTextNumberEn(minute - 40);
        } else if(minute > 30){
            return "Thirty-" + getTextNumberEn(minute - 30);
        } else if(minute > 20){
            return "Twenty-" + getTextNumberEn(minute - 20);
        }
        return "Null...";
    }

    private String getTextNumberEn(int number){
        switch (number){
            case 1:
                return "One";
            case 2:
                return "Two";
            case 3:
                return "Three";
            case 4:
                return "Four";
            case 5:
                return "Five";
            case 6:
                return "Six";
            case 7:
                return "Seven";
            case 8:
                return "Eight";
            case 9:
                return "Nine";
            default:
                return "Null...";

        }
    }

}
