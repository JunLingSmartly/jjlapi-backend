package com.jjlapi.jjlapiinterface.controller;

/**
 * @Author: jinjunling
 * @CreateTime: 2024/1/11
 * @Description:
 */

import cn.hutool.json.JSONUtil;
import com.jjlapi.jjlapiclientsdk.exception.ApiException;
import com.jjlapi.jjlapiclientsdk.model.User;
import com.jjlapi.jjlapiclientsdk.model.params.HoroscopeParams;
import com.jjlapi.jjlapiclientsdk.model.params.IpInfoParams;
import com.jjlapi.jjlapiclientsdk.model.params.RandomWallpaperParams;
import com.jjlapi.jjlapiclientsdk.model.params.WeatherParams;
import com.jjlapi.jjlapiclientsdk.model.response.RandomWallpaperResponse;
import com.jjlapi.jjlapiclientsdk.model.response.ResultResponse;
import com.jjlapi.jjlapiclientsdk.utils.SignUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import static com.jjlapi.jjlapiinterface.utils.RequestUtils.get;
import static com.jjlapi.jjlapiinterface.utils.RequestUtils.buildUrl;
import static com.jjlapi.jjlapiinterface.utils.ResponseUtils.baseResponse;
import static com.jjlapi.jjlapiinterface.utils.ResponseUtils.responseToMap;

/**
 * 名称 API
 *
 * @author jjlapi
 */
@RestController
@RequestMapping("/jjl")
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String name) {
        return "GET 你的名字是" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是" + name;
    }

    @PostMapping("/getUserName")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        String result = "POST 用户名字是" + user.getUsername();
        return result;
    }

    @GetMapping("/loveTalk")
    public String randomLoveTalk() {
        return get("https://api.vvhan.com/api/love");
    }

    @GetMapping("/poisonousChickenSoup")
    public String getPoisonousChickenSoup() {
        return get("https://api.btstu.cn/yan/api.php?charset=utf-8&encode=json");
    }

    @GetMapping("/randomWallpaper")
    public RandomWallpaperResponse randomWallpaper(RandomWallpaperParams randomWallpaperParams) throws ApiException {
        String baseUrl = "https://api.btstu.cn/sjbz/api.php";
        String url = buildUrl(baseUrl, randomWallpaperParams);
        if (StringUtils.isAllBlank(randomWallpaperParams.getLx(), randomWallpaperParams.getMethod())) {
            url = url + "?format=json";
        } else {
            url = url + "&format=json";
        }
        return JSONUtil.toBean(get(url), RandomWallpaperResponse.class);
    }

    @GetMapping("/horoscope")
    public ResultResponse getHoroscope(HoroscopeParams horoscopeParams) throws ApiException {
        String response = get("https://api.vvhan.com/api/horoscope", horoscopeParams);
        Map<String, Object> fromResponse = responseToMap(response);
        boolean success = (boolean) fromResponse.get("success");
        if (!success) {
            ResultResponse baseResponse = new ResultResponse();
            baseResponse.setData(fromResponse);
            return baseResponse;
        }
        return JSONUtil.toBean(response, ResultResponse.class);
    }

    @GetMapping("/ipInfo")
    public ResultResponse getIpInfo(IpInfoParams ipInfoParams) {
        return baseResponse("https://api.vvhan.com/api/getIpInfo", ipInfoParams);
    }

    @GetMapping("/weather")
    public ResultResponse getWeatherInfo(WeatherParams weatherParams) {
        return baseResponse("https://api.vvhan.com/api/weather", weatherParams);
    }
}
