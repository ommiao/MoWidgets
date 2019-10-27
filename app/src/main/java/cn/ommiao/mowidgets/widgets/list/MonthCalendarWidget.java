package cn.ommiao.mowidgets.widgets.list;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.requesters.BaseRequester;
import cn.ommiao.mowidgets.utils.SPUtil;

public class MonthCalendarWidget extends BaseTimingRefreshListWidget<MonthCalendarService, BaseRequester<MonthCalendarWidget>> {

    @Override
    protected Class<MonthCalendarService> classOfS() {
        return MonthCalendarService.class;
    }

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_month_calendar);

        String yearMonth = String.format("%s/%s", getYearStr(), getMonthWith0());
        views.setTextViewText(R.id.tv_year_month, yearMonth);

        int alignment = SPUtil.getInt(context.getString(R.string.label_month_calendar) + appWidgetId + "_alignment", Gravity.TOP);

        if(alignment == Gravity.TOP){
            views.setViewVisibility(R.id.iv_top, View.GONE);
            views.setViewVisibility(R.id.iv_bottom, View.VISIBLE);
        } else if(alignment == Gravity.BOTTOM){
            views.setViewVisibility(R.id.iv_top, View.VISIBLE);
            views.setViewVisibility(R.id.iv_bottom, View.GONE);
        } else {
            views.setViewVisibility(R.id.iv_top, View.VISIBLE);
            views.setViewVisibility(R.id.iv_bottom, View.VISIBLE);
        }

        String colorStrOrigin = SPUtil.getString(context.getString(R.string.label_month_calendar) + appWidgetId + "_color_main", "#ff0000");
        views.setInt(R.id.iv_rect, "setColorFilter", Color.parseColor(getColorByHex(colorStrOrigin)));
        views.setInt(R.id.iv_rect, "setAlpha", getAlphaByHex(colorStrOrigin));

        String picPath = SPUtil.getString(context.getString(R.string.label_month_calendar) + appWidgetId + "_path", "Invalid path");
        Bitmap bitmap = null;
        if(!"Invalid path".equals(picPath)){
            picPath = appendRootPath(picPath);
            try {
                bitmap = BitmapFactory.decodeFile(picPath);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        if(bitmap != null){
            views.setImageViewBitmap(R.id.iv_custom, bitmap);
        } else {
            views.setInt(R.id.iv_custom, "setColorFilter", Color.parseColor(getColorByHex(colorStrOrigin)));
            views.setInt(R.id.iv_custom, "setAlpha", getAlphaByHex(colorStrOrigin));
        }

        views.setRemoteAdapter(R.id.grid_calendar, new Intent(context, classOfS()).putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.grid_calendar);
        return views;
    }

    @Override
    public boolean needAccessibilityService() {
        return false;
    }

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[]{
                context.getString(R.string.label_month_calendar) + appWidgetId + "_color_main",
                context.getString(R.string.label_month_calendar) + appWidgetId + "_color_date_now",
                context.getString(R.string.label_month_calendar) + appWidgetId + "_alignment",
                context.getString(R.string.label_month_calendar) + appWidgetId + "_path"
        };
    }

}
