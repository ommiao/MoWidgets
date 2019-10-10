package cn.ommiao.mowidgets.configs;

import android.text.InputFilter;

import com.orhanobut.logger.Logger;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.BaseWidget;

public abstract class BaseWeatherConfigActivity<W extends BaseWidget> extends BaseConfigActivity<W> {

    private LayoutEdittextBinding edittextBinding;
    private String area;

    private LayoutEdittextBinding keyBinding;
    private String key;

    @Override
    protected void initConfigViews() {
        edittextBinding = getEdittextBinding(getString(R.string.txt_area));
        edittextBinding.et.setHint(R.string.hint_et_location);
        addConfigView(edittextBinding.getRoot());
        keyBinding = getEdittextBinding("和风Key");
        keyBinding.et.setHint("请到和风天气官网申请免费Key(32位)");
        keyBinding.et.setFilters(
                new InputFilter[]{
                        new InputFilter.LengthFilter(32),
                        (source, start, end, dest, dstart, dend) -> {
                            String allDigits = "0123456789abcdefermiao";
                            String result = source.toString();
                            for(int i = end - 1; i >= start; i--){
                                Logger.d("ommiao: " + i);
                                String c = String.valueOf(source.charAt(i));
                                if(!allDigits.contains(c)){
                                    result = result.replace(c, "");
                                }
                            }
                            return result;
                        }});
        addConfigView(keyBinding.getRoot());
    }

    @Override
    protected boolean isDataValid() {
        area = edittextBinding.et.getText().toString().trim();
        if(area.length() == 0){
            ToastUtil.shortToast(R.string.please_input_valid_area);
            return false;
        }
        key = keyBinding.et.getText().toString().trim();
        if(!getString(R.string.key_secret).equals(key) && key.length() != 32){
            ToastUtil.shortToast("请输入有效的和风天气Key");
            return false;
        }
        return true;
    }

    @Override
    protected void saveConfigs() {
        saveConfigs(area, key);
    }

    protected abstract void saveConfigs(String area, String key);
}
