package com.jjlapi.jjlapiclientsdk.model.request;


import com.jjlapi.jjlapiclientsdk.model.enums.RequestMethodEnum;
import com.jjlapi.jjlapiclientsdk.model.params.IpInfoParams;
import com.jjlapi.jjlapiclientsdk.model.response.ResultResponse;
import lombok.experimental.Accessors;

/**
 * @Author: QiMu
 * @Date: 2023/09/22 09:04:04
 * @Version: 1.0
 * @Description: 获取ip地址请求
 */
@Accessors(chain = true)
public class IpInfoRequest extends BaseRequest<IpInfoParams, ResultResponse> {

    @Override
    public String getPath() {
        return "/ipInfo";
    }

    /**
     * 获取响应类
     *
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
