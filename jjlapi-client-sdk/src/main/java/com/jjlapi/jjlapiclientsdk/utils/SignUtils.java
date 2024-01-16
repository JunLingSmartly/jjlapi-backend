package com.jjlapi.jjlapiclientsdk.utils;

/**
 * @Author: jinjunling
 * @CreateTime: 2024/1/15
 * @Description:   生成签名
 */

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名工具
 */
public class SignUtils {
    /**
     * 生成签名
     * @param body
     * @param secretKey 密钥
     * @return 生成的签名字符串
     */
    public static String genSign(String body, String secretKey) {
        // 使用SHA256算法的Digester
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        // 构建签名内容，将哈希映射转换为字符串并拼接密钥
        String content = body + "." + secretKey;
        // 计算签名的摘要并返回摘要的十六进制表示形式
        return md5.digestHex(content);
    }
}
