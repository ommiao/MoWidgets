package cn.ommiao.mowidgets.requesters;

import android.appwidget.AppWidgetManager;
import android.content.Context;

import cn.ommiao.mowidgets.widgets.BaseWidget;
import cn.ommiao.network.BaseRequest;
import cn.ommiao.network.RequestCallBack;
import cn.ommiao.network.RequestInBase;
import cn.ommiao.network.RequestOutBase;
import okhttp3.ResponseBody;
import retrofit2.Response;

public abstract class BaseRequester<W extends BaseWidget> {

    protected Context context;
    private AppWidgetManager appWidgetManager;
    protected int widgetId;
    private W widget;

    public BaseRequester(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        this.context = context;
        this.appWidgetManager = appWidgetManager;
        this.widgetId = widgetId;
        widget = getTargetWidget();
    }

    protected abstract W getTargetWidget();

    public abstract void request();

    protected void notifyDataRequested(){
        appWidgetManager.updateAppWidget(widgetId, widget.getRemoteViews(context, appWidgetManager, widgetId));
    }

    protected  <IN extends RequestInBase, OUT extends RequestOutBase> void newCall(BaseRequest<IN, OUT> request, IN in, RequestCallBack<OUT> callBack) {
        request.params(in).build(arrangeCallback(request.getUrl(), in, callBack)).call();
    }

    private <OUT extends RequestOutBase> RequestCallBack<OUT> arrangeCallback(final String url, final RequestInBase in, final RequestCallBack<OUT> callBack) {
        return new RequestCallBack<OUT>() {
            @Override
            public void onSuccess(OUT result, String str, Response<ResponseBody> res) {
                callBack.onSuccess(result, str, res);
            }

            @Override
            public void onError(OUT result, int code, String message, Throwable err) {
                callBack.onError(result, code, message, err);
            }

            @Override
            public void onCancel() {
                callBack.onCancel();
            }
        };
    }

}
