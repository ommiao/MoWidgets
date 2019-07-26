package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.StringUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.ThreeLinesWordWidget;

public class ThreeLinesWordConfigActivity extends BaseConfigActivity<ThreeLinesWordWidget> {

    private LayoutEdittextBinding bindingLine1, bindingLine2, bindingLine3;
    private String wordLine1, wordLine2, wordLine3;
    private LayoutColorSelectorBinding bindingColor1, bindingColor2, bindingColor3;
    private String colorLine1, colorLine2, colorLine3;
    private LayoutEdittextBinding bindingSize1, bindingSize2, bindingSize3;
    private int sizeLine1, sizeLine2, sizeLine3;

    @Override
    protected ThreeLinesWordWidget getTargetWidget() {
        return new ThreeLinesWordWidget();
    }

    @Override
    protected void initConfigViews() {
        bindingLine1 = getEdittextBinding("第一行文字");
        bindingLine2 = getEdittextBinding("第二行文字");
        bindingLine3 = getEdittextBinding("第三行文字");
        addConfigView(bindingLine1.getRoot());
        addConfigView(bindingLine2.getRoot());
        addConfigView(bindingLine3.getRoot());
        bindingColor1 = getColorSelectorBinding("第一行颜色");
        bindingColor2 = getColorSelectorBinding("第二行颜色");
        bindingColor3= getColorSelectorBinding("第三行颜色");
        addConfigView(bindingColor1.getRoot());
        addConfigView(bindingColor2.getRoot());
        addConfigView(bindingColor3.getRoot());
        bindingSize1 = getNumberEdittextBinding("第一行大小", 2);
        bindingSize2 = getNumberEdittextBinding("第二行大小", 2);
        bindingSize3 = getNumberEdittextBinding("第三行大小", 2);
        addConfigView(bindingSize1.getRoot());
        addConfigView(bindingSize2.getRoot());
        addConfigView(bindingSize3.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_three_lines_word);
    }

    @Override
    protected boolean isDataValid() {
        wordLine1 = bindingLine1.et.getText().toString().trim();
        wordLine2 = bindingLine2.et.getText().toString().trim();
        wordLine3 = bindingLine3.et.getText().toString().trim();
        if(StringUtil.isEmpty(wordLine1) && StringUtil.isEmpty(wordLine2) && StringUtil.isEmpty(wordLine3)){
            ToastUtil.shortToast(R.string.please_input_at_least_one_line);
            return false;
        }
        colorLine1 = bindingColor1.etColor.getText().toString();
        colorLine2 = bindingColor2.etColor.getText().toString();
        colorLine3 = bindingColor3.etColor.getText().toString();
        if(!isColorValid(colorLine1)){
            ToastUtil.shortToast("请输入正确的颜色值（第一行）");
            return false;
        }
        if(!isColorValid(colorLine2)){
            ToastUtil.shortToast("请输入正确的颜色值（第二行）");
            return false;
        }
        if(!isColorValid(colorLine3)){
            ToastUtil.shortToast("请输入正确的颜色值（第三行）");
            return false;
        }
        sizeLine1 = Integer.parseInt(bindingSize1.et.getText().toString());
        sizeLine2 = Integer.parseInt(bindingSize2.et.getText().toString());
        sizeLine3 = Integer.parseInt(bindingSize3.et.getText().toString());
        if(sizeLine1 == 0){
            ToastUtil.shortToast("请输入正确的文字大小（第一行）");
            return false;
        }
        if(sizeLine2 == 0){
            ToastUtil.shortToast("请输入正确的文字大小（第二行）");
            return false;
        }
        if(sizeLine3 == 0){
            ToastUtil.shortToast("请输入正确的文字大小（第三行）");
            return false;
        }
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_three_lines_word) + widgetId + "_word_line1", wordLine1);
        SPUtil.put(getString(R.string.label_three_lines_word) + widgetId + "_word_line2", wordLine2);
        SPUtil.put(getString(R.string.label_three_lines_word) + widgetId + "_word_line3", wordLine3);
        SPUtil.put(getString(R.string.label_three_lines_word) + widgetId + "_color_line1", "#" + colorLine1);
        SPUtil.put(getString(R.string.label_three_lines_word) + widgetId + "_color_line2", "#" + colorLine2);
        SPUtil.put(getString(R.string.label_three_lines_word) + widgetId + "_color_line3", "#" + colorLine3);
        SPUtil.put(getString(R.string.label_three_lines_word) + widgetId + "_size_line1", sizeLine1);
        SPUtil.put(getString(R.string.label_three_lines_word) + widgetId + "_size_line2", sizeLine2);
        SPUtil.put(getString(R.string.label_three_lines_word) + widgetId + "_size_line3", sizeLine3);
    }

}
