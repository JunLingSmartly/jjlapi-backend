package com.jjlapi.jjlapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.jjlapi.jjlapiclientsdk.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import static com.jjlapi.jjlapiclientsdk.utils.SignUtils.genSign;

/**
 * @Author: jinjunling
 * @CreateTime: 2024/1/11
 * @Description:   调用第三方接口的客户端
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JjlApiClient {

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;

}
