package cn.ommiao.mowidgets.widgets.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;

import com.orhanobut.logger.Logger;

import java.util.Calendar;
import java.util.HashMap;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.entities.MonthCalendarDay;
import cn.ommiao.mowidgets.utils.SPUtil;

public class MonthCalendarService extends BaseRemoteViewsService {

    @Override
    protected RemoteViewsFactory getFactory(Intent intent) {
        return new MonthCalendarFactory(this, intent);
    }

    class MonthCalendarFactory extends BaseRemoteViewsService.BaseFactory<MonthCalendarDay> {

        private MonthCalendarWidget.Theme theme;

        private final String[] WEEKS = {"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"};

        private final HashMap<MonthCalendarDay.ViewType, Integer> LAYOUT_MAP = new HashMap<MonthCalendarDay.ViewType, Integer>(){
            {
                put(MonthCalendarDay.ViewType.WEEK_ROW, R.layout.item_month_calendar_week_row);
                put(MonthCalendarDay.ViewType.NONE, R.layout.item_month_calendar_week_row);
                put(MonthCalendarDay.ViewType.DAY, R.layout.item_calendar_day);
                put(MonthCalendarDay.ViewType.WEEK_COL, R.layout.item_month_calendar_week_col);
            }
        };

        MonthCalendarFactory(Context context, Intent intent) {
            super(context, intent);
            theme = MonthCalendarWidget.Theme.valueOf(SPUtil.getString(context.getString(R.string.label_month_calendar) + widgetId + "_theme", "WHITE"));
        }

        @Override
        protected void initData() {
            mData.clear();
            MonthCalendarDay none = new MonthCalendarDay();
            none.setViewType(MonthCalendarDay.ViewType.NONE);
            mData.add(none);
            for(int i = 1; i < 8; i++){
                MonthCalendarDay monthCalendarDay = new MonthCalendarDay();
                monthCalendarDay.setViewType(MonthCalendarDay.ViewType.WEEK_ROW);
                monthCalendarDay.setData(WEEKS[i-1]);
                if(i - 1 == getWeekNo()){
                    monthCalendarDay.setHighLight(true);
                }
                mData.add(monthCalendarDay);
            }
            int weekOfYearRowOne = getWeekOfYearRowOne();
            int weekOfYear = getWeekOfYear();
            int dayStart = getFirstDayWeek();
            int allDays = getDaysOfThisMonth();
            int day = 1;
            int today = getDayOfMonth();
            for(int i = 8; i < 56; i++){
                MonthCalendarDay monthCalendarDay = new MonthCalendarDay();
                if( i % 8 == 0){
                    monthCalendarDay.setViewType(MonthCalendarDay.ViewType.WEEK_COL);
                    monthCalendarDay.setData(String.valueOf(weekOfYearRowOne));
                    if(weekOfYearRowOne >= weekOfYear){
                        monthCalendarDay.setHighLight(true);
                    }
                    weekOfYearRowOne++;
                } else {
                    monthCalendarDay.setViewType(MonthCalendarDay.ViewType.DAY);
                    if(i - 8 >= dayStart && day <= allDays){
                        monthCalendarDay.setData(String.valueOf(day));
                        if(day == today){
                            monthCalendarDay.setHighLight(true);
                        }
                        day++;
                    }
                }
                mData.add(monthCalendarDay);
            }
        }

        @Override
        protected int getItemLayoutId(int i) {
            Integer id = LAYOUT_MAP.get(mData.get(i).getViewType());
            return id == null ? R.layout.item_month_calendar_week_row : id;
        }

        @Override
        protected RemoteViews buildRemoteViews(int pos, RemoteViews views, MonthCalendarDay bean) {

            if(bean.getViewType() == MonthCalendarDay.ViewType.WEEK_ROW ||
                bean.getViewType() == MonthCalendarDay.ViewType.WEEK_COL){
                views.setTextViewText(R.id.tv_content, bean.getData());
                if(bean.isHighLight()){
                    views.setTextColor(R.id.tv_content, theme.getWeekHighLightColor());
                } else {
                    views.setTextColor(R.id.tv_content, theme.getWeekNormalColor());
                }
            } else if(bean.getViewType() == MonthCalendarDay.ViewType.DAY){
                views.setTextViewText(R.id.tv_content, bean.getData());
                if(bean.isHighLight()){
                    String colorDateNow = SPUtil.getString(getString(R.string.label_month_calendar) + widgetId + "_color_date_now", "#ffffff");
                    views.setTextColor(R.id.tv_content, Color.parseColor(colorDateNow));
                    String bgColor = SPUtil.getString(getString(R.string.label_month_calendar) + widgetId + "_color_main", "#ff0000");
                    views.setInt(R.id.iv_day_bg, "setColorFilter", Color.parseColor(getColorByHex(bgColor)));
                    views.setInt(R.id.iv_day_bg, "setAlpha", getAlphaByHex(bgColor));
                } else {
                    views.setTextColor(R.id.tv_content, theme.getDayNormalColor());
                    String bgColor = theme.getDayNormalBgColor();
                    views.setInt(R.id.iv_day_bg, "setColorFilter", Color.parseColor(getColorByHex(bgColor)));
                    views.setInt(R.id.iv_day_bg, "setAlpha", getAlphaByHex(bgColor));
                }
            }

            return views;
        }

        @Override
        public void onDataSetChanged() {
            super.onDataSetChanged();
            mData.clear();
            initData();
        }

        private int getWeekNo(){
            Calendar calendar = Calendar.getInstance();
            return calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        private int getWeekOfYearRowOne(){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 1);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        }

        private int getWeekOfYear(){
            Calendar calendar = Calendar.getInstance();
            return calendar.get(Calendar.WEEK_OF_YEAR);
        }

        private int getDayOfMonth(){
            Calendar calendar = Calendar.getInstance();
            return calendar.get(Calendar.DAY_OF_MONTH);
        }

        private int getFirstDayWeek(){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 1);
            return calendar.get(Calendar.DAY_OF_WEEK);
        }

        private int getDaysOfThisMonth(){
            Calendar calendar = Calendar.getInstance();
            return calendar.getActualMaximum(Calendar.DATE);
        }

        @Override
        public int getViewTypeCount() {
            return 4;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        private String getColorByHex(String hex){
            String colorStr;
            if(hex.length() == 9){
                colorStr = "#" + hex.substring(3);
            } else {
                colorStr = hex;
            }
            return colorStr;
        }

        private int getAlphaByHex(String hex){
            int alpha = 255;
            if(hex.length() == 9){
                alpha = Integer.parseInt(hex.substring(1, 3), 16);
            }
            return alpha;
        }
    }

}
