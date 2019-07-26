package cn.ommiao.network;

import okhttp3.ResponseBody;
import retrofit2.Response;


public interface RequestCallBack<D extends RequestOutBase> {
    void onSuccess(D res, String str, Response<ResponseBody> result);
    void onError(D res, int code, String message, Throwable err);
    void onCancel();
}
