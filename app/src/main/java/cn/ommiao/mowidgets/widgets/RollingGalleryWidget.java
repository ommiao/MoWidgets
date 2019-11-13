package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;

public class RollingGalleryWidget extends BaseWidget {

    private float radiusScale;

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_rolling_gallery);
        radiusScale = SPUtil.getFloat(context.getString(R.string.label_rolling_gallery) + appWidgetId + "_radius_scale", 0.03F);
        views.setImageViewBitmap(R.id.iv_gallery_1, getBitmap(appendRootPath(SPUtil.getString(context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_1", ""))));
        views.setImageViewBitmap(R.id.iv_gallery_2, getBitmap(appendRootPath(SPUtil.getString(context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_2", ""))));
        views.setImageViewBitmap(R.id.iv_gallery_3, getBitmap(appendRootPath(SPUtil.getString(context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_3", ""))));
        views.setImageViewBitmap(R.id.iv_gallery_1_bg, getBitmap(appendRootPath(SPUtil.getString(context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_bg_1", ""))));
        views.setImageViewBitmap(R.id.iv_gallery_2_bg, getBitmap(appendRootPath(SPUtil.getString(context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_bg_2", ""))));
        views.setImageViewBitmap(R.id.iv_gallery_3_bg, getBitmap(appendRootPath(SPUtil.getString(context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_bg_3", ""))));
        return views;
    }

    private Bitmap getBitmap(String filePath){
        Bitmap source = BitmapFactory.decodeFile(filePath);
        if(source == null){
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        //设置矩形大小
        Rect rect = new Rect(0,0,source.getWidth(),source.getHeight());
        RectF rectf = new RectF(rect);

        // 相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        //画圆角
        float radius = source.getWidth() > source.getHeight() ? source.getWidth() * radiusScale : source.getHeight() * radiusScale;
        canvas.drawRoundRect(rectf, radius, radius, paint);
        // 取两层绘制，显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 把原生的图片放到这个画布上，使之带有画布的效果
        canvas.drawBitmap(source, rect, rect, paint);
        return bitmap;
    }

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[]{
                context.getString(R.string.label_rolling_gallery) + appWidgetId + "_radius_scale",
                context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_1",
                context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_2",
                context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_3",
                context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_bg_1",
                context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_bg_2",
                context.getString(R.string.label_rolling_gallery) + appWidgetId + "_path_bg_3"
        };
    }

}
