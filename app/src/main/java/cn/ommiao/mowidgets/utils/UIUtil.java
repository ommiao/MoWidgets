package cn.ommiao.mowidgets.utils;

import android.content.res.Resources;

public class UIUtil {

    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

}
