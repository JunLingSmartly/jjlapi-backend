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
}
