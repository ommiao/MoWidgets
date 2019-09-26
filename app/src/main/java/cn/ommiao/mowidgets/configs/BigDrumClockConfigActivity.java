package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.BigDrumClockWidget;

public class BigDrumClockConfigActivity extends BaseConfigActivity<BigDrumClockWidget> {

    private LayoutColorSelectorBinding selectorBindingHour, selectorBindingMinute, selectorBindingLine, selectorBindingWeek;
    private String colorHour, colorMinute, colorLine, colorWeek;

    @Override
    protected BigDrumClockWidget getTargetWidget() {
        return new BigDrumClockWidget();
    }

    @Override
    protected void initConfigViews() {
        selectorBindingHour = getColorSelectorBinding("小时颜色", "000000");
        selectorBindingMinute = getColorSelectorBinding("分钟颜色", "9D1237");
        selectorBindingLine = getColorSelectorBinding("线条颜色", "000000");
        selectorBindingWeek = getColorSelectorBinding("星期颜色", "000000");
        addConfigView(selectorBindingHour.getRoot());
        addConfigView(selectorBindingMinute.getRoot());
        addConfigView(selectorBindingLine.getRoot());
        addConfigView(selectorBindingWeek.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_big_drum_clock_settings);
    }

    @Override
    protected boolean isDataValid() {
        colorHour = selectorBindingHour.etColor.getText().toString().trim();
        colorMinute = selectorBindingMinute.etColor.getText().toString().trim();
        colorLine = selectorBindingLine.etColor.getText().toString().trim();
        colorWeek = selectorBindingWeek.etColor.getText().toString().trim();
        if(!checkColorString(colorHour, selectorBindingHour.tvLabel.getText())){
            return false;
        }
        if(!checkColorString(colorMinute, selectorBindingMinute.tvLabel.getText())){
            return false;
        }
        if(!checkColorString(colorLine, selectorBindingLine.tvLabel.getText())){
            return false;
        }
        if(!checkColorString(colorWeek, selectorBindingWeek.tvLabel.getText())){
            return false;
        }
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_big_drum_clock) + widgetId + "_color_hour", "#" + colorHour);
        SPUtil.put(getString(R.string.label_big_drum_clock) + widgetId + "_color_minute", "#" + colorMinute);
        SPUtil.put(getString(R.string.label_big_drum_clock) + widgetId + "_color_line", "#" + colorLine);
        SPUtil.put(getString(R.string.label_big_drum_clock) + widgetId + "_color_week", "#" + colorWeek);
    }

    @Override
    protected boolean isSharedWidget() {
        return true;
    }

    protected String getSharedUserName(){
        return "@NOTEPAD";
    }

}
