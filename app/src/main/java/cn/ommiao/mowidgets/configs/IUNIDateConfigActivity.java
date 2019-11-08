package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutTwoSelectionBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.IUNIDateWidget;
import cn.ommiao.mowidgets.widgets.others.RadioTextView;

public class IUNIDateConfigActivity extends BaseConfigActivity<IUNIDateWidget> {

    private static final String LABEL_SHOW_CHINESE_DATE = "显示中文日期";

    private LayoutColorSelectorBinding colorSelectorBinding;
    private String color;

    private boolean showChinese = true;

    @Override
    protected IUNIDateWidget getTargetWidget() {
        return new IUNIDateWidget();
    }

    @Override
    protected void initConfigViews() {
        colorSelectorBinding = getColorSelectorBinding("颜色");
        addConfigView(colorSelectorBinding.getRoot());
        LayoutTwoSelectionBinding binding = getTwoSelectionBinding(LABEL_SHOW_CHINESE_DATE, new String[]{"是", "否"});
        addConfigView(binding.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_iuni_date_settings);
    }

    @Override
    protected boolean isDataValid() {
        color = colorSelectorBinding.etColor.getText().toString();
        if(!isColorValid(color)){
            ToastUtil.shortToast("请输入有效的文字颜色");
            return false;
        }
        showChinese = "是".equals(RadioTextView.getCheckedString(LABEL_SHOW_CHINESE_DATE));
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_iuni_date) + widgetId + "_color", "#" + color);
        SPUtil.put(getString(R.string.label_iuni_date) + widgetId + "_show_chinese", showChinese);
    }

}
