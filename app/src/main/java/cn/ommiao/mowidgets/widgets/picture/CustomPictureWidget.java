package cn.ommiao.mowidgets.widgets.picture;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.BaseWidget;

public class CustomPictureWidget extends BaseWidget {

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_picture);
        String picPath = SPUtil.getString(context.getString(R.string.label_custom_picture) + appWidgetId + "_path", "Invalid path");
        picPath = appendRootPath(picPath);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(picPath);
        } catch (Exception e){
            e.printStackTrace();
            ToastUtil.shortToast("图片加载失败，请检查路径");
        }
        if(bitmap != null){
            views.setImageViewBitmap(R.id.iv_picture, bitmap);
        }
        int alpha = SPUtil.getInt(context.getString(R.string.label_custom_picture) + appWidgetId + "_alpha", 255);
        views.setInt(R.id.iv_picture, "setAlpha", alpha);
        int paddingL = SPUtil.getInt(context.getString(R.string.label_custom_picture) + appWidgetId + "_padding_left", 0);
        int paddingT = SPUtil.getInt(context.getString(R.string.label_custom_picture) + appWidgetId + "_padding_top", 0);
        int paddingR = SPUtil.getInt(context.getString(R.string.label_custom_picture) + appWidgetId + "_padding_right", 0);
        int paddingB = SPUtil.getInt(context.getString(R.string.label_custom_picture) + appWidgetId + "_padding_bottom", 0);
        views.setViewPadding(R.id.fl_picture, paddingL, paddingT, paddingR, paddingB);
        return views;
    }

}