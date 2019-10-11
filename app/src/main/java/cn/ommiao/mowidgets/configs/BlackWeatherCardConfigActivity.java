package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutAlignmentBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.widgets.BlackCardWeatherWidget;
import cn.ommiao.mowidgets.widgets.others.RadioTextView;

public class BlackWeatherCardConfigActivity extends BaseWeatherConfigActivity<BlackCardWeatherWidget> {

    private String alignment;

    @Override
    protected void initConfigViews() {
        super.initConfigViews();
        LayoutAlignmentBinding alignmentBinding = getAlignmentBinding("垂直对齐", true);
        addConfigView(alignmentBinding.getRoot());
    }

    @Override
    protected boolean isDataValid() {
        alignment = RadioTextView.getCheckedString("垂直对齐");
        return super.isDataValid();
    }

    @Override
    protected void saveConfigs(String area, String key) {
        SPUtil.put(getString(R.string.label_black_card_weather) + widgetId + "_location", area);
        SPUtil.put(getString(R.string.label_black_card_weather) + widgetId + "_key", key);
        SPUtil.put(getString(R.string.label_black_card_weather) + widgetId + "_alignment", getAlignment(alignment, true));
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
