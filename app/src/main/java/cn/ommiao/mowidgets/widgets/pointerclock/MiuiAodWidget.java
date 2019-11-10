package cn.ommiao.mowidgets.widgets.pointerclock;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.RemoteViews;

import androidx.annotation.DrawableRes;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class MiuiAodWidget extends BasePointerClockWidget {

    public enum Style {
        STYLE1("样式1", R.drawable.aod_clock_1_bg, R.drawable.aod_clock_1_hour, R.drawable.aod_clock_1_minute),
        STYLE2("样式2", R.drawable.aod_clock_2_bg, R.drawable.aod_clock_2_hour, R.drawable.aod_clock_2_minute),
        STYLE3("样式3", R.drawable.aod_clock_3_bg, R.drawable.aod_clock_3_hour, R.drawable.aod_clock_3_minute);

        private String desc;
        private int bgRes, hourRes, minuteRes;

        Style(String desc, @DrawableRes int bgRes, @DrawableRes int hourRes, @DrawableRes int minuteRes) {
            this.desc = desc;
            this.bgRes = bgRes;
            this.hourRes = hourRes;
            this.minuteRes = minuteRes;
        }

        public static Style getStyleByDesc(String desc){
            for (Style value : values()) {
                if(value.desc.equals(desc)){
                    return value;
                }
            }
            return STYLE1;
        }

    }

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock_miui_aod);
        Style style = Style.valueOf(SPUtil.getString(context.getString(R.string.label_miui_aod) + appWidgetId + "_style", "STYLE1"));
        views.setImageViewResource(R.id.iv_aod_bg, style.bgRes);
        views.setImageViewBitmap(R.id.iv_pointer_hour, getPointerHour(context, style));
        views.setImageViewBitmap(R.id.iv_pointer_minute, getPointerMinute(context, style));
        return views;
    }

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[]{
                context.getString(R.string.label_miui_aod) + appWidgetId + "_style"
        };
    }

    private Bitmap getPointerHour(Context context, Style style){
        Bitmap source = BitmapFactory.decodeResource(context.getResources(), style.hourRes);
        return getRotatedBitmap(source, getHourRotation());
    }

    private Bitmap getPointerMinute(Context context, Style style){
        Bitmap source = BitmapFactory.decodeResource(context.getResources(), style.minuteRes);
        return getRotatedBitmap(source, getMinuteRotation());
    }

    private Bitmap getRotatedBitmap(Bitmap source, float degrees){
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
