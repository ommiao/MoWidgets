package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutFileSelectorBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.TextClockWidget;

public class TextClockConfigActivity extends BaseConfigActivity<TextClockWidget> {

    private LayoutColorSelectorBinding colorSelectorBindingNormal, colorSelectorBindingHighLight;
    private String colorNormal, colorHighLight;

    private LayoutFileSelectorBinding fileSelectorBindingFont;
    private String fontPath;

    @Override
    protected TextClockWidget getTargetWidget() {
        return new TextClockWidget();
    }

    @Override
    protected void initConfigViews() {
        colorSelectorBindingNormal = getColorSelectorBinding("常态文字颜色", "333333");
        colorSelectorBindingHighLight = getColorSelectorBinding("高亮文字颜色");
        addConfigView(colorSelectorBindingNormal.getRoot());
        addConfigView(colorSelectorBindingHighLight.getRoot());
        fileSelectorBindingFont = getFontSelectorBinding("字体");
        addConfigView(fileSelectorBindingFont.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_text_clock_settings);
    }

    @Override
    protected boolean isDataValid() {
        colorNormal = colorSelectorBindingNormal.etColor.getText().toString().trim();
        colorHighLight = colorSelectorBindingHighLight.etColor.getText().toString().trim();
        if(!isColorValid(colorNormal)){
            ToastUtil.shortToast("请输入有效的文字颜色(常态文字颜色)");
            return false;
        }
        if(!isColorValid(colorHighLight)){
            ToastUtil.shortToast("请输入有效的文字颜色(高亮文字颜色)");
            return false;
        }
        fontPath = fileSelectorBindingFont.tvFileName.getText().toString().trim();
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_text_clock) + widgetId + "_color_normal", "#" + colorNormal);
        SPUtil.put(getString(R.string.label_text_clock) + widgetId + "_color_highlight", "#" + colorHighLight);
        SPUtil.put(getString(R.string.label_text_clock) + widgetId + "_font_path", fontPath);
    }

    @Override
    protected boolean needReadStorage() {
        return true;
    }
}
