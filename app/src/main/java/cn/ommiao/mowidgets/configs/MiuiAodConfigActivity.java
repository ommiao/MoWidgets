package cn.ommiao.mowidgets.configs;

import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.databinding.LayoutMiuiAodSelectorBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.others.RadioTextView;
import cn.ommiao.mowidgets.widgets.pointerclock.MiuiAodWidget;

public class MiuiAodConfigActivity extends BaseConfigActivity<MiuiAodWidget> {

    private MiuiAodWidget.Style style;

    private LayoutColorSelectorBinding colorSelectorBindingBg1, colorSelectorBindingBg2, colorSelectorBindingHour, colorSelectorBindingMinute;
    private String colorBg1, colorBg2, colorHour, colorMinute;

    private LayoutEdittextBinding edittextBindingAngle;
    private int angle;

    @Override
    protected MiuiAodWidget getTargetWidget() {
        return new MiuiAodWidget();
    }

    @Override
    protected void initConfigViews() {
        RadioTextView.clearGroup("AOD-STYLE");
        LayoutMiuiAodSelectorBinding binding = DataBindingUtil.bind(LayoutInflater.from(this).inflate(R.layout.layout_miui_aod_selector, null));
        assert binding != null;
        addConfigView(binding.getRoot());
        colorSelectorBindingBg1 = getColorSelectorBinding("背景颜色一", "00000000");
        colorSelectorBindingBg2 = getColorSelectorBinding("背景颜色二", "00000000");
        colorSelectorBindingHour = getColorSelectorBinding("时针颜色", "00000000");
        colorSelectorBindingMinute = getColorSelectorBinding("分针颜色", "00000000");
        edittextBindingAngle = getNumberEdittextBinding("起始角度", 3);
        edittextBindingAngle.et.setText("0");
        addConfigView(colorSelectorBindingBg1.getRoot());
        addConfigView(colorSelectorBindingBg2.getRoot());
        addConfigView(edittextBindingAngle.getRoot());
        addConfigView(colorSelectorBindingHour.getRoot());
        addConfigView(colorSelectorBindingMinute.getRoot());

    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_miui_aod_clock_settings);
    }

    @Override
    protected boolean isDataValid() {
        String styleDesc = RadioTextView.getCheckedString("AOD-STYLE");
        style = MiuiAodWidget.Style.getStyleByDesc(styleDesc);
        colorBg1 = colorSelectorBindingBg1.etColor.getText().toString().trim();
        colorBg2 = colorSelectorBindingBg2.etColor.getText().toString().trim();
        colorHour = colorSelectorBindingHour.etColor.getText().toString().trim();
        colorMinute = colorSelectorBindingMinute.etColor.getText().toString().trim();
        if(!isColorValid(colorBg1)){
            ToastUtil.shortToast("请输入有效的颜色值(背景颜色一)");
            return false;
        }
        if(!isColorValid(colorBg2)){
            ToastUtil.shortToast("请输入有效的颜色值(背景颜色二)");
            return false;
        }
        if(!isColorValid(colorHour)){
            ToastUtil.shortToast("请输入有效的颜色值(时针颜色)");
            return false;
        }
        if(!isColorValid(colorMinute)){
            ToastUtil.shortToast("请输入有效的颜色值(分针颜色)");
            return false;
        }
        String angleStr = edittextBindingAngle.et.getText().toString().trim();
        if(!isNumberValid(angleStr)){
            angleStr = "0";
        }
        angle = Integer.parseInt(angleStr);
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_miui_aod) + widgetId + "_style", style.name());
        SPUtil.put(getString(R.string.label_miui_aod) + widgetId + "_color_bg_1", "#" + colorBg1);
        SPUtil.put(getString(R.string.label_miui_aod) + widgetId + "_color_bg_2", "#" + colorBg2);
        SPUtil.put(getString(R.string.label_miui_aod) + widgetId + "_color_hour", "#" + colorHour);
        SPUtil.put(getString(R.string.label_miui_aod) + widgetId + "_color_minute", "#" + colorMinute);
        SPUtil.put(getString(R.string.label_miui_aod) + widgetId + "_angle", angle);
    }

}
