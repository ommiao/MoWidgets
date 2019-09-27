package cn.ommiao.mowidgets.configs;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.picture.CustomPictureWidget;

public class CustomPictureActivity extends BaseConfigActivity<CustomPictureWidget> {

    private LayoutEdittextBinding edittextBindingPath;
    private String path;

    private LayoutEdittextBinding edittextBindingAlpha;
    private int alpha;

    @Override
    protected CustomPictureWidget getTargetWidget() {
        return new CustomPictureWidget();
    }

    @Override
    protected void initConfigViews() {
        edittextBindingPath = getEdittextBinding("图片路径");
        edittextBindingPath.et.setHint("填写根目录的相对路径如font/roboto.ttf");
        addConfigView(edittextBindingPath.getRoot());
        edittextBindingAlpha = getNumberEdittextBinding("透明度", 3);
        edittextBindingAlpha.et.setText("255");
        addConfigView(edittextBindingAlpha.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_custom_settings);
    }

    @Override
    protected boolean isDataValid() {
        path = edittextBindingPath.et.getText().toString().trim();
        if(path.length() > 0){
            if (!path.endsWith(".jpg") && !path.endsWith(".JPG") &&
                    !path.endsWith(".png") && !path.endsWith(".PNG") &&
                    !path.endsWith(".jpeg") && !path.endsWith(".JPEG")){
                ToastUtil.shortToast("请输入正确的图片文件路径");
                return false;
            }
        } else {
            ToastUtil.shortToast("请输入图片文件路径");
        }
        String a = edittextBindingAlpha.et.getText().toString().trim();
        if(!isNumberValid(a)){
            ToastUtil.shortToast("请输入正确的透明度（0-255）");
            return false;
        }
        alpha = Integer.parseInt(a);
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_custom_picture) + widgetId + "_path", path);
        SPUtil.put(getString(R.string.label_custom_picture) + widgetId + "_alpha", alpha);
    }

    @Override
    protected boolean needReadStorage() {
        return true;
    }
}
