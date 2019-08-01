package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.PaperNotesWidget;

public class PaperNotesConfigActivity extends BaseConfigActivity<PaperNotesWidget> {

    private LayoutColorSelectorBinding selectorBindingBottom, selectorBindingTop, selectorBindingPin, selectorBindingText;
    private String colorStrBottom, colorStrTop, colorStrPin, colorStrText;

    private LayoutEdittextBinding edittextBinding;
    private String text;

    @Override
    protected PaperNotesWidget getTargetWidget() {
        return new PaperNotesWidget();
    }

    @Override
    protected void initConfigViews() {
        selectorBindingBottom = getColorSelectorBinding("底层颜色", "fc5656");
        selectorBindingTop = getColorSelectorBinding("顶层颜色", "ace7ff");
        selectorBindingPin = getColorSelectorBinding("别针颜色", "e8ff4a");
        selectorBindingText = getColorSelectorBinding("文字颜色", "555555");
        addConfigView(selectorBindingBottom.getRoot());
        addConfigView(selectorBindingTop.getRoot());
        addConfigView(selectorBindingPin.getRoot());
        addConfigView(selectorBindingText.getRoot());
        edittextBinding = getEdittextBinding("文字内容");
        edittextBinding.et.setHint("写点儿什么吧，自行控制字数");
        addConfigView(edittextBinding.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_paper_notes_settings);
    }

    @Override
    protected boolean isDataValid() {
        colorStrBottom = selectorBindingBottom.etColor.getText().toString();
        colorStrTop = selectorBindingTop.etColor.getText().toString();
        colorStrPin = selectorBindingPin.etColor.getText().toString();
        colorStrText = selectorBindingText.etColor.getText().toString();
        if(!isColorValid(colorStrBottom)){
            ToastUtil.shortToast("请输入正确的颜色值（底部颜色）");
            return false;
        }
        if(!isColorValid(colorStrTop)){
            ToastUtil.shortToast("请输入正确的颜色值（顶部颜色）");
            return false;
        }
        if(!isColorValid(colorStrPin)){
            ToastUtil.shortToast("请输入正确的颜色值（别针颜色）");
            return false;
        }
        if(!isColorValid(colorStrText)){
            ToastUtil.shortToast("请输入正确的颜色值（文字颜色）");
            return false;
        }
        text = edittextBinding.et.getText().toString();
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_paper_notes) + widgetId + "_color_bottom", "#" + colorStrBottom);
        SPUtil.put(getString(R.string.label_paper_notes) + widgetId + "_color_top", "#" + colorStrTop);
        SPUtil.put(getString(R.string.label_paper_notes) + widgetId + "_color_pin", "#" + colorStrPin);
        SPUtil.put(getString(R.string.label_paper_notes) + widgetId + "_color_text", "#" + colorStrText);
        SPUtil.put(getString(R.string.label_paper_notes) + widgetId + "_text", text);
    }

    @Override
    protected boolean isSharedWidget() {
        return true;
    }

}
