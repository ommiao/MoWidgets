package cn.ommiao.mowidgets.widgets.pointerclock;

import cn.ommiao.mowidgets.requesters.BaseRequester;
import cn.ommiao.mowidgets.widgets.TimingRefreshWidget;

public abstract class BasePointerClockWidget<R extends BaseRequester> extends TimingRefreshWidget<R> {

    protected float getHourRotation(){
        float minute = getMinute() + 60 * (getHour() % 12);
        return minute / 720 * 360;
    }

    protected float getMinuteRotation(){
        float minute = getMinute();
        return minute / 60 * 360;
    }

}
