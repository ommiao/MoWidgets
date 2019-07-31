package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.widgets.ColorosClockWidget;

public class ColorosClockConfigActivity extends WeatherNowConfigActivity<ColorosClockWidget> {

    @Override
    protected ColorosClockWidget getTargetWidget() {
        return new ColorosClockWidget();
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_coloros_clock_settings);
    }

    @Override
    protected void saveConfigs(String area, String key) {
        SPUtil.put(getString(R.string.label_coloros_clock) + widgetId + "_location", area);
        SPUtil.put(getString(R.string.label_coloros_clock) + widgetId + "_key", key);
    }

}
