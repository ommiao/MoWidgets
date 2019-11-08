package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.PixelDateWeatherWidget;

public class PixelDateWeatherConfigActivity extends BaseWeatherConfigActivity<PixelDateWeatherWidget> {

    private LayoutColorSelectorBinding colorSelectorBinding;
    private String color;

    @Override
    protected void initConfigViews() {
        colorSelectorBinding = getColorSelectorBinding("颜色");
        addConfigView(colorSelectorBinding.getRoot());
        super.initConfigViews();
    }

    @Override
    protected boolean isDataValid() {
        color = colorSelectorBinding.etColor.getText().toString();
        if(!isColorValid(color)){
            ToastUtil.shortToast("请输入有效的文字颜色");
            return false;
        }
        return super.isDataValid();
    }

    @Override
    protected void saveConfigs(String area, String key) {
        SPUtil.put(getString(R.string.label_pixel_date_weather) + widgetId + "_color", "#" + color);
        SPUtil.put(getString(R.string.label_pixel_date_weather) + widgetId + "_location", area);
        SPUtil.put(getString(R.string.label_pixel_date_weather) + widgetId + "_key", key);
    }

    @Override
    protected PixelDateWeatherWidget getTargetWidget() {
        return new PixelDateWeatherWidget();
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_pixel_date_weather_settings);
    }

}
