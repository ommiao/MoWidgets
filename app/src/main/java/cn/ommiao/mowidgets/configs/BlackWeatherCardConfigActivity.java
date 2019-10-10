package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.widgets.BlackCardWeatherWidget;

public class BlackWeatherCardConfigActivity extends BaseWeatherConfigActivity<BlackCardWeatherWidget> {

    @Override
    protected void saveConfigs(String area, String key) {
        SPUtil.put(getString(R.string.label_black_card_weather) + widgetId + "_location", area);
        SPUtil.put(getString(R.string.label_black_card_weather) + widgetId + "_key", key);
    }

    @Override
    protected BlackCardWeatherWidget getTargetWidget() {
        return new BlackCardWeatherWidget();
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_black_card_weather_settings);
    }

}
