package cn.ommiao.mowidgets.utils;

import android.widget.Toast;

import cn.ommiao.mowidgets.App;

public class ToastUtil {

    public static void shortToast(String msg){
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void shortToast(int strId){
        Toast.makeText(App.getContext(), App.getContext().getString(strId), Toast.LENGTH_SHORT).show();
    }

}
