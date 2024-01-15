package com.jjlapi.jjlapiinterface;

import com.jjlapi.jjlapiinterface.client.JjlApiClient;
import com.jjlapi.jjlapiinterface.model.User;

/**
 * @Author: jinjunling
 * @CreateTime: 2024/1/12
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        String accessKey = "jjlapi";
        String secretKey = "abcdefg";
        JjlApiClient jjlApiClient = new JjlApiClient(accessKey,secretKey);
        String result1 = jjlApiClient.getNameByGet("JinJunling");
        String result2 = jjlApiClient.getNameByPost("JunLing-Jin");
        User user = new User();
        user.setUsername("JJL");
        String result3 = jjlApiClient.getUserNameByPost(user);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }
}
