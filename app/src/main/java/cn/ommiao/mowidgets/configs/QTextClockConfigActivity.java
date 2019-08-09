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

    private LayoutEdittextBinding bindingTopPadding, bindingLeftPadding, bindingLinePadding;
    private int topPadding, leftPadding, linePadding;

    @Override
    protected QTextClockWidget getTargetWidget() {
        return new QTextClockWidget();
    }

    @Override
    protected void initConfigViews() {
        colorSelectorBindingIts = getColorSelectorBinding("It's颜色");
        colorSelectorBindingTime = getColorSelectorBinding("时间颜色");
        edittextBinding = getNumberEdittextBinding("文字大小", TEXT_SIZE_MAX_LENGTH);
        edittextBinding.et.setText("24");
        edittextBinding.et.setHint("默认为24");
        addConfigView(colorSelectorBindingIts.getRoot());
        addConfigView(colorSelectorBindingTime.getRoot());
        addConfigView(edittextBinding.getRoot());
        bindingTopPadding = getNumberEdittextBinding("上方间距", PADDING_MAX_LENGTH);
        bindingLeftPadding = getNumberEdittextBinding("左侧间距", PADDING_MAX_LENGTH);
        bindingLinePadding = getNumberEdittextBinding("文字间距", PADDING_MAX_LENGTH);
        addConfigView(bindingTopPadding.getRoot());
        addConfigView(bindingLeftPadding.getRoot());
        addConfigView(bindingLinePadding.getRoot());
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
        if(!isNumberValid(size)){
            ToastUtil.shortToast("请输入正确的文字大小");
            return false;
        }
        if(!isNumberValid(size)){
            size = "24";
        }
        textSize = Integer.parseInt(size);
        String topPaddingStr = bindingTopPadding.et.getText().toString().trim();
        String leftPaddingStr = bindingLeftPadding.et.getText().toString().trim();
        String linePaddingStr = bindingLinePadding.et.getText().toString().trim();
        if(!isNumberValid(topPaddingStr)){
            topPaddingStr = "0";
        }
        if(!isNumberValid(leftPaddingStr)){
            leftPaddingStr = "0";
        }
        if(!isNumberValid(linePaddingStr)){
            linePaddingStr = "0";
        }
        topPadding = Integer.parseInt(topPaddingStr);
        leftPadding = Integer.parseInt(leftPaddingStr);
        linePadding = Integer.parseInt(linePaddingStr);
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_q_text_clock) + widgetId + "_color_it_s", "#" + colorIts);
        SPUtil.put(getString(R.string.label_q_text_clock) + widgetId + "_color_time", "#" + colorTime);
        SPUtil.put(getString(R.string.label_q_text_clock) + widgetId + "_size_text", textSize);
        SPUtil.put(getString(R.string.label_q_text_clock) + widgetId + "_top_padding", topPadding);
        SPUtil.put(getString(R.string.label_q_text_clock) + widgetId + "_left_padding", leftPadding);
        SPUtil.put(getString(R.string.label_q_text_clock) + widgetId + "_line_padding", linePadding);
    }

}
