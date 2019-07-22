package cn.ommiao.mowidgets.widgets;

import android.content.Context;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;

public class HydrogenClockWidget extends BaseWidget {

    @Override
    protected RemoteViews update(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock_hydrogen);
        String year = getYearStr();
        String month = getDisplaySimpleMonthEn();
        String day = getDayWith0();
        String date = month + "." + day + "." + year;
        String week = getDisplayWeekEn();
        views.setTextViewText(R.id.date, date);
        views.setTextViewText(R.id.week, week);
        return views;
    }

}
