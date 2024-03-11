package com.jjlapi.jjlapiinterface.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import static com.jjlapi.jjlapiinterface.utils.RequestUtils.get;
import com.jjlapi.jjlapiclientsdk.model.response.ResultResponse;

import java.util.Map;

/**
 * @Author: jinjunling
 * @CreateTime: 2024/3/7
 * @Description:
 */
public class ResponseUtils {
    public static Map<String, Object> responseToMap(String response) {
        return new Gson().fromJson(response, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    public static <T> ResultResponse baseResponse(String baseUrl, T params) {
        String response = null;
        try {
            response = get(baseUrl, params);
            Map<String, Object> fromResponse = responseToMap(response);
            boolean success = (boolean) fromResponse.get("success");
            ResultResponse baseResponse = new ResultResponse();
            if (!success) {
                baseResponse.setData(fromResponse);
                return baseResponse;
            }
            fromResponse.remove("success");
            baseResponse.setData(fromResponse);
            return baseResponse;
        } catch (Exception e) {
            return null;
        }
    }
}
