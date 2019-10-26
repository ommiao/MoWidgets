package cn.ommiao.mowidgets.widgets.list;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.orhanobut.logger.Logger;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.requesters.BaseRequester;

public class MonthCalendarWidget extends BaseListWidget<MonthCalendarService, BaseRequester<MonthCalendarWidget>> {

    @Override
    protected Class<MonthCalendarService> classOfS() {
        return MonthCalendarService.class;
    }

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_month_calendar);
        views.setRemoteAdapter(R.id.grid_calendar, new Intent(context, classOfS()).putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId));
        return views;
    }

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[0];
    }

}
