package cn.ommiao.mowidgets.configs;

import android.graphics.Color;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.GoogleWordWidget;

public class GoogleWordConfigActivity extends BaseConfigActivity<GoogleWordWidget> {

    private LayoutColorSelectorBinding colorSelectorBinding;
    private String colorStr;

    @Override
    protected void initConfigViews() {
        colorSelectorBinding = getColorSelectorBinding(getString(R.string.txt_color));
        addConfigView(colorSelectorBinding.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_google_word_settings);
    }

    @Override
    protected boolean isDataValid() {
        colorStr = colorSelectorBinding.etColor.getText().toString().trim();
        if(isColorValid(colorStr)){
            return true;
        } else {
            ToastUtil.shortToast(R.string.please_input_valid_color);
            return false;
        }
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_google_word) + widgetId, "#" + colorStr);
    }

    @Override
    protected GoogleWordWidget getTargetWidget() {
        return new GoogleWordWidget();
    }

}
