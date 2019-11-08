package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.HydrogenClockWidget;

public class HydrogenClockConfigActivity extends BaseConfigActivity<HydrogenClockWidget> {

    private LayoutColorSelectorBinding colorSelectorBinding;
    private String color;

    @Override
    protected HydrogenClockWidget getTargetWidget() {
        return new HydrogenClockWidget();
    }

    @Override
    protected void initConfigViews() {
        colorSelectorBinding = getColorSelectorBinding("颜色");
        addConfigView(colorSelectorBinding.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_hydrogen_clock_settings);
    }

    @Override
    protected boolean isDataValid() {
        color = colorSelectorBinding.etColor.getText().toString();
        if(!isColorValid(color)){
            ToastUtil.shortToast("请输入有效的文字颜色");
            return false;
        }
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_hydrogen_clock) + widgetId + "_color", "#" + color);
    }

}
