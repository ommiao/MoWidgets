package cn.ommiao.mowidgets.widgets.pointerclock;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.SweepGradient;
import android.widget.RemoteViews;

import androidx.annotation.DrawableRes;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class MiuiAodWidget extends BasePointerClockWidget {

    private int colorBg1, colorBg2, angle;

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
        colorBg1 = Color.parseColor(SPUtil.getString(context.getString(R.string.label_miui_aod) + appWidgetId + "_color_bg_1", "#00000000"));
        colorBg2 = Color.parseColor(SPUtil.getString(context.getString(R.string.label_miui_aod) + appWidgetId + "_color_bg_2", "#00000000"));
        String colorHourStr = SPUtil.getString(context.getString(R.string.label_miui_aod) + appWidgetId + "_color_hour", "#00000000");
        String colorMinuteStr = SPUtil.getString(context.getString(R.string.label_miui_aod) + appWidgetId + "_color_minute", "#00000000");
        Style style = Style.valueOf(SPUtil.getString(context.getString(R.string.label_miui_aod) + appWidgetId + "_style", "STYLE1"));
        angle = SPUtil.getInt(context.getString(R.string.label_miui_aod) + appWidgetId + "_angle", 0);
        views.setImageViewBitmap(R.id.iv_aod_bg, getColorfulBg(context, style.bgRes));
        views.setImageViewBitmap(R.id.iv_pointer_hour, getPointerHour(context, style));
        views.setImageViewBitmap(R.id.iv_pointer_minute, getPointerMinute(context, style));
        views.setInt(R.id.iv_pointer_hour, "setColorFilter", Color.parseColor(colorHourStr));
        views.setInt(R.id.iv_pointer_minute, "setColorFilter", Color.parseColor(colorMinuteStr));
        return views;
    }

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[]{
                context.getString(R.string.label_miui_aod) + appWidgetId + "_style",
                context.getString(R.string.label_miui_aod) + appWidgetId + "_style",
                context.getString(R.string.label_miui_aod) + appWidgetId + "_color_bg_1",
                context.getString(R.string.label_miui_aod) + appWidgetId + "_color_bg_2",
                context.getString(R.string.label_miui_aod) + appWidgetId + "_color_hour",
                context.getString(R.string.label_miui_aod) + appWidgetId + "_color_minute",
                context.getString(R.string.label_miui_aod) + appWidgetId + "_angle"
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

    private Bitmap getColorfulBg(Context context, @DrawableRes int resId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId).copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        float width = bitmap.getWidth(), height = bitmap.getHeight();
        float start = ((float) (angle % 360) / 360);
        SweepGradient shader = new SweepGradient(width / 2, height / 2, new int[]{colorBg1, colorBg2, colorBg1}, new float[]{start, start + 0.5F, start + 1F});
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawCircle(width / 2, height / 2, width > height ? height / 2 : width / 2, paint);
        return bitmap;
    }

}
