package cn.ommiao.mowidgets.configs;

import android.Manifest;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.TimeService;
import cn.ommiao.mowidgets.databinding.ActivityConfigBinding;
import cn.ommiao.mowidgets.databinding.LayoutAlignmentBinding;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutDescriptionBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;
import cn.ommiao.mowidgets.databinding.LayoutTwoSelectionBinding;
import cn.ommiao.mowidgets.ui.ColorPickerFragment;
import cn.ommiao.mowidgets.ui.CustomDialogFragment;
import cn.ommiao.mowidgets.utils.StringUtil;
import cn.ommiao.mowidgets.utils.ToastUtil;
import cn.ommiao.mowidgets.widgets.BaseWidget;
import cn.ommiao.mowidgets.widgets.TimingRefreshWidget;
import cn.ommiao.mowidgets.widgets.others.RadioTextView;


public abstract class BaseConfigActivity<W extends BaseWidget> extends AppCompatActivity {

    public static final int NO_LIMIT = -1;

    public static final int PADDING_MAX_LENGTH = 2;
    public static final int TEXT_SIZE_MAX_LENGTH = 2;

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
        if(needReadStorage() && !hasReadStoragePermission()){
            showRequestReadStoragePermission();
        }
    }

    private boolean hasReadStoragePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    private void showRequestReadStoragePermission(){
        new CustomDialogFragment()
                .title("提示")
                .content("为了读取媒体文件需要申请读取存储空间权限，不授予该权限则无法使用该控件。")
                .leftBtn("确定")
                .rightBtn("取消")
                .onLeftClick(this::requestReadStoragePermission)
                .onRightClick(this::finish)
                .show(getSupportFragmentManager());
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestReadStoragePermission(){
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
            ToastUtil.shortToast("如读取文件失败，请手动打开读写存储权限！");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Logger.d("Permission Granted.");
        } else{
            ToastUtil.shortToast("读写存储权限被拒绝！");
            finish();
        }
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
        if(isSharedWidget()){
            String strStart = "此控件由", strEnd = "共享定制";
            String footerStr = strStart + getSharedUserName() + strEnd;
            SpannableString spannableString = new SpannableString(footerStr);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(getSharedUserNameColorStr()));
            spannableString.setSpan(colorSpan, strStart.length(), strStart.length() + getSharedUserName().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mBinding.tvFooter.setText(spannableString);
        }
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
        if(getTargetWidget() instanceof TimingRefreshWidget && (((TimingRefreshWidget) getTargetWidget()).needAccessibilityService()) && !isTimeAccessibilityServiceOn()){
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
        return getColorSelectorBinding(label, getString(R.string.default_et_color));
    }

    protected LayoutColorSelectorBinding getColorSelectorBinding(String label, String defaultColorStr){
        LayoutColorSelectorBinding binding = DataBindingUtil.bind(LayoutInflater.from(this).inflate(R.layout.layout_color_selector, null));
        assert binding != null;
        binding.tvLabel.setText(label);
        binding.etColor.setHint(R.string.hint_et_color);
        binding.etColor.setText(defaultColorStr);
        if(isColorValid(defaultColorStr)){
            binding.ivColor.setColorFilter(Color.parseColor("#" + defaultColorStr));
        }
        binding.ivColor.setOnClickListener(view -> {
            String colorStr = binding.etColor.getText().toString().trim();
            if(isColorValid(colorStr)){
                binding.ivColor.setColorFilter(Color.parseColor("#" + colorStr));
            } else {
                ToastUtil.shortToast("请输入有效的颜色值");
            }
        });
        binding.ivTest.setOnClickListener(view -> {
            ColorPickerFragment colorPickerFragment = new ColorPickerFragment();
            colorPickerFragment.setOnColorPickerClickedListener(new ColorPickerFragment.OnColorPickerClickedListener() {
                @Override
                public void onColorSelected(String hexColor) {
                    binding.etColor.setText(hexColor);
                    binding.ivColor.setColorFilter(Color.parseColor("#" + hexColor));
                }

                @Override
                public void onCancel() {

                }
            });
            colorPickerFragment.show(getSupportFragmentManager(), ColorPickerFragment.class.getSimpleName());
        });
        return binding;
    }

    protected boolean isColorValid(String colorStr){
        return colorStr.length() == 6 || colorStr.length() == 8;
    }

    protected LayoutEdittextBinding getEdittextBinding(String label){
        return getEdittextBinding(label, NO_LIMIT);
    }

    protected LayoutEdittextBinding getEdittextBinding(String label, int maxLength){
        LayoutEdittextBinding binding = DataBindingUtil.bind(LayoutInflater.from(this).inflate(R.layout.layout_edittext, null));
        assert binding != null;
        binding.tvLabel.setText(label);
        if(maxLength != NO_LIMIT){
            binding.et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
        return binding;
    }

    protected LayoutEdittextBinding getNumberEdittextBinding(String label){
        return getNumberEdittextBinding(label, 100, false);
    }

    protected LayoutEdittextBinding getNumberEdittextBinding(String label, int maxLength){
        return getNumberEdittextBinding(label, maxLength, false);
    }

    protected LayoutEdittextBinding getNumberEdittextBinding(String label, int maxLength, boolean allowNeg){
        LayoutEdittextBinding binding = getEdittextBinding(label);
        binding.et.setInputType(InputType.TYPE_CLASS_PHONE);
        binding.et.setFilters(getNumberInputFilters(allowNeg, maxLength));
        binding.et.setHint("默认为0");
        return binding;
    }

    protected LayoutTwoSelectionBinding getTwoSelectionBinding(String label, String[] twoSelections){
        RadioTextView.clearGroup(label);
        LayoutTwoSelectionBinding binding = DataBindingUtil.bind(LayoutInflater.from(this).inflate(R.layout.layout_two_selection, null));
        assert  binding != null;
        binding.tvLabel.setText(label);
        binding.tvSelection1.setGroupId(label);
        binding.tvSelection1.setText(twoSelections[0]);
        binding.tvSelection2.setGroupId(label);
        binding.tvSelection2.setText(twoSelections[1]);
        return binding;
    }

    protected LayoutAlignmentBinding getAlignmentBinding(String label){
        return getAlignmentBinding(label, false);
    }

    protected LayoutAlignmentBinding getAlignmentBinding(String label, boolean vertical){
        RadioTextView.clearGroup(label);
        LayoutAlignmentBinding binding = DataBindingUtil.bind(LayoutInflater.from(this).inflate(R.layout.layout_alignment, null));
        assert binding != null;
        binding.tvLabel.setText(label);
        binding.tvAlignLeft.setGroupId(label);
        binding.tvAlignMiddle.setGroupId(label);
        binding.tvAlignRight.setGroupId(label);
        if(vertical){
            binding.tvAlignLeft.setText("顶部");
            binding.tvAlignMiddle.setText("居中");
            binding.tvAlignRight.setText("底部");
        }
        return binding;
    }

    protected int getAlignment(String alignmentStr){
        return getAlignment(alignmentStr, false);
    }

    protected int getAlignment(String alignmentStr, boolean vertical){
        if(vertical){
            switch (alignmentStr){
                case "顶部":
                    return Gravity.TOP;
                case "居中":
                    return Gravity.CENTER_VERTICAL;
                case "底部":
                    return Gravity.BOTTOM;
                default:
                    return Gravity.TOP;
            }
        } else {
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
    }

    protected InputFilter[] getNumberInputFilters(boolean allowNeg, int maxLength){
        return new InputFilter[]{
                new InputFilter.LengthFilter(maxLength),
                (source, start, end, dest, dstart, dend) -> {
                    String allDigits = allowNeg ? "-1234567890" : "1234567890";
                    String result = source.toString();
                    for(int i = end - 1; i >= start; i--){
                        String c = String.valueOf(source.charAt(i));
                        if(!allDigits.contains(c)){
                            result = result.replace(c, "");
                        }
                    }
                    return result;
                }
        };
    }

    protected boolean isNumberValid(String numberStr){
        if(StringUtil.isEmpty(numberStr)){
            return false;
        }
        return !"-".equals(numberStr);
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

    protected LayoutDescriptionBinding getDescriptionBinding(String desc){
        LayoutDescriptionBinding binding = DataBindingUtil.bind(LayoutInflater.from(this).inflate(R.layout.layout_description, null));
        assert binding != null;
        binding.tvDesc.setText(desc);
        return binding;
    }

    protected boolean checkColorString(String colorStr, CharSequence label){
        if(!isColorValid(colorStr)){
            ToastUtil.shortToast("请输入有效的文字颜色(" + label + ")");
            return false;
        }
        return true;
    }

    protected boolean isSharedWidget(){
        return false;
    }

    protected String getSharedUserName(){
        return "@喵了";
    }

    protected String getSharedUserNameColorStr(){
        return "#0099EE";
    }

    protected boolean needReadStorage(){
        return false;
    }

}
