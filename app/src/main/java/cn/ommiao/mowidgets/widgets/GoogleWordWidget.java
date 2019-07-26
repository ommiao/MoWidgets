package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class GoogleWordWidget extends BaseWidget {
    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_google_word);
        String colorStrOrigin = SPUtil.getString(context.getString(R.string.label_google_word) + appWidgetId, context.getString(R.string.default_google_word_color));
        views.setInt(R.id.iv_google_word, "setColorFilter", Color.parseColor(getColorByHex(colorStrOrigin)));
        views.setInt(R.id.iv_google_word, "setAlpha", getAlphaByHex(colorStrOrigin));
        return views;
    }
}
