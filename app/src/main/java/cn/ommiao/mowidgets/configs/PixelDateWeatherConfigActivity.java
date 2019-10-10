package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.widgets.PixelDateWeatherWidget;

public class PixelDateWeatherConfigActivity extends BaseWeatherConfigActivity<PixelDateWeatherWidget> {

    @Override
    protected void saveConfigs(String area, String key) {
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
