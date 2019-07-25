package cn.ommiao.mowidgets;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import cn.ommiao.logger.SimpleLogger;
import cn.ommiao.network.Client;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SimpleLogger.initLogger();
        Client.initNetwork();
    }

    public static Context getContext() {
        return context;
    }
}
