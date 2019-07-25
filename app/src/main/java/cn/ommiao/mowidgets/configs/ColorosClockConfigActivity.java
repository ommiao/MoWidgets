package cn.ommiao.mowidgets.configs;

import android.widget.RemoteViews;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.ColorosClockWidget;

public class ColorosClockConfigActivity extends BaseConfigActivity {

    private LayoutEdittextBinding edittextBinding;
    private String area;

    @Override
    protected RemoteViews getRemoteViews() {
        ColorosClockWidget widget = new ColorosClockWidget();
        widget.setNeedRequestData(true);
        return widget.getRemoteViews(this, appWidgetManager, widgetId);
    }

    @Override
    protected void initConfigViews() {
        edittextBinding = getEdittextBinding(getString(R.string.txt_area));
        addConfigView(edittextBinding.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_coloros_clock_settings);
    }

    @Override
    protected boolean isDataValid() {
        area = edittextBinding.et.getText().toString().trim();
        if(area.length() > 0){
            return true;
        } else {
            ToastUtil.shortToast(R.string.please_input_valid_area);
            return false;
        }
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_coloros_clock) + widgetId + "_location", area);
    }

}
