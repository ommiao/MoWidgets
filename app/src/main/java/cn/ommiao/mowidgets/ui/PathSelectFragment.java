package cn.ommiao.mowidgets.ui;

import android.app.Activity;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import java.io.File;
import java.util.ArrayList;


import cn.ommiao.mowidgets.Constant;
import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.databinding.FragmentPathSelectBinding;
import cn.ommiao.mowidgets.entities.Path;
import cn.ommiao.mowidgets.ui.adapter.PathSelectAdapter;
import cn.ommiao.mowidgets.utils.ToastUtil;

import static cn.ommiao.mowidgets.Constant.MO_WIDGETS_DIR;


public class PathSelectFragment extends DialogFragment implements PathSelectAdapter.OnFileClickListener {

    private static final String INVALID_FILE = "Invalid File";

    public enum FileType {

        IMG(Constant.SUFFIX_IMG),
        FONT(Constant.SUFFIX_FONT);

        private String[] suffixArray;
        FileType(String[] suffixArray){
            this.suffixArray = suffixArray;
        }

        public String[] getSuffixArray() {
            return suffixArray;
        }

    }

    private FileType fileType = FileType.IMG;

    private FragmentPathSelectBinding mBinding;
    private Activity mActivity;

    private PathSelectAdapter adapter;
    private ArrayList<Path> list = new ArrayList<>();

    private String fileNameSelected = INVALID_FILE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_path_select, container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.rvFiles.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new PathSelectAdapter(list);
        adapter.setOnFileClickListener(this);
        mBinding.rvFiles.setAdapter(adapter);
        mBinding.btnRight.setOnClickListener(view -> {
            dismiss();
        });
        mBinding.btnLeft.setOnClickListener(view -> {
            if(onFileSelectListener != null){
                if(INVALID_FILE.equals(fileNameSelected)){
                    ToastUtil.shortToast("请选择文件");
                } else {
                    dismiss();
                    onFileSelectListener.onFileSelected("MoWidgets" + "/" + fileNameSelected);
                }
            } else {
                dismiss();
            }
        });
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    public void onResume() {
        super.onResume();
        File autoTaskDir = new File(MO_WIDGETS_DIR);
        if(autoTaskDir.exists()){
            String[] fileNames = autoTaskDir.list();
            if(fileNames != null){
                for (String fileName : fileNames) {
                    String[] targetTypes = fileType.getSuffixArray();
                    boolean contains = false;
                    for (String targetType : targetTypes) {
                        if(fileName.endsWith(targetType)){
                            contains = true;
                        }
                    }
                    if(contains){
                        Path path = new Path();
                        path.setFileName(fileName);
                        path.setSelected(false);
                        list.add(path);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void show(FragmentManager fragmentManager){
        show(fragmentManager, PathSelectFragment.class.getSimpleName());
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
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
        int screenHeight = metrics.heightPixels;
        attributes.width = screenWidth - getResources().getDimensionPixelSize(R.dimen.dialog_margin) * 2;
        attributes.height = screenHeight / 3 * 2;
        window.setAttributes(attributes);
    }

    private OnFileSelectListener onFileSelectListener;

    public void setOnFileSelectListener(OnFileSelectListener onFileSelectListener) {
        this.onFileSelectListener = onFileSelectListener;
    }

    private int getIndexSelected(String fileName){
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if(fileName.equals(list.get(i).getFileName())){
                index = i;
            }
        }
        return index;
    }

    @Override
    public void onFileClick(int pos) {
        Path path = list.get(pos);
        if(!fileNameSelected.equals(path.getFileName())){
            int oldIndex = getIndexSelected(fileNameSelected);
            if(oldIndex != -1){
                list.get(oldIndex).setSelected(false);
                adapter.notifyItemChanged(oldIndex, PathSelectAdapter.PAYLOAD_SELECTED);
            }
            fileNameSelected = path.getFileName();
            path.setSelected(true);
            adapter.notifyItemChanged(pos, PathSelectAdapter.PAYLOAD_SELECTED);
        }
    }

    public interface OnFileSelectListener {
        void onFileSelected(String path);
    }


}
