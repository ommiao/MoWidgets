package cn.ommiao.mowidgets.configs;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RemoteViews;

import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.ActivityConfigBinding;
import cn.ommiao.mowidgets.databinding.LayoutColorSelectorBinding;
import cn.ommiao.mowidgets.databinding.LayoutEdittextBinding;

public abstract class BaseConfigActivity extends Activity {

    private ArrayList<View> configList = new ArrayList<>();
    private ActivityConfigBinding mBinding;
    protected int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    protected AppWidgetManager appWidgetManager;

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
        initConfigViews();
        initViews();
    }

    private void initWindow(){
        Window window = getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = screenWidth - getResources().getDimensionPixelSize(R.dimen.dialog_margin) * 2;
        params.height = screenHeight / 2;
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
        appWidgetManager.updateAppWidget(widgetId, getRemoteViews());
        finish();
    }

    protected abstract RemoteViews getRemoteViews();

    protected abstract void initConfigViews();

    protected abstract String getConfigTitle();

    protected void addConfigView(View view){
        configList.add(view);
    }

    protected abstract boolean isDataValid();

    protected abstract void saveConfigs();

    protected LayoutColorSelectorBinding getColorSelectorBinding(String label){
        LayoutColorSelectorBinding binding = DataBindingUtil.bind(LayoutInflater.from(this).inflate(R.layout.layout_color_selector, null));
        assert binding != null;
        binding.tvLabel.setText(label);
        return binding;
    }

    protected LayoutEdittextBinding getEdittextBinding(String label){
        LayoutEdittextBinding binding = DataBindingUtil.bind(LayoutInflater.from(this).inflate(R.layout.layout_edittext, null));
        assert binding != null;
        binding.tvLabel.setText(label);
        return binding;
    }

}
