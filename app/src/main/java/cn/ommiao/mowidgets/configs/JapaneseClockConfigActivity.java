package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.JapaneseClockWidget;

public class JapaneseClockConfigActivity extends BaseConfigActivity<JapaneseClockWidget> {

    private LayoutColorSelectorBinding bindingColorIts, bindingColorTime;
    private String colorIts, colorTime;

    private LayoutEdittextBinding bindingFontName;
    private String fontName;

    @Override
    protected JapaneseClockWidget getTargetWidget() {
        return new JapaneseClockWidget();
    }

    @Override
    protected void initConfigViews() {
        addConfigView(getDescriptionBinding("说明：\n请将.ttf/.otf字体文件放置在Android/data/cn.ommiao.mowidgets/file/font路径下，然后填写字体文件名称（带扩展名），使用该目录是为了避免申请乱七八糟的权限，也可以选择不填字体名称使用默认字体。").getRoot());
        bindingColorIts = getColorSelectorBinding("今は颜色");
        bindingColorTime = getColorSelectorBinding("时间颜色");
        addConfigView(bindingColorIts.getRoot());
        addConfigView(bindingColorTime.getRoot());
        bindingFontName = getEdittextBinding("字体文件");
        bindingFontName.et.setHint("roboto.ttf");
        addConfigView(bindingFontName.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_japanese_clock_settings);
    }

    @Override
    protected boolean isDataValid() {
        colorIts = "#" + bindingColorIts.etColor.getText().toString().trim();
        colorTime = "#" + bindingColorTime.etColor.getText().toString().trim();
        fontName = bindingFontName.et.getText().toString().trim();
        if(fontName.length() > 0){
            if (!fontName.endsWith(".ttf") && !fontName.endsWith(".otf")){
                ToastUtil.shortToast("请输入正确的字体文件名称");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_japanese_clock) + widgetId + "_color_its", colorIts);
        SPUtil.put(getString(R.string.label_japanese_clock) + widgetId  + "_color_time", colorTime);
        SPUtil.put(getString(R.string.label_japanese_clock) + widgetId + "_font_name", fontName);
    }

}
