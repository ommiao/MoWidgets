package cn.ommiao.mowidgets.widgets.list;

import android.widget.RemoteViewsService;

import cn.ommiao.mowidgets.requesters.BaseRequester;
import cn.ommiao.mowidgets.widgets.TimingRefreshWidget;

public abstract class BaseTimingRefreshListWidget<S extends RemoteViewsService, R extends BaseRequester> extends TimingRefreshWidget<R> {

    protected abstract Class<S> classOfS();

}
