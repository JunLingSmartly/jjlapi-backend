package com.jjlapi.jjlapiclientsdk.model.request;

import com.jjlapi.jjlapiclientsdk.model.enums.RequestMethodEnum;
import com.jjlapi.jjlapiclientsdk.model.params.WeatherParams;
import com.jjlapi.jjlapiclientsdk.model.response.NameResponse;
import com.jjlapi.jjlapiclientsdk.model.response.ResultResponse;
import lombok.experimental.Accessors;

/**
 * @Author: QiMu
 * @Date: 2023/09/22 10:11:43
 * @Version: 1.0
 * @Description: 获取天气请求
 */
@Accessors(chain = true)
public class WeatherRequest extends BaseRequest<WeatherParams, ResultResponse> {

    @Override
    public String getPath() {
        return "/weather";
    }

    /**
     * 获取响应类
     *
     * @return {@link Class}<{@link NameResponse}>
     */
    @Override
    public Class<ResultResponse> getResponseClass() {
        return ResultResponse.class;
    }


    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }
}
