package cn.ommiao.mowidgets.widgets.list;

import android.widget.RemoteViewsService;

import cn.ommiao.mowidgets.requesters.BaseRequester;
import cn.ommiao.mowidgets.widgets.BaseWidget;

public abstract class BaseListWidget<S extends RemoteViewsService, R extends BaseRequester> extends BaseWidget<R> {

    protected abstract Class<S> classOfS();

}
