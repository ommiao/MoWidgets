package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class GoogleWordWidget extends BaseWidget {
    @Override
    protected RemoteViews update(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        return getRemoteViews(context, appWidgetManager, appWidgetId);
    }

    public static RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_google_word);
        String colorStr;
        int alpha = 255;
        String colorStrOrigin = SPUtil.getString(context.getString(R.string.label_google_word) + appWidgetId, context.getString(R.string.default_google_word_color));
        if(colorStrOrigin.length() == 9){
            colorStr = "#" + colorStrOrigin.substring(3);
            alpha = Integer.parseInt(colorStrOrigin.substring(1, 3), 16);
        } else {
            colorStr = colorStrOrigin;
        }
        views.setInt(R.id.iv_google_word, "setColorFilter", Color.parseColor(colorStr));
        views.setInt(R.id.iv_google_word, "setAlpha", alpha);
        return views;
    }
}
