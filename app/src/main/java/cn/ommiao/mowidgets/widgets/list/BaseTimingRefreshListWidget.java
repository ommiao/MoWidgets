package cn.ommiao.mowidgets.widgets.list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViewsService;

import cn.ommiao.mowidgets.requesters.BaseRequester;
import cn.ommiao.mowidgets.widgets.TimingRefreshWidget;

public abstract class BaseTimingRefreshListWidget<S extends RemoteViewsService, R extends BaseRequester> extends TimingRefreshWidget<R> {

    protected abstract Class<S> classOfS();

    protected Intent getRefreshListIntent(Context context, int widgetId){
        Intent intent = new Intent(context, classOfS());
        intent.setData(Uri.fromParts("content", String.valueOf(widgetId), null));
        return intent;
    }

}
