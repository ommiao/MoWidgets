package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.JapaneseClockWidget;

public class JapaneseClockConfigActivity extends BaseConfigActivity<JapaneseClockWidget> {

    private LayoutColorSelectorBinding bindingColorIts, bindingColorTime;
    private String colorIts, colorTime;

    private LayoutEdittextBinding bindingFontPath;
    private String fontPath;

    @Override
    protected JapaneseClockWidget getTargetWidget() {
        return new JapaneseClockWidget();
    }

    @Override
    protected void initConfigViews() {
        bindingColorIts = getColorSelectorBinding("今は颜色");
        bindingColorTime = getColorSelectorBinding("时间颜色");
        addConfigView(bindingColorIts.getRoot());
        addConfigView(bindingColorTime.getRoot());
        bindingFontPath = getEdittextBinding("字体路径");
        bindingFontPath.et.setHint("填写根目录的相对路径如font/roboto.ttf");
        addConfigView(bindingFontPath.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_japanese_clock_settings);
    }

    @Override
    protected boolean isDataValid() {
        colorIts = "#" + bindingColorIts.etColor.getText().toString().trim();
        colorTime = "#" + bindingColorTime.etColor.getText().toString().trim();
        fontPath = bindingFontPath.et.getText().toString().trim();
        if(fontPath.length() > 0){
            if (!fontPath.endsWith(".ttf") && !fontPath.endsWith(".otf")){
                ToastUtil.shortToast("请输入正确的字体文件路径");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_japanese_clock) + widgetId + "_color_its", colorIts);
        SPUtil.put(getString(R.string.label_japanese_clock) + widgetId  + "_color_time", colorTime);
        SPUtil.put(getString(R.string.label_japanese_clock) + widgetId + "_font_path", fontPath);
    }

    @Override
    protected boolean isSharedWidget() {
        return true;
    }

    @Override
    protected boolean needReadStorage() {
        return true;
    }
}
