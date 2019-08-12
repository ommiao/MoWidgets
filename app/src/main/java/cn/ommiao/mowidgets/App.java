package cn.ommiao.mowidgets;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import java.io.File;

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
        initFileDir();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initFileDir() {
        File fontFile = getExternalFilesDir("font");
        if (fontFile != null){
            File file = new File(fontFile.getAbsolutePath());
            if(!file.exists()){
                file.mkdirs();
            }
        }
    }

    public static Context getContext() {
        return context;
    }
}
