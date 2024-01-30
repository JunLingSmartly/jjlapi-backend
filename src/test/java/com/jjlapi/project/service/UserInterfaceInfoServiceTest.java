package com.jjlapi.project.service;

import org.junit.jupiter.api.Assertions;
// 自动生成的包不对，要改成这个
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: jinjunling
 * @CreateTime: 2024/1/30
 * @Description:
 */
@SpringBootTest
public class UserInterfaceInfoServiceTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void invokeCount() {
        // 调用了userInterfaceInfoService的invokeCount方法，并传入两个参数(1L, 1L)
        boolean b = userInterfaceInfoService.invokeCount(1L, 1L);
        // 表示断言b的值为true，即测试用例期望invokeCount方法返回true
        Assertions.assertTrue(b);
    }
}