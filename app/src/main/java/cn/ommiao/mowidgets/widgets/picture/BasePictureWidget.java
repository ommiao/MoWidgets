package cn.ommiao.mowidgets.widgets.picture;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import androidx.annotation.DrawableRes;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.widgets.BaseWidget;

public abstract class BasePictureWidget extends BaseWidget {
    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_picture);
        views.setImageViewResource(R.id.iv_picture, getPictureRes());
        return views;
    }

    protected abstract  @DrawableRes int getPictureRes();

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[0];
    }
}
