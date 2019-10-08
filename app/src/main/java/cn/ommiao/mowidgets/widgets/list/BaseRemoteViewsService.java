package cn.ommiao.mowidgets.widgets.list;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.LayoutRes;

import java.util.ArrayList;

import cn.ommiao.bean.JavaBean;

public abstract class BaseRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return getFactory(intent);
    }

    protected abstract RemoteViewsFactory getFactory(Intent intent);

    public abstract static class BaseFactory<E extends JavaBean> implements RemoteViewsService.RemoteViewsFactory {

        protected Context mContext;
        protected ArrayList<E> mData = new ArrayList<>();
        protected int widgetId;

        BaseFactory(Context context, Intent intent){
            this.mContext = context;
            widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            //根据widget id初始化数据??
            initData();
        }

        protected abstract void initData();

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            mData.clear();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), getItemLayoutId(i));
            return buildRemoteViews(views, mData.get(i));
        }

        protected abstract @LayoutRes int getItemLayoutId(int i);
        protected abstract RemoteViews buildRemoteViews(RemoteViews views, E bean);

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
