package cn.ommiao.mowidgets.configs;

import android.text.InputFilter;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.databinding.LayoutFileSelectorBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.StringUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.RollingGalleryWidget;

public class RollingGalleryConfigActivity extends BaseConfigActivity<RollingGalleryWidget> {

    private LayoutFileSelectorBinding fileSelectorBinding1, fileSelectorBinding2, fileSelectorBinding3;
    private LayoutFileSelectorBinding fileSelectorBindingBg1, fileSelectorBindingBg2, fileSelectorBindingBg3;
    private String path1, path2, path3;
    private String pathBg1, pathBg2, pathBg3;

    private LayoutEdittextBinding edittextBindingRadiusScale;
    private float radiusScale;

    @Override
    protected RollingGalleryWidget getTargetWidget() {
        return new RollingGalleryWidget();
    }

    @Override
    protected void initConfigViews() {
        fileSelectorBinding1 = getImgSelectorBinding("图片一前景");
        fileSelectorBinding2 = getImgSelectorBinding("图片二前景");
        fileSelectorBinding3 = getImgSelectorBinding("图片三前景");
        fileSelectorBindingBg1 = getImgSelectorBinding("图片一背景");
        fileSelectorBindingBg2 = getImgSelectorBinding("图片二背景");
        fileSelectorBindingBg3 = getImgSelectorBinding("图片三背景");
        addConfigView(fileSelectorBinding1.getRoot());
        addConfigView(fileSelectorBindingBg1.getRoot());
        addConfigView(fileSelectorBinding2.getRoot());
        addConfigView(fileSelectorBindingBg2.getRoot());
        addConfigView(fileSelectorBinding3.getRoot());
        addConfigView(fileSelectorBindingBg3.getRoot());
        edittextBindingRadiusScale = getEdittextBinding("圆角比例");
        edittextBindingRadiusScale.et.setText("0.03");
        edittextBindingRadiusScale.et.setHint("0-0.5的小数");
        edittextBindingRadiusScale.et.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(4),
                (source, start, end, dest, dstart, dend) -> {
                    String allDigits = "1234567890.";
                    String result = source.toString();
                    for(int i = end - 1; i >= start; i--){
                        String c = String.valueOf(source.charAt(i));
                        if(!allDigits.contains(c)){
                            result = result.replace(c, "");
                        }
                    }
                    return result;
                }});
        addConfigView(edittextBindingRadiusScale.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_rolling_gallery_settings);
    }

    @Override
    protected boolean isDataValid() {
        path1 = fileSelectorBinding1.tvFileName.getText().toString().trim();
        path2 = fileSelectorBinding2.tvFileName.getText().toString().trim();
        path3 = fileSelectorBinding3.tvFileName.getText().toString().trim();
        pathBg1 = fileSelectorBindingBg1.tvFileName.getText().toString().trim();
        pathBg2 = fileSelectorBindingBg2.tvFileName.getText().toString().trim();
        pathBg3 = fileSelectorBindingBg3.tvFileName.getText().toString().trim();
        if(StringUtil.isEmpty(path1)){
            ToastUtil.shortToast("请选择图片一前景");
            return false;
        }
        if(StringUtil.isEmpty(path2)){
            ToastUtil.shortToast("请选择图片二前景");
            return false;
        }
        if(StringUtil.isEmpty(path3)){
            ToastUtil.shortToast("请选择图片三前景");
            return false;
        }
        if(StringUtil.isEmpty(pathBg1)){
            ToastUtil.shortToast("请选择图片一背景");
            return false;
        }
        if(StringUtil.isEmpty(pathBg2)){
            ToastUtil.shortToast("请选择图片二背景");
            return false;
        }
        if(StringUtil.isEmpty(pathBg3)){
            ToastUtil.shortToast("请选择图片三背景");
            return false;
        }
        try {
            radiusScale = Float.parseFloat(edittextBindingRadiusScale.et.getText().toString().trim());
        } catch (NumberFormatException e){
            e.printStackTrace();
            ToastUtil.shortToast("请输入圆角比例");
            return false;
        }
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_rolling_gallery) + widgetId + "_path_1", path1);
        SPUtil.put(getString(R.string.label_rolling_gallery) + widgetId + "_path_2", path2);
        SPUtil.put(getString(R.string.label_rolling_gallery) + widgetId + "_path_3", path3);
        SPUtil.put(getString(R.string.label_rolling_gallery) + widgetId + "_path_bg_1", pathBg1);
        SPUtil.put(getString(R.string.label_rolling_gallery) + widgetId + "_path_bg_2", pathBg2);
        SPUtil.put(getString(R.string.label_rolling_gallery) + widgetId + "_path_bg_3", pathBg3);
        SPUtil.put(getString(R.string.label_rolling_gallery) + widgetId + "_radius_scale", radiusScale);
    }

}
