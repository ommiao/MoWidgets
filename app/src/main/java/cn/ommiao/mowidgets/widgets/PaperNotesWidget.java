package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class PaperNotesWidget extends BaseWidget {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_paper_notes);

        String colorStrBottom = SPUtil.getString(context.getString(R.string.label_paper_notes) + appWidgetId + "_color_bottom", "#fc5656");
        views.setInt(R.id.iv_bottom, "setColorFilter", Color.parseColor(getColorByHex(colorStrBottom)));
        views.setInt(R.id.iv_bottom, "setAlpha", getAlphaByHex(colorStrBottom));

        String colorStrTop = SPUtil.getString(context.getString(R.string.label_paper_notes) + appWidgetId + "_color_top", "#ace7ff");
        views.setInt(R.id.iv_top, "setColorFilter", Color.parseColor(getColorByHex(colorStrTop)));
        views.setInt(R.id.iv_top, "setAlpha", getAlphaByHex(colorStrTop));

        String colorStrPin = SPUtil.getString(context.getString(R.string.label_paper_notes) + appWidgetId + "_color_pin", "#e8ff4a");
        views.setInt(R.id.iv_pin, "setColorFilter", Color.parseColor(getColorByHex(colorStrPin)));
        views.setInt(R.id.iv_pin, "setAlpha", getAlphaByHex(colorStrPin));

        views.setTextViewText(R.id.tv_notes, SPUtil.getString(context.getString(R.string.label_paper_notes) + appWidgetId + "_text", ""));
        views.setTextColor(R.id.tv_notes, Color.parseColor(SPUtil.getString(context.getString(R.string.label_paper_notes) + appWidgetId + "_color_text", "#555555")));
        return views;
    }

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[]{
                context.getString(R.string.label_paper_notes) + appWidgetId + "_color_bottom",
                context.getString(R.string.label_paper_notes) + appWidgetId + "_color_top",
                context.getString(R.string.label_paper_notes) + appWidgetId + "_color_pin",
                context.getString(R.string.label_paper_notes) + appWidgetId + "_text",
                context.getString(R.string.label_paper_notes) + appWidgetId + "_color_text"
        };
    }

}
