package cn.ommiao.mowidgets.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void saveViewPicture(View view, String fileName){

        new Thread(() -> {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            File dir = view.getContext().getExternalFilesDir("img");
            if(dir != null && !dir.exists()){
                dir.mkdirs();
            }
            if(dir != null){
                File file = new File(dir.getAbsolutePath() + "/" + fileName);
                try {
                    file.createNewFile();
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
