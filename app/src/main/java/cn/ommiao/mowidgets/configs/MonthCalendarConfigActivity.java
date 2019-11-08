package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutAlignmentBinding;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutFileSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutTwoSelectionBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.list.MonthCalendarWidget;
import cn.ommiao.mowidgets.widgets.others.RadioTextView;

public class MonthCalendarConfigActivity extends BaseConfigActivity<MonthCalendarWidget> {

    private MonthCalendarWidget.Theme theme = MonthCalendarWidget.Theme.WHITE;

    private LayoutColorSelectorBinding colorSelectorBindingMain;
    private String colorMain;

    private LayoutColorSelectorBinding colorSelectorBindingDateNow;
    private String colorDateNow;

    private String alignment;

    private LayoutFileSelectorBinding fileSelectorBinding;
    private String path;

    @Override
    protected MonthCalendarWidget getTargetWidget() {
        return new MonthCalendarWidget();
    }

    @Override
    protected void initConfigViews() {
        LayoutTwoSelectionBinding twoSelectionBindingTheme = getTwoSelectionBinding("主题", new String[]{"皓白", "暗夜"});
        addConfigView(twoSelectionBindingTheme.getRoot());
        colorSelectorBindingMain = getColorSelectorBinding("强调色", "ff0000");
        colorSelectorBindingDateNow = getColorSelectorBinding("当前日期文字颜色", "ffffff");
        addConfigView(colorSelectorBindingMain.getRoot());
        addConfigView(colorSelectorBindingDateNow.getRoot());
        LayoutAlignmentBinding alignmentBinding = getAlignmentBinding("垂直对齐", true);
        addConfigView(alignmentBinding.getRoot());
        fileSelectorBinding = getImgSelectorBinding("图片路径");
        addConfigView(fileSelectorBinding.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_month_calendar_settings);
    }

    @Override
    protected boolean isDataValid() {
        colorMain = colorSelectorBindingMain.etColor.getText().toString();
        colorDateNow = colorSelectorBindingDateNow.etColor.getText().toString();
        if(!isColorValid(colorMain)){
            ToastUtil.shortToast("请输入正确的颜色值（强调色）");
            return false;
        }
        if(!isColorValid(colorDateNow)){
            ToastUtil.shortToast("请输入正确的颜色值（当前日期文字颜色）");
            return false;
        }
        alignment = RadioTextView.getCheckedString("垂直对齐");
        theme = "暗夜".equals(RadioTextView.getCheckedString("主题")) ? MonthCalendarWidget.Theme.BLACK : MonthCalendarWidget.Theme.WHITE;
        path = fileSelectorBinding.tvFileName.getText().toString().trim();
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_month_calendar) + widgetId + "_theme", theme.name());
        SPUtil.put(getString(R.string.label_month_calendar) + widgetId + "_color_main", "#" + colorMain);
        SPUtil.put(getString(R.string.label_month_calendar) + widgetId + "_color_date_now", "#" + colorDateNow);
        SPUtil.put(getString(R.string.label_month_calendar) + widgetId + "_alignment", getAlignment(alignment, true));
        SPUtil.put(getString(R.string.label_month_calendar) + widgetId + "_path", path);
    }

    @Override
    protected boolean needReadStorage() {
        return true;
    }

    @Override
    protected boolean isSharedWidget() {
        return true;
    }

    @Override
    protected String getSharedUserName() {
        return "@题海散人";
    }
}
