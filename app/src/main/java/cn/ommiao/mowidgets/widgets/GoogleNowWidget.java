package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import androidx.annotation.LayoutRes;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class GoogleNowWidget extends BaseWidget {

    public enum Theme {
        WHITE(R.layout.widget_google_now),
        BLACK(R.layout.widget_google_now_dark);

        private int layoutRes;

        Theme(@LayoutRes int layoutRes){
            this.layoutRes = layoutRes;
        }

        public int getLayoutRes() {
            return layoutRes;
        }
    }

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Theme theme = Theme.valueOf(SPUtil.getString(context.getString(R.string.label_google_now) + appWidgetId + "_theme", "WHITE"));
        RemoteViews views = new RemoteViews(context.getPackageName(), theme.getLayoutRes());
        String slogan = SPUtil.getString(context.getString(R.string.label_google_now) + appWidgetId + "_word", context.getString(R.string.slogan_google_now));
        views.setTextViewText(R.id.tv_slogan, slogan);
        return views;
    }

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[]{
                context.getString(R.string.label_google_now) + appWidgetId + "_word"
        };
    }
}
