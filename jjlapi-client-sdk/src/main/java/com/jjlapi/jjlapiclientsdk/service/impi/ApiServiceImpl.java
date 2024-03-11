package com.jjlapi.jjlapiclientsdk.service.impi;

import com.jjlapi.jjlapiclientsdk.client.JjlApiClient;
import com.jjlapi.jjlapiclientsdk.exception.ApiException;
import com.jjlapi.jjlapiclientsdk.model.request.*;
import com.jjlapi.jjlapiclientsdk.model.response.LoveResponse;
import com.jjlapi.jjlapiclientsdk.model.response.PoisonousChickenSoupResponse;
import com.jjlapi.jjlapiclientsdk.model.response.RandomWallpaperResponse;
import com.jjlapi.jjlapiclientsdk.model.response.ResultResponse;
import com.jjlapi.jjlapiclientsdk.service.ApiService;
import com.jjlapi.jjlapiclientsdk.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @Author: jinjunling
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@Service
public class ApiServiceImpl extends BaseService implements ApiService {

    @Override
    public PoisonousChickenSoupResponse getPoisonousChickenSoup() throws ApiException {
        PoisonousChickenSoupRequest request = new PoisonousChickenSoupRequest();
        return request(request);
    }

    @Override
    public PoisonousChickenSoupResponse getPoisonousChickenSoup(JjlApiClient jjlApiClient) throws ApiException {
        PoisonousChickenSoupRequest request = new PoisonousChickenSoupRequest();
        return request(jjlApiClient, request);
    }

    @Override
    public RandomWallpaperResponse getRandomWallpaper(RandomWallpaperRequest request) throws ApiException {
        return request(request);
    }

    @Override
    public RandomWallpaperResponse getRandomWallpaper(JjlApiClient jjlApiClient, RandomWallpaperRequest request) throws ApiException {
        return request(jjlApiClient, request);
    }

    @Override
    public LoveResponse randomLoveTalk() throws ApiException {
        LoveRequest request = new LoveRequest();
        return request(request);
    }

    @Override
    public LoveResponse randomLoveTalk(JjlApiClient jjlApiClient) throws ApiException {
        LoveRequest request = new LoveRequest();
        return request(jjlApiClient, request);
    }

    @Override
    public ResultResponse horoscope(HoroscopeRequest request) throws ApiException {
        return request(request);
    }

    @Override
    public ResultResponse horoscope(JjlApiClient jjlApiClient, HoroscopeRequest request) throws ApiException {
        return request(jjlApiClient, request);
    }

    @Override
    public ResultResponse getIpInfo(JjlApiClient jjlApiClient, IpInfoRequest request) throws ApiException {
        return request(jjlApiClient, request);
    }

    @Override
    public ResultResponse getIpInfo(IpInfoRequest request) throws ApiException {
        return request(request);
    }

    @Override
    public ResultResponse getWeatherInfo(JjlApiClient jjlApiClient, WeatherRequest request) throws ApiException {
        return request(jjlApiClient, request);
    }

    @Override
    public ResultResponse getWeatherInfo(WeatherRequest request) throws ApiException {
        return request(request);
    }
}
