package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.configs.BaseConfigActivity;
import cn.ommiao.mowidgets.utils.SPUtil;

public class ThreeLinesWordWidget extends BaseWidget {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_three_lines_word);
        views.setTextViewText(R.id.tv_line1, SPUtil.getString(context.getString(R.string.label_three_lines_word) + appWidgetId + "_word_line1", context.getString(R.string.default_word_line1)));
        views.setTextViewText(R.id.tv_line2, SPUtil.getString(context.getString(R.string.label_three_lines_word) + appWidgetId + "_word_line2", context.getString(R.string.default_word_line2)));
        views.setTextViewText(R.id.tv_line3, SPUtil.getString(context.getString(R.string.label_three_lines_word) + appWidgetId + "_word_line3", context.getString(R.string.default_word_line3)));
        views.setTextColor(R.id.tv_line1, Color.parseColor(SPUtil.getString(context.getString(R.string.label_three_lines_word) + appWidgetId + "_color_line1", context.getString(R.string.default_three_line_word_color))));
        views.setTextColor(R.id.tv_line2, Color.parseColor(SPUtil.getString(context.getString(R.string.label_three_lines_word) + appWidgetId + "_color_line2", context.getString(R.string.default_three_line_word_color))));
        views.setTextColor(R.id.tv_line3, Color.parseColor(SPUtil.getString(context.getString(R.string.label_three_lines_word) + appWidgetId + "_color_line3", context.getString(R.string.default_three_line_word_color))));
        views.setTextViewTextSize(R.id.tv_line1, TypedValue.COMPLEX_UNIT_SP, SPUtil.getInt(context.getString(R.string.label_three_lines_word) + appWidgetId + "_size_line1", 14));
        views.setTextViewTextSize(R.id.tv_line2, TypedValue.COMPLEX_UNIT_SP, SPUtil.getInt(context.getString(R.string.label_three_lines_word) + appWidgetId + "_size_line2", 14));
        views.setTextViewTextSize(R.id.tv_line3, TypedValue.COMPLEX_UNIT_SP, SPUtil.getInt(context.getString(R.string.label_three_lines_word) + appWidgetId + "_size_line3", 14));
        int alignment = SPUtil.getInt(context.getString(R.string.label_three_lines_word) + appWidgetId + "_alignment", Gravity.START);
        views.setInt(R.id.ll_lines, "setGravity", alignment);
        int hPadding = SPUtil.getInt(context.getString(R.string.label_three_lines_word) + appWidgetId + "_horizontal_padding", 0);
        views.setViewPadding(R.id.fl_three_lines_word, hPadding, 0, hPadding, 0);
        return views;
    }

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[]{
                context.getString(R.string.label_three_lines_word) + appWidgetId + "_word_line1",
                context.getString(R.string.label_three_lines_word) + appWidgetId + "_word_line2",
                context.getString(R.string.label_three_lines_word) + appWidgetId + "_word_line3",
                context.getString(R.string.label_three_lines_word) + appWidgetId + "_color_line1",
                context.getString(R.string.label_three_lines_word) + appWidgetId + "_color_line2",
                context.getString(R.string.label_three_lines_word) + appWidgetId + "_color_line3",
                context.getString(R.string.label_three_lines_word) + appWidgetId + "_size_line1",
                context.getString(R.string.label_three_lines_word) + appWidgetId + "_size_line2",
                context.getString(R.string.label_three_lines_word) + appWidgetId + "_size_line3",
                context.getString(R.string.label_three_lines_word) + appWidgetId + "_alignment",
                context.getString(R.string.label_three_lines_word) + appWidgetId + "_horizontal_padding"
        };
    }

}
