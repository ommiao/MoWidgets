package cn.ommiao.mowidgets.widgets.others;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.concurrent.CopyOnWriteArrayList;

import cn.ommiao.mowidgets.R;
import cn.ommiao.mowidgets.utils.StringUtil;

public class RadioTextView extends TextView {

    private static CopyOnWriteArrayList<RadioTextView> allRadioTextViews = new CopyOnWriteArrayList<>();

    private String groupId;

    private int normalBackgroudRes, checkedBackgroundRes;

    private int normalTextColorRes, checkedTextColorRes;

    private boolean checked;

    public static String getCheckedString(String groupId){
        if(StringUtil.isEmpty(groupId)){
            return null;
        }
        for (RadioTextView radioTextView : allRadioTextViews){
            if(groupId.equals(radioTextView.groupId) && radioTextView.checked){
                return radioTextView.getText().toString();
            }
        }
        return null;
    }

    public static void clearGroup(String groupId){
        for (RadioTextView radioTextView : allRadioTextViews){
            if(radioTextView.groupId.equals(groupId)){
                allRadioTextViews.remove(radioTextView);
            }
        }
    }

    public RadioTextView(Context context) {
        this(context, null);
    }

    public RadioTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RadioTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        allRadioTextViews.add(this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadioTextView);
        groupId = ta.getString(R.styleable.RadioTextView_groupId);
        normalBackgroudRes = ta.getResourceId(R.styleable.RadioTextView_normalBackground, R.drawable.shape_default_normal_radio_textview_bg);
        checkedBackgroundRes = ta.getResourceId(R.styleable.RadioTextView_checkedBackground, R.drawable.shape_default_checked_radio_textview_bg);
        normalTextColorRes = ta.getResourceId(R.styleable.RadioTextView_normalTextColor, getResources().getColor(R.color.defaultNormalRadioTextColor));
        checkedTextColorRes = ta.getResourceId(R.styleable.RadioTextView_checkedTextColor, getResources().getColor(R.color.defaultCheckedRadioTextColor));
        checked = ta.getBoolean(R.styleable.RadioTextView_checked, false);
        ta.recycle();
        init();
    }

    private void init() {
        if(checked){
            setBackgroundResource(checkedBackgroundRes);
            setTextColor(checkedTextColorRes);
        } else {
            setBackgroundResource(normalBackgroudRes);
            setTextColor(normalTextColorRes);
        }
        setOnClickListener(view -> {
            if(!checked){
                for(RadioTextView radioTextView : allRadioTextViews){
                    if(radioTextView.groupId.equals(groupId)){
                        radioTextView.checked = false;
                        radioTextView.setBackgroundResource(radioTextView.normalBackgroudRes);
                        radioTextView.setTextColor(normalTextColorRes);
                    }
                }
                checked = true;
                setBackgroundResource(checkedBackgroundRes);
                setTextColor(checkedTextColorRes);
            }
        });
    }

    public void setGroupId(String groupId){
        this.groupId = groupId;
    }
}
