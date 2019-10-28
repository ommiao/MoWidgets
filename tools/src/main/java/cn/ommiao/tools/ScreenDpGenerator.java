package cn.ommiao.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class ScreenDpGenerator {
    private final static String rootPath = ".\\tools\\src\\main\\res\\values-sw{0}dp\\";

    private final static String WTemplate = "   <dimen name=\"x{0}\">{1}dp</dimen>\n";
    private final static String _WTemplate = "   <dimen name=\"_x{0}\">-{1}dp</dimen>\n";

    private final static float dw = 1080;


    public static void main(String[] args) {
        screenString(320);
        screenString(340);
        screenString(360);
        screenString(370);
        screenString(380);
        screenString(390);
        screenString(400);
        screenString(410);
        screenString(420);
    }

    public static void screenString(int w) {

        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<resources>\n");
        float dpi = dw / w * 160;
        for (int i = 1; i <= dw; i++) {
            sb.append(WTemplate.replace("{0}", i + "").replace("{1}",
                    change(i/(dpi/160)) + ""));
            sb.append(_WTemplate.replace("{0}", i + "").replace("{1}",
                    change(i/(dpi/160)) + ""));
        }
//        sb.append(WTemplate.replace("{0}", "320").replace("{1}", w + ""));
        sb.append("</resources>");

        String path = rootPath.replace("{0}", w + "");
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File layxFile = new File(path + "dimens.xml");
//        File layyFile = new File(path + "lay_y.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
            pw.print(sb.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }
}
