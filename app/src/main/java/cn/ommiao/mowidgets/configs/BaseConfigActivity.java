package cn.ommiao.mowidgets.configs;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RemoteViews;

import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.TimeService;
import cn.ommiao.mowidgets.databinding.ActivityConfigBinding;
import cn.ommiao.mowidgets.databinding.LayoutAlignmentBinding;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.BaseWidget;
import cn.ommiao.mowidgets.widgets.TimingRefreshWidget;
import cn.ommiao.mowidgets.widgets.others.RadioTextView;

public abstract class BaseConfigActivity<W extends BaseWidget> extends Activity {

    private ArrayList<View> configList = new ArrayList<>();
    private ActivityConfigBinding mBinding;
    protected int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    protected AppWidgetManager appWidgetManager;
    private W widget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if(widgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
            return;
        }
        initWindow();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_config);
        appWidgetManager = AppWidgetManager.getInstance(this);
        widget = getTargetWidget();
        initConfigViews();
        initViews();
    }

    protected abstract W getTargetWidget();

    private void initWindow(){
        Window window = getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = screenWidth - getResources().getDimensionPixelSize(R.dimen.dialog_margin) * 2;
        params.height = screenHeight;
        window.setAttributes(params);
    }

    private void initViews(){
        mBinding.tvConfigTitle.setText(getConfigTitle());
        for (View view : configList){
            mBinding.llConfig.addView(view);
        }
        mBinding.tvConfirm.setOnClickListener(this::onConfirmClick);
        mBinding.tvCancel.setOnClickListener(this::onCancelClick);
    }

    private void onCancelClick(View view) {
        finish();
    }

    private void onConfirmClick(View view) {
        if(!isDataValid()){
            return;
        }
        saveConfigs();
        Intent reslut = new Intent();
        reslut.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, reslut);
        if(widget.needRequestData()){
            widget.getDataRequester(this, appWidgetManager, widgetId).request();
        } else {
            appWidgetManager.updateAppWidget(widgetId, getRemoteViews());
        }
        if(getTargetWidget() instanceof TimingRefreshWidget && !isTimeAccessibilityServiceOn()){
            ToastUtil.shortToast("请打开无障碍服务以稳定更新时间！");
            try {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        finish();
    }

    protected RemoteViews getRemoteViews(){
        return getTargetWidget().getRemoteViews(this, appWidgetManager, widgetId);
    }

    protected abstract void initConfigViews();

    protected abstract String getConfigTitle();

    protected void addConfigView(View view){
        boolean needDivider = configList.size() > 0;
        if(needDivider){
            addConfigItemDivider();
        }
        configList.add(view);
    }

    private void addConfigItemDivider() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_config_list_divider, null);
        configList.add(view);
    }

    protected abstract boolean isDataValid();

    protected abstract void saveConfigs();

    protected LayoutColorSelectorBinding getColorSelectorBinding(String label){
        LayoutColorSelectorBinding binding = DataBindingUtil.bind(LayoutInflater.from(this).inflate(R.layout.layout_color_selector, null));
        assert binding != null;
        binding.tvLabel.setText(label);
        binding.etColor.setHint(R.string.hint_et_color);
        binding.etColor.setText(R.string.default_et_color);
        binding.ivTest.setOnClickListener(view -> {
            String colorStr = binding.etColor.getText().toString().trim();
            if(isColorValid(colorStr)){
                int color = Color.parseColor("#" + colorStr);
                binding.ivColor.setColorFilter(color);
            } else {
                ToastUtil.shortToast(R.string.please_input_valid_color);
            }
        });
        return binding;
    }

    protected boolean isColorValid(String colorStr){
        return colorStr.length() == 6 || colorStr.length() == 8;
    }

    protected LayoutEdittextBinding getEdittextBinding(String label){
        return getEdittextBinding(label, 30);
    }

    protected LayoutEdittextBinding getEdittextBinding(String label, int maxLength){
        LayoutEdittextBinding binding = DataBindingUtil.bind(LayoutInflater.from(this).inflate(R.layout.layout_edittext, null));
        assert binding != null;
        binding.tvLabel.setText(label);
        binding.et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        return binding;
    }

    protected LayoutEdittextBinding getNumberEdittextBinding(String label){
        LayoutEdittextBinding binding = getEdittextBinding(label);
        binding.et.setInputType(InputType.TYPE_CLASS_PHONE);
        return binding;
    }

    protected LayoutEdittextBinding getNumberEdittextBinding(String label, int maxLength){
        LayoutEdittextBinding binding = getEdittextBinding(label, maxLength);
        binding.et.setInputType(InputType.TYPE_CLASS_PHONE);
        return binding;
    }

    protected LayoutAlignmentBinding getAlignmentBinding(String label){
        RadioTextView.clearGroup(label);
        LayoutAlignmentBinding binding = DataBindingUtil.bind(LayoutInflater.from(this).inflate(R.layout.layout_alignment, null));
        assert binding != null;
        binding.tvLabel.setText(label);
        binding.tvAlignLeft.setGroupId(label);
        binding.tvAlignMiddle.setGroupId(label);
        binding.tvAlignRight.setGroupId(label);
        return binding;
    }

    protected int getAlignment(String alignmentStr){
        switch (alignmentStr){
            case "居左":
                return Gravity.START;
            case "居中":
                return Gravity.CENTER_HORIZONTAL;
            case "居右":
                return Gravity.END;
            default:
                return Gravity.START;
        }
    }

    private boolean isTimeAccessibilityServiceOn() {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + TimeService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(
                    getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
