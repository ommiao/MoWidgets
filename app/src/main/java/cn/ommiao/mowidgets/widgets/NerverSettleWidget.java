package cn.ommiao.mowidgets.widgets;

import android.content.Context;
import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;

public class NerverSettleWidget extends BaseWidget {
    @Override
    protected RemoteViews update(Context context) {
        return new RemoteViews(context.getPackageName(), R.layout.widget_clock_nerver_settle);
    }
}
