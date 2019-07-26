package cn.ommiao.mowidgets.utils;

public class StringUtil {

    public static boolean isEmpty(String str){
        if(str != null){
            return str.trim().length() == 0;
        }
        return true;
    }

}
