package cn.ommiao.mowidgets;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

import cn.ommiao.mowidgets.widgets.BaseWidget;

import static cn.ommiao.mowidgets.Constant.EXTRA_WIDGET_CLASS;

public class RefreshActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        Logger.d(id);
        if(id != AppWidgetManager.INVALID_APPWIDGET_ID){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            String clazz = intent.getStringExtra(EXTRA_WIDGET_CLASS);
            Logger.d(clazz);
            try {
                assert clazz != null;
                BaseWidget widget = (BaseWidget) Class.forName(clazz).newInstance();
                if(widget.needRequestData()){
                    widget.getDataRequester(this, appWidgetManager, id).request();
                } else {
                    appWidgetManager.updateAppWidget(id, widget.getRemoteViews(this, appWidgetManager, id));
                }
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
