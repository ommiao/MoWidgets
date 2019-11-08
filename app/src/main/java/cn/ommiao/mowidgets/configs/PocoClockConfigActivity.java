package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.PocoClockWidget;

public class PocoClockConfigActivity extends BaseConfigActivity<PocoClockWidget> {

    private LayoutColorSelectorBinding colorSelectorBinding;
    private String color;

    @Override
    protected PocoClockWidget getTargetWidget() {
        return new PocoClockWidget();
    }

    @Override
    protected void initConfigViews() {
        colorSelectorBinding = getColorSelectorBinding("颜色");
        addConfigView(colorSelectorBinding.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_poco_clock_settings);
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
        SPUtil.put(getString(R.string.label_poco_clock) + widgetId + "_color", "#" + color);
    }

}
