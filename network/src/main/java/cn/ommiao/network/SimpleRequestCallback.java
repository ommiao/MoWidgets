package cn.ommiao.network;

import okhttp3.ResponseBody;
import retrofit2.Response;

public abstract class SimpleRequestCallback<D extends RequestOutBase> implements RequestCallBack<D> {

    @Override
    public void onSuccess(D res, String str, Response<ResponseBody> result) {
        onSuccess(res);
    }

    @Override
    public void onError(int code, String message, Throwable err) {
        onError(code, message);
    }

    @Override
    public void onCancel() {

    }

    public abstract void onSuccess(D out);

    public abstract void onError(int code, String error);

}
