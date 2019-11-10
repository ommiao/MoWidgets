package cn.ommiao.mowidgets.configs;

import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutMiuiAodSelectorBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.widgets.others.RadioTextView;
import cn.ommiao.mowidgets.widgets.pointerclock.MiuiAodWidget;

public class MiuiAodConfigActivity extends BaseConfigActivity<MiuiAodWidget> {

    private MiuiAodWidget.Style style;

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
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_miui_aod_clock_settings);
    }

    @Override
    protected boolean isDataValid() {
        String styleDesc = RadioTextView.getCheckedString("AOD-STYLE");
        style = MiuiAodWidget.Style.getStyleByDesc(styleDesc);
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_miui_aod) + widgetId + "_style", style.name());
    }

}
