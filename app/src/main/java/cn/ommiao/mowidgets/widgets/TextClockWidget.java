package cn.ommiao.mowidgets.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cn.ommiao.mowidgets.App;
import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.entities.TextClockText;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.StringUtil;

public class TextClockWidget extends TimingRefreshWidget {

    private static int[] INDEX_IT_IS = new int[]{0, 1, 3, 4};
    private static int[] INDEX_MIDNIGHT = new int[]{110, 111, 112, 113, 114, 115, 116, 117};

    private static int[] INDEX_HR_ONE = new int[]{55, 56, 57};
    private static int[] INDEX_HR_TWO = new int[]{74, 75, 76};
    private static int[] INDEX_HR_THREE = new int[]{61, 62, 63, 64, 65};
    private static int[] INDEX_HR_FOUR = new int[]{66, 67, 68, 69};
    private static int[] INDEX_HR_FIVE = new int[]{70, 71, 72, 73};
    private static int[] INDEX_HR_SIX = new int[]{58, 59, 60};
    private static int[] INDEX_HR_SEVEN = new int[]{88, 89, 90, 91, 92};
    private static int[] INDEX_HR_EIGHT = new int[]{77, 78, 79, 80, 81};
    private static int[] INDEX_HR_NINE = new int[]{51, 52, 53, 54};
    private static int[] INDEX_HR_TEN = new int[]{99, 100, 101};
    private static int[] INDEX_HR_ELEVEN = new int[]{82, 83, 84, 85, 86, 87};
    private static int[] INDEX_HR_TWELVE = new int[]{93, 94, 95, 96, 97, 98};

    private static int[] INDEX_MINUTE_FIVE = new int[]{28, 29, 30, 31};
    private static int[] INDEX_MINUTE_TEN = new int[]{38, 39, 40};
    private static int[] INDEX_MINUTE_QUARTER = new int[]{13, 14, 15, 16, 17, 18, 19};
    private static int[] INDEX_MINUTE_TWENTY = new int[]{22, 23, 24, 25, 26, 27};
    private static int[] INDEX_MINUTE_HALF = new int[]{33, 34,35, 36};

    private static int[] INDEX_PAST = new int[]{44, 45, 46, 47};
    private static int[] INDEX_TO = new int[]{42, 43};
    private static int[] INDEX_O_CLOCK = new int[]{104, 105, 106, 107, 108, 109};

    private static HashMap<Integer, int[]> hoursMap = new HashMap<Integer, int[]>(){
        {
            put(0, INDEX_MIDNIGHT);
            put(1, INDEX_HR_ONE);
            put(2, INDEX_HR_TWO);
            put(3, INDEX_HR_THREE);
            put(4, INDEX_HR_FOUR);
            put(5, INDEX_HR_FIVE);
            put(6, INDEX_HR_SIX);
            put(7, INDEX_HR_SEVEN);
            put(8, INDEX_HR_EIGHT);
            put(9, INDEX_HR_NINE);
            put(10, INDEX_HR_TEN);
            put(11, INDEX_HR_ELEVEN);
            put(12, INDEX_HR_TWELVE);
            put(13, INDEX_HR_ONE);
            put(14, INDEX_HR_TWO);
            put(15, INDEX_HR_THREE);
            put(16, INDEX_HR_FOUR);
            put(17, INDEX_HR_FIVE);
            put(18, INDEX_HR_SIX);
            put(19, INDEX_HR_SEVEN);
            put(20, INDEX_HR_EIGHT);
            put(21, INDEX_HR_NINE);
            put(22, INDEX_HR_TEN);
            put(23, INDEX_HR_ELEVEN);
            put(24, INDEX_MIDNIGHT);
        }
    };

    private int colorNormal;
    private int colorHighLight = Color.parseColor("#ffffff");
    private String fontPath;

    private Paint mPaint = new Paint();

    @Override
    public RemoteViews getRemoteViews(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_text_clock);
        colorNormal = Color.parseColor(SPUtil.getString(context.getString(R.string.label_text_clock) + appWidgetId + "_color_normal", "#333333"));
        colorHighLight = Color.parseColor(SPUtil.getString(context.getString(R.string.label_text_clock) + appWidgetId + "_color_highlight", "#ffffff"));
        colorHighLight = Color.parseColor(SPUtil.getString(context.getString(R.string.label_text_clock) + appWidgetId + "_color_highlight", "#ffffff"));
        fontPath = SPUtil.getString(context.getString(R.string.label_text_clock) + appWidgetId + "_font_path", "");
        views.setImageViewBitmap(R.id.iv_text_clock, getBitmap());
        return views;
    }

    @Override
    protected String[] getCacheKeys(Context context, int appWidgetId) {
        return new String[0];
    }

    private Bitmap getBitmap(){
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(65);
        if(!StringUtil.isEmpty(fontPath)){
            fontPath = appendRootPath(fontPath);
            mPaint.setTypeface(Typeface.createFromFile(fontPath));
        }
        float perLetterWidth = mPaint.measureText("W");
        float startX = perLetterWidth / 2, startY = mPaint.descent() - mPaint.ascent() + 20;
        float perLetterSpaceH = 100;
        float perLetterSpaceV = 150;
        Bitmap bitmap = Bitmap.createBitmap(Math.round(startX + perLetterSpaceH * 10 + perLetterWidth / 2), Math.round(startY + perLetterSpaceV * 10 + 20 + mPaint.getFontMetricsInt().bottom), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        int col = 11;
        float x = startX, y = startY;
        List<TextClockText> data = getData();
        for (int i = 0; i < data.size(); i++) {
            if(i % col == 0){
                x = startX;
                if(i > 0){
                    y += perLetterSpaceV;
                }
            } else {
                x += perLetterSpaceH;
            }
            TextClockText text = data.get(i);
            mPaint.setColor(text.getColor());
            float textWidth = mPaint.measureText(text.getContent());
            canvas.drawText(text.getContent(), x - textWidth / 2, y, mPaint);
        }
        return bitmap;
    }

    private ArrayList<TextClockText> getData(){
        ArrayList<TextClockText> data = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int textResId = calendar.get(Calendar.DAY_OF_WEEK) - 1 == 5 ? R.string.text_clock_dictionary_tgif : R.string.text_clock_dictionary;
        char[] chars = App.getContext().getString(textResId).toCharArray();
        for (char aChar : chars) {
            data.add(new TextClockText(String.valueOf(aChar)));
        }

        for (TextClockText text : data) {
            text.setColor(colorNormal);
        }

        for (int index : INDEX_IT_IS) {
            data.get(index).setColor(colorHighLight);
        }

        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(minute > 32){
            hour++;
        }
        for (int i : Objects.requireNonNull(hoursMap.get(hour))) {
            data.get(i).setColor(colorHighLight);
        }
        if(minute >= 58 || minute <= 2){
            if(hour != 0 && hour != 24){
                for (int i : INDEX_O_CLOCK) {
                    data.get(i).setColor(colorHighLight);
                }
            }
        } else if(minute <= 7){
            for (int i : INDEX_PAST) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_FIVE) {
                data.get(i).setColor(colorHighLight);
            }
        } else if(minute <= 12){
            for (int i : INDEX_PAST) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_TEN) {
                data.get(i).setColor(colorHighLight);
            }
        } else if(minute <= 17){
            for (int i : INDEX_PAST) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_QUARTER) {
                data.get(i).setColor(colorHighLight);
            }
        } else if(minute <= 22){
            for (int i : INDEX_PAST) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_TWENTY) {
                data.get(i).setColor(colorHighLight);
            }
        } else if(minute <= 27){
            for (int i : INDEX_PAST) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_TWENTY) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_FIVE) {
                data.get(i).setColor(colorHighLight);
            }
        } else if(minute <= 32){
            for (int i : INDEX_PAST) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_HALF) {
                data.get(i).setColor(colorHighLight);
            }
        } else if(minute <= 37){
            for (int i : INDEX_TO) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_TWENTY) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_FIVE) {
                data.get(i).setColor(colorHighLight);
            }
        } else if(minute <= 42){
            for (int i : INDEX_TO) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_TWENTY) {
                data.get(i).setColor(colorHighLight);
            }
        } else if(minute <= 47){
            for (int i : INDEX_TO) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_QUARTER) {
                data.get(i).setColor(colorHighLight);
            }
        } else if(minute <= 52){
            for (int i : INDEX_TO) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_TEN) {
                data.get(i).setColor(colorHighLight);
            }
        } else {
            for (int i : INDEX_TO) {
                data.get(i).setColor(colorHighLight);
            }
            for (int i : INDEX_MINUTE_FIVE) {
                data.get(i).setColor(colorHighLight);
            }
        }

        return data;
    }

}
