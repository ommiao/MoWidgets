package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.RemoteViews;

import com.orhanobut.logger.Logger;

import java.io.File;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.StringUtil;

public class JapaneseClockWidget extends TimingRefreshWidget {

    private int colorIts, colorTime;
    private String textIts = "今は";
    private int textSize = 100;
    private File fontFile = null;

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock_japanese);
        colorIts = Color.parseColor(SPUtil.getString(context.getString(R.string.label_japanese_clock) + appWidgetId + "_color_its", "#ffffff"));
        colorTime = Color.parseColor(SPUtil.getString(context.getString(R.string.label_japanese_clock) + appWidgetId + "_color_time", "#ffffff"));
        String fontPath = SPUtil.getString(context.getString(R.string.label_japanese_clock) + appWidgetId + "_font_path", "");
        Logger.d(fontPath);
        if(!StringUtil.isEmpty(fontPath)){
            String fullPath =  appendRootPath(fontPath);
            fontFile = new File(fullPath);
        }
        views.setImageViewBitmap(R.id.iv_clock, getBitmap());
        return views;
    }

    private Bitmap getBitmap(){
        int width = 600, height = 600;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        paint.setColor(colorIts);

        if(fontFile != null && fontFile.exists()){
            Typeface typeface = Typeface.createFromFile(fontFile);
            paint.setTypeface(typeface);
        }

        int oX = width - textSize * 2;
        int oY = textSize;

        int x1 = oX;
        int y1 = oY;

        for (char c : textIts.toCharArray()) {
            canvas.drawText(String.valueOf(c), x1, y1, paint);
            y1 += textSize + textSize / 3;
        }

        paint.setColor(colorTime);

        int x2 = oX - textSize - textSize / 2;
        int y2 = oY;

        for (char c : getHourJa().toCharArray()){
            canvas.drawText(String.valueOf(c), x2, y2, paint);
            y2 += textSize + textSize / 3;
        }

        int x3 = oX - (textSize + textSize / 2) * 2;
        int y3 = oY;

        for (char c : getMinuteJa().toCharArray()){
            canvas.drawText(String.valueOf(c), x3, y3, paint);
            y3 += textSize + textSize / 3;
        }

        return bitmap;
    }

    private String getHourJa(){
        String result;
        int hour = getHour();
        if(hour > 20){
            result = "二十" + numberToTextJa(hour - 20);
        } else if(hour == 20){
            result = "二十";
        } else if(hour > 10){
            result = "十" + numberToTextJa(hour - 10);
        } else {
            result = numberToTextJa(hour);
        }
        return result + "時";
    }

    private String getMinuteJa(){
        String result;
        int minute = getMinute();
        if(minute > 50){
            result = "五十" + numberToTextJa(minute - 50);
        } else if(minute == 50){
            result = "五十";
        } else if(minute > 40){
            result = "四十" + numberToTextJa(minute - 40);
        } else if(minute == 40){
            result = "四十";
        } else if(minute > 30){
            result = "三十" + numberToTextJa(minute - 30);
        } else if(minute == 30){
            result = "三十";
        } else if(minute > 20){
            result = "二十" + numberToTextJa(minute - 20);
        } else if(minute == 20){
            result = "二十";
        } else if(minute > 10){
            result = "十" + numberToTextJa(minute - 10);
        }  else if(minute == 10){
            result = "十";
        } else if(minute > 0){
            result = "零" + numberToTextJa(minute);
        } else {
            result = "零";
        }
        return result + "分";
    }

    private String numberToTextJa(int number){
        switch (number){
            case 0:
                return "零";
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
            case 10:
                return "十";
        }
        return "Null...";
    }

}
