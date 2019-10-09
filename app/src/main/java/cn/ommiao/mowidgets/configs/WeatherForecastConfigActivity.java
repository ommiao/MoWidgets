package cn.ommiao.mowidgets.configs;

import android.text.InputFilter;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.SPUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.list.WeatherForecastWidget;

public class WeatherForecastConfigActivity extends BaseConfigActivity<WeatherForecastWidget> {

    private LayoutEdittextBinding edittextBinding;
    private String area;

    private LayoutEdittextBinding keyBinding;
    private String key;

    private LayoutColorSelectorBinding selectorBindingCard1, selectorBindingText1, selectorBindingCard2, selectorBindingText2, selectorBindingCard3, selectorBindingText3;
    private String colorCard1, colorText1, colorCard2, colorText2, colorCard3, colorText3;

    @Override
    protected WeatherForecastWidget getTargetWidget() {
        return new WeatherForecastWidget();
    }

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
                                String c = String.valueOf(source.charAt(i));
                                if(!allDigits.contains(c)){
                                    result = result.replace(c, "");
                                }
                            }
                            return result;
                        }});
        addConfigView(keyBinding.getRoot());
        selectorBindingCard1 = getColorSelectorBinding("天气1卡片颜色", "FFD200");
        selectorBindingCard2 = getColorSelectorBinding("天气2卡片颜色", "E2E2E2");
        selectorBindingCard3 = getColorSelectorBinding("天气3卡片颜色", "1C1E21");
        selectorBindingText1 = getColorSelectorBinding("天气1文字颜色", "000000");
        selectorBindingText2 = getColorSelectorBinding("天气2文字颜色", "000000");
        selectorBindingText3 = getColorSelectorBinding("天气3文字颜色", "FFFFFF");
        addConfigView(selectorBindingCard1.getRoot());
        addConfigView(selectorBindingCard2.getRoot());
        addConfigView(selectorBindingCard3.getRoot());
        addConfigView(selectorBindingText1.getRoot());
        addConfigView(selectorBindingText2.getRoot());
        addConfigView(selectorBindingText3.getRoot());
    }

    @Override
    protected String getConfigTitle() {
        return getString(R.string.title_weather_forecast_settings);
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
        colorCard1 = selectorBindingCard1.etColor.getText().toString();
        colorCard2 = selectorBindingCard2.etColor.getText().toString();
        colorCard3 = selectorBindingCard3.etColor.getText().toString();
        colorText1 = selectorBindingText1.etColor.getText().toString();
        colorText2 = selectorBindingText2.etColor.getText().toString();
        colorText3 = selectorBindingText3.etColor.getText().toString();
        if(!isColorValid(colorCard1)){
            ToastUtil.shortToast("请输入正确的颜色值（天气1卡片颜色）");
            return false;
        }
        if(!isColorValid(colorCard2)){
            ToastUtil.shortToast("请输入正确的颜色值（天气2卡片颜色）");
            return false;
        }
        if(!isColorValid(colorCard3)){
            ToastUtil.shortToast("请输入正确的颜色值（天气3卡片颜色）");
            return false;
        }
        if(!isColorValid(colorText1)){
            ToastUtil.shortToast("请输入正确的颜色值（天气1文字颜色）");
            return false;
        }
        if(!isColorValid(colorText2)){
            ToastUtil.shortToast("请输入正确的颜色值（天气2文字颜色）");
            return false;
        }
        if(!isColorValid(colorText3)){
            ToastUtil.shortToast("请输入正确的颜色值（天气3文字颜色）");
            return false;
        }
        return true;
    }

    @Override
    protected void saveConfigs() {
        SPUtil.put(getString(R.string.label_weather_forecast) + widgetId + "_location", area);
        SPUtil.put(getString(R.string.label_weather_forecast) + widgetId + "_key", key);
        SPUtil.put(getString(R.string.label_weather_forecast) + widgetId + "_color_card1", "#" + colorCard1);
        SPUtil.put(getString(R.string.label_weather_forecast) + widgetId + "_color_card2", "#" + colorCard2);
        SPUtil.put(getString(R.string.label_weather_forecast) + widgetId + "_color_card3", "#" + colorCard3);
        SPUtil.put(getString(R.string.label_weather_forecast) + widgetId + "_color_text1", "#" + colorText1);
        SPUtil.put(getString(R.string.label_weather_forecast) + widgetId + "_color_text2", "#" + colorText2);
        SPUtil.put(getString(R.string.label_weather_forecast) + widgetId + "_color_text3", "#" + colorText3);
    }

}
