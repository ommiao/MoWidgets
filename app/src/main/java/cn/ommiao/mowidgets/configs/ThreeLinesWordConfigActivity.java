package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutAlignmentBinding;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.StringUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.ThreeLinesWordWidget;
import cn.ommiao.mowidgets.widgets.others.RadioTextView;

public class ThreeLinesWordConfigActivity extends BaseConfigActivity<ThreeLinesWordWidget> {

    private LayoutEdittextBinding bindingLine1, bindingLine2, bindingLine3;
    private String wordLine1, wordLine2, wordLine3;
    private LayoutColorSelectorBinding bindingColor1, bindingColor2, bindingColor3;
    private String colorLine1, colorLine2, colorLine3;
    private LayoutEdittextBinding bindingSize1, bindingSize2, bindingSize3;
    private int sizeLine1, sizeLine2, sizeLine3;
    private String alignment;

    private LayoutEdittextBinding bindingHorizontalPadding;
    private int horizontalPadding;

    @Override
    protected ThreeLinesWordWidget getTargetWidget() {
        return new ThreeLinesWordWidget();
    }

    @Override
    protected void initConfigViews() {
        bindingLine1 = getEdittextBinding("第一行文字");
        bindingLine2 = getEdittextBinding("第二行文字");
        bindingLine3 = getEdittextBinding("第三行文字");
        bindingLine1.et.setText("我");
        bindingLine2.et.setText("只有在做一件事的时候才会想你");
        bindingLine3.et.setText("那就是  呼吸");
        addConfigView(bindingLine1.getRoot());
        addConfigView(bindingLine2.getRoot());
        addConfigView(bindingLine3.getRoot());
        bindingColor1 = getColorSelectorBinding("第一行颜色");
        bindingColor2 = getColorSelectorBinding("第二行颜色");
        bindingColor3= getColorSelectorBinding("第三行颜色");
        bindingColor1.etColor.setText("ffffffff");
        bindingColor2.etColor.setText("ffffffff");
        bindingColor3.etColor.setText("ffffffff");
        addConfigView(bindingColor1.getRoot());
        addConfigView(bindingColor2.getRoot());
        addConfigView(bindingColor3.getRoot());
        bindingSize1 = getNumberEdittextBinding("第一行大小", TEXT_SIZE_MAX_LENGTH);
        bindingSize2 = getNumberEdittextBinding("第二行大小", TEXT_SIZE_MAX_LENGTH);
        bindingSize3 = getNumberEdittextBinding("第三行大小", TEXT_SIZE_MAX_LENGTH);
        bindingSize1.et.setText("32");
        bindingSize1.et.setHint("默认为32");
        bindingSize2.et.setText("24");
        bindingSize2.et.setHint("默认为24");
        bindingSize3.et.setText("28");
        bindingSize3.et.setHint("默认为28");
        addConfigView(bindingSize1.getRoot());
        addConfigView(bindingSize2.getRoot());
        addConfigView(bindingSize3.getRoot());
        LayoutAlignmentBinding alignmentBinding = getAlignmentBinding("文字对齐方式");
        addConfigView(alignmentBinding.getRoot());
        bindingHorizontalPadding = getNumberEdittextBinding("两侧边距", PADDING_MAX_LENGTH);
        addConfigView(bindingHorizontalPadding.getRoot());
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
        String size1, size2, size3;
        size1 = bindingSize1.et.getText().toString().trim();
        size2 = bindingSize2.et.getText().toString().trim();
        size3 = bindingSize3.et.getText().toString().trim();
        if(!isNumberValid(size1)){
            ToastUtil.shortToast("请输入正确的文字大小（第一行）");
            return false;
        }
        if(!isNumberValid(size2)){
            ToastUtil.shortToast("请输入正确的文字大小（第二行）");
            return false;
        }
        if(!isNumberValid(size3)){
            ToastUtil.shortToast("请输入正确的文字大小（第三行）");
            return false;
        }
        sizeLine1 = Integer.parseInt(size1);
        sizeLine2 = Integer.parseInt(size2);
        sizeLine3 = Integer.parseInt(size3);
        alignment = RadioTextView.getCheckedString("文字对齐方式");
        String hPaddingStr = bindingHorizontalPadding.et.getText().toString().trim();
        if(!isNumberValid(hPaddingStr)){
            hPaddingStr = "0";
        }
        horizontalPadding = Integer.parseInt(hPaddingStr);
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
        SPUtil.put(getString(R.string.label_three_lines_word) + widgetId + "_alignment", getAlignment(alignment));
        SPUtil.put(getString(R.string.label_three_lines_word) + widgetId + "_horizontal_padding", horizontalPadding);
    }

}
