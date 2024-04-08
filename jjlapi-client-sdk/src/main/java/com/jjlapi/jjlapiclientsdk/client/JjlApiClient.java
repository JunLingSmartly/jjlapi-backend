package com.jjlapi.jjlapiclientsdk.client;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.jjlapi.jjlapiclientsdk.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import static com.jjlapi.jjlapiclientsdk.utils.SignUtils.genSign;

/**
 * @Author: jinjunling
 * @CreateTime: 2024/1/11
 * @Description:   调用第三方接口的客户端
 */
@Data
@NoArgsConstructor
public class JjlApiClient {

    public static String GATEWAY_HOST = "http://localhost:8090";

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;

    public JjlApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public void setGatewayHost(String gatewayHost) {
        GATEWAY_HOST = gatewayHost;
    }


    private Map<String, String> getHeaderMap(String body, String method) throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<>();
        map.put("accessKey", accessKey);
        map.put("nonce", RandomUtil.randomNumbers(4));
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        body = URLUtil.encode(body, CharsetUtil.CHARSET_UTF_8);
        if(body == null){
            body = "";
        }
        map.put("sign", genSign(body, secretKey));
        map.put("body", body);
        map.put("method", method);
        return map;
    }

    public String invokeInterface(String params, String url, String method) throws UnsupportedEncodingException {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + url)
                .header("Accept-Charset", CharsetUtil.UTF_8)
                .addHeaders(getHeaderMap(params, method))
                .body(params)
                .execute();
        return JSONUtil.formatJsonStr(httpResponse.body());
    }
}
