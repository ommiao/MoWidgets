package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutAlignmentBinding;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.list.MonthCalendarWidget;
import cn.ommiao.mowidgets.widgets.others.RadioTextView;

public class MonthCalendarConfigActivity extends BaseConfigActivity<MonthCalendarWidget> {

    private LayoutColorSelectorBinding colorSelectorBindingMain;
    private String colorMain;

    private LayoutColorSelectorBinding colorSelectorBindingDateNow;
    private String colorDateNow;

    private String alignment;

    private LayoutEdittextBinding edittextBindingPath;
    private String path;

    @Override
    protected MonthCalendarWidget getTargetWidget() {
        return new MonthCalendarWidget();
    }

    @Override
    protected void initConfigViews() {
        colorSelectorBindingMain = getColorSelectorBinding("强调色", "ff0000");
        colorSelectorBindingDateNow = getColorSelectorBinding("当前日期文字颜色", "ffffff");
        addConfigView(colorSelectorBindingMain.getRoot());
        addConfigView(colorSelectorBindingDateNow.getRoot());
        LayoutAlignmentBinding alignmentBinding = getAlignmentBinding("垂直对齐", true);
        addConfigView(alignmentBinding.getRoot());
        edittextBindingPath = getEdittextBinding("图片路径");
        edittextBindingPath.et.setHint("填写相对路径，png/jpg格式，不填为默认图片");
        addConfigView(edittextBindingPath.getRoot());
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
        path = edittextBindingPath.et.getText().toString().trim();
        if(path.length() > 0){
            if (!path.endsWith(".jpg") && !path.endsWith(".JPG") &&
                    !path.endsWith(".png") && !path.endsWith(".PNG") &&
                    !path.endsWith(".jpeg") && !path.endsWith(".JPEG")){
                ToastUtil.shortToast("请输入正确的图片文件路径");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_month_calendar) + widgetId + "_color_main", "#" + colorMain);
        SPUtil.put(getString(R.string.label_month_calendar) + widgetId + "_color_date_now", "#" + colorDateNow);
        SPUtil.put(getString(R.string.label_month_calendar) + widgetId + "_alignment", getAlignment(alignment, true));
        SPUtil.put(getString(R.string.label_month_calendar) + widgetId + "_path", path);
    }

    @Override
    protected boolean needReadStorage() {
        return true;
    }
}
