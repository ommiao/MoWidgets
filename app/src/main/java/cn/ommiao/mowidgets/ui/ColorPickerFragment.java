package cn.ommiao.mowidgets.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.FragmentColorPickerBinding;

public class ColorPickerFragment extends DialogFragment {

    private FragmentColorPickerBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_color_picker, container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.tvConfirm.setOnClickListener(view -> {
            dismiss();
            if(onColorPickerClickedListener != null){
                int color = mBinding.colorPickerView.getSelectedColor();
                onColorPickerClickedListener.onColorSelected(Integer.toHexString(color));
            }
        });
        mBinding.tvCancel.setOnClickListener(view -> {
            dismiss();
            if(onColorPickerClickedListener != null){
                onColorPickerClickedListener.onCancel();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        setCancelable(false);
        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        assert window != null;
        window.setWindowAnimations(R.style.dialog_enter_exit);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.width = screenWidth - getResources().getDimensionPixelSize(R.dimen.dialog_margin) * 2;
        window.setAttributes(attributes);
    }

    private OnColorPickerClickedListener onColorPickerClickedListener;

    public void setOnColorPickerClickedListener(OnColorPickerClickedListener onColorPickerClickedListener) {
        this.onColorPickerClickedListener = onColorPickerClickedListener;
    }

    public interface OnColorPickerClickedListener{
        void onColorSelected(String hexColor);
        void onCancel();
    }
}
