package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.databinding.LayoutFileSelectorBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.picture.CustomPictureWidget;

public class CustomPictureActivity extends BaseConfigActivity<CustomPictureWidget> {

    private LayoutFileSelectorBinding fileSelectorBinding;
    private String path;

    private LayoutEdittextBinding edittextBindingAlpha;
    private int alpha;

    private LayoutEdittextBinding edittextBindingPaddingL, edittextBindingPaddingT, edittextBindingPaddingR, edittextBindingPaddingB;
    private int paddingL, paddingT, paddingR, paddingB;

    @Override
    protected CustomPictureWidget getTargetWidget() {
        return new CustomPictureWidget();
    }

    @Override
    protected void initConfigViews() {
        fileSelectorBinding = getImgSelectorBinding("图片路径");
        addConfigView(fileSelectorBinding.getRoot());
        edittextBindingAlpha = getNumberEdittextBinding("透明度", 3);
        edittextBindingAlpha.et.setText("255");
        addConfigView(edittextBindingAlpha.getRoot());
        edittextBindingPaddingL = getNumberEdittextBinding("左侧边距", 3);
        edittextBindingPaddingT = getNumberEdittextBinding("上方边距", 3);
        edittextBindingPaddingR = getNumberEdittextBinding("右侧边距", 3);
        edittextBindingPaddingB = getNumberEdittextBinding("下方边距", 3);
        addConfigView(edittextBindingPaddingL.getRoot());
        addConfigView(edittextBindingPaddingT.getRoot());
        addConfigView(edittextBindingPaddingR.getRoot());
        addConfigView(edittextBindingPaddingB.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_custom_settings);
    }

    @Override
    protected boolean isDataValid() {
        path = fileSelectorBinding.tvFileName.getText().toString().trim();
        if(path.length() == 0){
            ToastUtil.shortToast("请输入图片文件路径");
            return false;
        }
        String a = edittextBindingAlpha.et.getText().toString().trim();
        if(!isNumberValid(a)){
            ToastUtil.shortToast("请输入正确的透明度（0-255）");
            return false;
        }
        alpha = Integer.parseInt(a);
        String pl, pt, pr, pb;
        pl = edittextBindingPaddingL.et.getText().toString().trim();
        pt = edittextBindingPaddingT.et.getText().toString().trim();
        pr = edittextBindingPaddingR.et.getText().toString().trim();
        pb = edittextBindingPaddingB.et.getText().toString().trim();
        if(isNumberValid(pl)){
            paddingL = Integer.parseInt(pl);
        }
        if(isNumberValid(pt)){
            paddingT = Integer.parseInt(pt);
        }
        if(isNumberValid(pr)){
            paddingR = Integer.parseInt(pr);
        }
        if(isNumberValid(pb)){
            paddingB = Integer.parseInt(pb);
        }
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_custom_picture) + widgetId + "_path", path);
        SPUtil.put(getString(R.string.label_custom_picture) + widgetId + "_alpha", alpha);
        SPUtil.put(getString(R.string.label_custom_picture) + widgetId + "_padding_left", paddingL);
        SPUtil.put(getString(R.string.label_custom_picture) + widgetId + "_padding_top", paddingT);
        SPUtil.put(getString(R.string.label_custom_picture) + widgetId + "_padding_right", paddingR);
        SPUtil.put(getString(R.string.label_custom_picture) + widgetId + "_padding_bottom", paddingB);
    }

    @Override
    protected boolean needReadStorage() {
        return true;
    }
}
