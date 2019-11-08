package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.databinding.LayoutTwoSelectionBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.GoogleNowWidget;
import cn.ommiao.mowidgets.widgets.others.RadioTextView;

public class GoogleNowConfigActivity extends BaseConfigActivity<GoogleNowWidget> {

    private GoogleNowWidget.Theme theme = GoogleNowWidget.Theme.WHITE;

    private LayoutEdittextBinding sloganBinding;
    private String slogan;

    @Override
    protected GoogleNowWidget getTargetWidget() {
        return new GoogleNowWidget();
    }

    @Override
    protected void initConfigViews() {
        LayoutTwoSelectionBinding twoSelectionBindingTheme = getTwoSelectionBinding("主题", new String[]{"默认", "暗黑"});
        addConfigView(twoSelectionBindingTheme.getRoot());
        sloganBinding = getEdittextBinding("显示文字");
        addConfigView(sloganBinding.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return "Google Now Settings";
    }

    @Override
    protected boolean isDataValid() {
        theme = "暗黑".equals(RadioTextView.getCheckedString("主题")) ? GoogleNowWidget.Theme.BLACK : GoogleNowWidget.Theme.WHITE;
        slogan = sloganBinding.et.getText().toString();
        if(slogan.length() > 0){
            return true;
        } else {
            ToastUtil.shortToast(R.string.please_input_display_word);
            return false;
        }
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_google_now) + widgetId + "_theme", theme.name());
        SPUtil.put(getString(R.string.label_google_now) + widgetId + "_word", slogan);
    }
}
