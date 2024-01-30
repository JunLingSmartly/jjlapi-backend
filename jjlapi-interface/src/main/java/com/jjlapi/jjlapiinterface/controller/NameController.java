package com.jjlapi.jjlapiinterface.controller;

/**
 * @Author: jinjunling
 * @CreateTime: 2024/1/11
 * @Description:
 */

import com.jjlapi.jjlapiclientsdk.model.User;
import com.jjlapi.jjlapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称 API
 *
 * @author jjlapi
 */
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/")
    public String getNameByGet(String name) {
        return "GET 你的名字是" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是" + name;
    }

    @PostMapping("/getUserName")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        //从请求头中获取名为“accessKey”的值
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");

        // todo 实际情况应该是去数据库中查是否已分配给用户
        if (!accessKey.equals("jjlapi")){
            throw new RuntimeException("无权限");
        }
        // 校验随机数，模拟一下，直接判断nonce是否大于10000
        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }

        // todo 时间和当前时间不能超过5分钟
//        if (timestamp) {}
        String sign1 = SignUtils.genSign(body, "abcdefg");
        //如果生成的签名不一致，抛出异常，并提示“无权限”
        if (!sign.equals(sign1)){
            throw new RuntimeException("无权限");
        }

        String result = "POST 用户名字是" + user.getUsername();
        return result;
    }
}
