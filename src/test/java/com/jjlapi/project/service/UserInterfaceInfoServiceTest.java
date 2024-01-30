package com.jjlapi.project.service;

import org.junit.jupiter.api.Assertions;
// �Զ����ɵİ����ԣ�Ҫ�ĳ����
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
        // ������userInterfaceInfoService��invokeCount��������������������(1L, 1L)
        boolean b = userInterfaceInfoService.invokeCount(1L, 1L);
        // ��ʾ����b��ֵΪtrue����������������invokeCount��������true
        Assertions.assertTrue(b);
    }
}