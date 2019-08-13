package cn.ommiao.mowidgets.configs;

import android.text.InputFilter;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.StringUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.ArtSentenceWidget;

public class ArtSentenceConfigActivity extends BaseConfigActivity<ArtSentenceWidget> {

    private LayoutColorSelectorBinding selectorBindingL, selectorBindingS;
    private String colorL, colorS;

    private LayoutEdittextBinding edittextBindingL, edittextBindingS;
    private String textL, textS;

    private LayoutEdittextBinding offsetBinding;
    private int offset;

    private LayoutEdittextBinding bindingFontPath;
    private String fontPath;

    @Override
    protected ArtSentenceWidget getTargetWidget() {
        return new ArtSentenceWidget();
    }

    @Override
    protected void initConfigViews() {
        edittextBindingL = getEdittextBinding("文字内容(大)", 10);
        edittextBindingS = getEdittextBinding("文字内容(小)", 50);
        edittextBindingL.et.setText("爱情");
        edittextBindingS.et.setText("真的是一场龙卷风啊");
        addConfigView(edittextBindingL.getRoot());
        addConfigView(edittextBindingS.getRoot());
        selectorBindingL = getColorSelectorBinding("文字颜色(大)");
        selectorBindingS = getColorSelectorBinding("文字颜色(小)");
        addConfigView(selectorBindingL.getRoot());
        addConfigView(selectorBindingS.getRoot());
        offsetBinding = getNumberEdittextBinding("小字偏移");
        offsetBinding.et.setHint("正值向下，负值向上偏移，不填为默认位置");
        offsetBinding.et.setFilters(getNumberInputFilters(true, 4));
        addConfigView(offsetBinding.getRoot());
        bindingFontPath = getEdittextBinding("字体路径");
        bindingFontPath.et.setHint("填写根目录的相对路径如font/roboto.ttf");
        addConfigView(bindingFontPath.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_art_sentence_settings);
    }

    @Override
    protected boolean isDataValid() {
        textL = edittextBindingL.et.getText().toString().trim();
        textS = edittextBindingS.et.getText().toString().trim();
        colorL = selectorBindingL.etColor.getText().toString();
        colorS = selectorBindingS.etColor.getText().toString();
        fontPath = bindingFontPath.et.getText().toString().trim();
        String offsetStr = offsetBinding.et.getText().toString().trim();
        if(!isNumberValid(offsetStr)){
            offsetStr = "0";
        }
        offset = Integer.parseInt(offsetStr);
        if(StringUtil.isEmpty(textL)){
            ToastUtil.shortToast("请输入文字内容(大)");
            return false;
        }
        if(StringUtil.isEmpty(textS)){
            ToastUtil.shortToast("请输入文字内容(小)");
            return false;
        }
        if(!isColorValid(colorL)){
            ToastUtil.shortToast("请输入有效的文字颜色(大)");
            return false;
        }
        if(!isColorValid(colorS)){
            ToastUtil.shortToast("请输入有效的文字颜色(小)");
            return false;
        }
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
        SPUtil.put(getString(R.string.label_art_sentence) + widgetId + "_text_large", textL);
        SPUtil.put(getString(R.string.label_art_sentence) + widgetId + "_text_small", textS);
        SPUtil.put(getString(R.string.label_art_sentence) + widgetId + "_color_large", "#" + colorL);
        SPUtil.put(getString(R.string.label_art_sentence) + widgetId + "_color_small", "#" + colorS);
        SPUtil.put(getString(R.string.label_art_sentence) + widgetId + "_offset", offset);
        SPUtil.put(getString(R.string.label_art_sentence) + widgetId + "_font_path", fontPath);
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
