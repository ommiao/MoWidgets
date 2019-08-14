package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.TextPaint;
import android.widget.RemoteViews;

import com.orhanobut.logger.Logger;

import java.io.File;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.StringUtil;

public class ArtSentenceWidget extends BaseWidget {

    private String textL, textS;
    private int colorL, colorS;
    private int sizeL, sizeS;
    private int offset;

    private File fontFile = null;

    private int offsetInside;
    private int space;

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_art_sentence);
        textL = SPUtil.getString(context.getString(R.string.label_art_sentence) + appWidgetId + "_text_large", "");
        textS = SPUtil.getString(context.getString(R.string.label_art_sentence) + appWidgetId + "_text_small", "");
        colorL = Color.parseColor(SPUtil.getString(context.getString(R.string.label_art_sentence) + appWidgetId + "_color_large", "#ffffff"));
        colorS = Color.parseColor(SPUtil.getString(context.getString(R.string.label_art_sentence) + appWidgetId + "_color_small", "#ffffff"));
        sizeL = context.getResources().getDimensionPixelSize(R.dimen.artSentenceLargeSize);
        sizeS = context.getResources().getDimensionPixelSize(R.dimen.artSentenceSmallSize);
        offset = SPUtil.getInt(context.getString(R.string.label_art_sentence) + appWidgetId + "_offset", 0);
        String fontPath = SPUtil.getString(context.getString(R.string.label_art_sentence) + appWidgetId + "_font_path", "");
        if(!StringUtil.isEmpty(fontPath)){
            String fullPath = Environment.getExternalStorageDirectory() + "/" + fontPath;
            Logger.d(fullPath);
            fontFile = new File(fullPath);
        }
        offsetInside = SPUtil.getInt(context.getString(R.string.label_art_sentence) + appWidgetId + "_offset_inside", 0);
        space = SPUtil.getInt(context.getString(R.string.label_art_sentence) + appWidgetId + "_space", 10);
        views.setImageViewBitmap(R.id.iv_text, getBitmap());
        return views;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private Bitmap getBitmap() {

        int bitmapWidth = getTextFullWidth(), bitmapHeight = 500;
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        //canvas.drawColor(Color.BLACK);

        Paint paintL = new Paint();
        paintL.setAntiAlias(true);
        paintL.setColor(colorL);
        paintL.setTextSize(sizeL);

        if(fontFile != null && fontFile.exists()){
            Typeface typeface = Typeface.createFromFile(fontFile);
            paintL.setTypeface(typeface);
        }

        canvas.drawText(textL, getTextFullWidth() / 2 - getTextWidthL() / 2, bitmapHeight / 2 + Math.abs(paintL.ascent() + paintL.descent()) / 2, paintL);

        Paint paintS = new Paint();
        paintS.setAntiAlias(true);
        paintS.setColor(colorS);
        paintS.setTextSize(sizeS);

        if(fontFile != null && fontFile.exists()){
            Typeface typeface = Typeface.createFromFile(fontFile);
            paintS.setTypeface(typeface);
        }

        int rectHeight = getTextHeight(paintS) + space;
        Rect rect = new Rect();
        rect.left = 0;
        rect.right = getTextFullWidth();
        rect.top = bitmapHeight / 2 + offset;
        rect.bottom = rect.top + rectHeight;

        canvas.drawRect(rect, paintL);

        paintL.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

        canvas.drawRect(rect, paintL);

        float baseLine = rect.top + rectHeight / 2 + Math.abs(paintS.ascent() + paintS.descent()) / 2;
        canvas.drawText(textS, getTextFullWidth() / 2 - getTextWidthS() / 2, baseLine + offsetInside, paintS);

        return bitmap;
    }

    private int getTextFullWidth(){
        int width = getTextWidthL() > getTextWidthS() ?
                getTextWidthL() : getTextWidthS();
        return width > 0 ? width : 600;
    }

    private int getTextWidthL(){
        TextPaint textPaint = new TextPaint();
        if(fontFile != null && fontFile.exists()){
            Typeface typeface = Typeface.createFromFile(fontFile);
            textPaint.setTypeface(typeface);
        }
        textPaint.setTextSize(sizeL);
        return (int) textPaint.measureText(textL);
    }

    private int getTextWidthS(){
        TextPaint textPaint = new TextPaint();
        if(fontFile != null && fontFile.exists()){
            Typeface typeface = Typeface.createFromFile(fontFile);
            textPaint.setTypeface(typeface);
        }
        textPaint.setTextSize(sizeS);
        return (int) textPaint.measureText(textS);
    }

    private int getTextHeight(Paint paint){
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return (int) (paint.descent() - paint.ascent());
    }

}
