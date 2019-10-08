package cn.ommiao.mowidgets.httpcalls.weather;

import cn.ommiao.mowidgets.httpcalls.weather.model.WeatherIn;
import cn.ommiao.mowidgets.httpcalls.weather.model.WeatherOut;
import cn.ommiao.network.BaseRequest;
import cn.ommiao.network.ErrorCodes;

public abstract class BaseWeatherCall extends BaseRequest<WeatherIn, WeatherOut> {

    @Override
    protected String api() {
        return key() + "?location={location}&key={key}";
    }

    protected abstract String key();

    @Override
    protected Class<WeatherOut> outClass() {
        return WeatherOut.class;
    }

    @Override
    protected int method() {
        return GET;
    }

    @Override
    protected String extraHandle(String res) {
        WeatherOut out = WeatherOut.fromJson(res, WeatherOut.class);
        if("ok".equals(out.getStatus())){
            out.setCode(ErrorCodes.SUCCESS);
        } else {
            out.setCode(-1);
            out.setMessage("No Weather Data.");
        }
        return out.toJson();
    }


}
