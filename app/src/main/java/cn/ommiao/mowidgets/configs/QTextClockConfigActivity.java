package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.QTextClockWidget;

public class QTextClockConfigActivity extends BaseConfigActivity<QTextClockWidget> {

    private LayoutColorSelectorBinding colorSelectorBindingIts, colorSelectorBindingTime;
    private LayoutEdittextBinding edittextBinding;

    private String colorIts, colorTime;
    private int textSize;

    @Override
    protected QTextClockWidget getTargetWidget() {
        return new QTextClockWidget();
    }

    @Override
    protected void initConfigViews() {
        colorSelectorBindingIts = getColorSelectorBinding("It's颜色");
        colorSelectorBindingTime = getColorSelectorBinding("时间颜色");
        edittextBinding = getNumberEdittextBinding("文字大小", 2);
        edittextBinding.et.setText("24");
        addConfigView(colorSelectorBindingIts.getRoot());
        addConfigView(colorSelectorBindingTime.getRoot());
        addConfigView(edittextBinding.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_q_text_clock_settings);
    }

    @Override
    protected boolean isDataValid() {
        colorIts = colorSelectorBindingIts.etColor.getText().toString();
        colorTime = colorSelectorBindingTime.etColor.getText().toString();
        String size = edittextBinding.et.getText().toString().trim();
        if(!isColorValid(colorIts)){
            ToastUtil.shortToast("请输入正确的颜色值（It's）");
            return false;
        }
        if(!isColorValid(colorTime)){
            ToastUtil.shortToast("请输入正确的颜色值（时间）");
            return false;
        }
        if(size.length() == 0){
            ToastUtil.shortToast("请输入正确的文字大小");
            return false;
        }
        textSize = Integer.parseInt(size);
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_q_text_clock) + widgetId + "_color_it_s", "#" + colorIts);
        SPUtil.put(getString(R.string.label_q_text_clock) + widgetId + "_color_time", "#" + colorTime);
        SPUtil.put(getString(R.string.label_q_text_clock) + widgetId + "_size_text", textSize);
    }

}
