package com.jjlapi.jjlapiinterface.utils;

import cn.hutool.http.HttpRequest;
import com.jjlapi.jjlapiclientsdk.exception.ApiException;
import com.jjlapi.jjlapiclientsdk.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @Author: jinjunling
 * @CreateTime: 2024/3/7
 * @Description:
 */
@Slf4j
public class RequestUtils {

    /**
     * 生成url
     *
     * @param baseUrl 基本url
     * @param params  params
     * @return {@link String}
     * @throws ApiException api异常
     */
    public static <T> String buildUrl(String baseUrl, T params){
        StringBuilder url = new StringBuilder(baseUrl);
        Field[] fields = params.getClass().getDeclaredFields();
        boolean isFirstParam = true;
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            // 跳过serialVersionUID属性
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            try {
                Object value = field.get(params);
                if (value != null) {
                    if (isFirstParam) {
                        url.append("?").append(name).append("=").append(value);
                        isFirstParam = false;
                    } else {
                        url.append("&").append(name).append("=").append(value);
                    }
                }
            } catch (Exception e) {
                log.error("");
            }
        }
        return url.toString();
    }

    /**
     * get请求
     *
     * @param baseUrl 基本url
     * @param params  params
     * @return {@link String}
     * @throws ApiException api异常
     */
    public static <T> String get(String baseUrl, T params){
        return get(buildUrl(baseUrl, params));
    }

    /**
     * get请求
     *
     * @param url url
     * @return {@link String}
     */
    public static String get(String url) {
        String body = HttpRequest.get(url).execute().body();
        log.info("【interface】：请求地址：{}，响应数据：{}", url, body);
        return body;
    }
}