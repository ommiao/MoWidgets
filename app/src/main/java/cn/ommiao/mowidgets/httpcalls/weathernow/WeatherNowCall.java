package cn.ommiao.mowidgets.httpcalls.weathernow;

import cn.ommiao.mowidgets.httpcalls.weathernow.model.WeatherNowIn;
import cn.ommiao.mowidgets.httpcalls.weathernow.model.WeatherNowOut;
import cn.ommiao.network.BaseRequest;
import cn.ommiao.network.ErrorCodes;

public class WeatherNowCall extends BaseRequest<WeatherNowIn, WeatherNowOut> {

    @Override
    protected String api() {
        return "now?location={location}&key={key}";
    }

    @Override
    protected Class<WeatherNowOut> outClass() {
        return WeatherNowOut.class;
    }

    @Override
    protected int method() {
        return GET;
    }

    @Override
    protected String extraHandle(String res) {
        WeatherNowOut out = WeatherNowOut.fromJson(res, WeatherNowOut.class);
        if(res.contains("now")){
            out.setCode(ErrorCodes.SUCCESS);
        } else {
            out.setCode(-1);
            out.setMessage("No Weather Data.");
        }
        return out.toJson();
    }

}
