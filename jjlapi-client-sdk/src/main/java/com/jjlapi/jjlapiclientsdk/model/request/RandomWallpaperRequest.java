package com.jjlapi.jjlapiclientsdk.model.request;

import com.jjlapi.jjlapiclientsdk.model.enums.RequestMethodEnum;
import com.jjlapi.jjlapiclientsdk.model.params.RandomWallpaperParams;
import com.jjlapi.jjlapiclientsdk.model.response.RandomWallpaperResponse;
import com.jjlapi.jjlapiclientsdk.model.response.ResultResponse;
import lombok.experimental.Accessors;

/**
 * @Author: QiMu
 * @Date: 2023年09月17日 08:38
 * @Version: 1.0
 * @Description:
 */
@Accessors(chain = true)
public class RandomWallpaperRequest extends BaseRequest<RandomWallpaperParams, RandomWallpaperResponse> {
    @Override
    public String getPath() {
        return "/randomWallpaper";
    }

    /**
     * 获取响应类
     *
     * @return {@link Class}<{@link ResultResponse}>
     */
    @Override
    public Class<RandomWallpaperResponse> getResponseClass() {
        return RandomWallpaperResponse.class;
    }


    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }
}
