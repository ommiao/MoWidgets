package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.widgets.ColorosClockWidget;

public class ColorosClockConfigActivity extends WeatherNowConfigActivity<ColorosClockWidget> {

    private LayoutEdittextBinding bindingTopPadding, bindingLeftPadding;
    private int topPadding, leftPadding;

    @Override
    protected ColorosClockWidget getTargetWidget() {
        return new ColorosClockWidget();
    }

    @Override
    protected void initConfigViews() {
        super.initConfigViews();
        bindingTopPadding = getNumberEdittextBinding("上方边距", PADDING_MAX_LENGTH);
        bindingLeftPadding = getNumberEdittextBinding("左侧边距", PADDING_MAX_LENGTH);
        addConfigView(bindingTopPadding.getRoot());
        addConfigView(bindingLeftPadding.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_coloros_clock_settings);
    }

    @Override
    protected boolean isDataValid() {
        if(!super.isDataValid()){
            return false;
        }
        String topPaddingStr = bindingTopPadding.et.getText().toString().trim();
        String leftPaddingStr = bindingLeftPadding.et.getText().toString().trim();
        if(!isNumberValid(topPaddingStr)){
            topPaddingStr = "0";
        }
        if(!isNumberValid(leftPaddingStr)){
            leftPaddingStr = "0";
        }
        topPadding = Integer.parseInt(topPaddingStr);
        leftPadding = Integer.parseInt(leftPaddingStr);
        return true;
    }

    @Override
    protected void saveConfigs(String area, String key) {
        SPUtil.put(getString(R.string.label_coloros_clock) + widgetId + "_location", area);
        SPUtil.put(getString(R.string.label_coloros_clock) + widgetId + "_key", key);
        SPUtil.put(getString(R.string.label_coloros_clock) + widgetId + "_top_padding", topPadding);
        SPUtil.put(getString(R.string.label_coloros_clock) + widgetId + "_left_padding", leftPadding);
    }

}
