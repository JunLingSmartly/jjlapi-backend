package com.jjlapi.jjlapiinterface;

import com.jjlapi.jjlapiclientsdk.client.JjlApiClient;
import com.jjlapi.jjlapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class JjlapiInterfaceApplicationTests {
    // 注入一个名为jjlApiClient的Bean
    @Resource
    private JjlApiClient jjlApiClient;
    // 表示这是一个测试方法
    @Test
    void contextLoads() {
        // 调用jjlApiClient的getNameByGet方法，并传入参数"yupi"，将返回的结果赋值给result变量
        String result = jjlApiClient.getNameByGet("jjlpi");
        // 创建一个User对象
        User user = new User();
        // 设置User对象的username属性为"liyupi"
        user.setUsername("JunLing-Jin");
        // 调用jjlApiClient的getUserNameByPost方法，并传入user对象作为参数，将返回的结果赋值给usernameByPost变量
        String usernameByPost = jjlApiClient.getUserNameByPost(user);
        // 打印result变量的值
        System.out.println(result);
        // 打印usernameByPost变量的值
        System.out.println(usernameByPost);
    }


}
