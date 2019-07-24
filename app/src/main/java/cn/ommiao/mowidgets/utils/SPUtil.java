package cn.ommiao.mowidgets.utils;

import android.content.Context;
import android.content.SharedPreferences;

import cn.ommiao.mowidgets.App;

public class SPUtil {

    interface Executable {
        void excute(SharedPreferences.Editor editor);
    }

    private static boolean executeWithEditor(Executable ex) {
        SharedPreferences.Editor editor = getSp().edit();
        ex.excute(editor);
        return editor.commit();
    }

    public static void clear() {
        executeWithEditor(SharedPreferences.Editor::clear);
    }

    public static boolean put(final String key, final boolean value) {
        return executeWithEditor(editor -> editor.putBoolean(key, value));
    }

    public static boolean put(final String key, final int value) {
        return executeWithEditor(editor -> editor.putInt(key, value));
    }

    public static boolean put(final String key, final long value) {
        return executeWithEditor(editor -> editor.putLong(key, value));
    }

    public static boolean put(final String key, final float value) {
        return executeWithEditor(editor -> editor.putFloat(key, value));
    }

    public static boolean put(final String key, final String value) {
        return executeWithEditor(editor -> editor.putString(key, value));
    }

    public static void remove(final String key) {
        executeWithEditor(editor -> editor.remove(key));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSp().getBoolean(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return getSp().getInt(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return getSp().getLong(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        return getSp().getFloat(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return getSp().getString(key, defaultValue);
    }

    private static SharedPreferences getSp() {
        return App.getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor() {
        return getSp().edit();
    }

}
