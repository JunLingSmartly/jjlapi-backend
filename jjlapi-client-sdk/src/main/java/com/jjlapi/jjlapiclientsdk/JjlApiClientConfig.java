package com.jjlapi.jjlapiclientsdk;

import com.jjlapi.jjlapiclientsdk.client.JjlApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jinjunling
 * @CreateTime: 2024/1/15
 * @Description:
 */
@Configuration
@ConfigurationProperties("jjlapi.client")
@Data
@ComponentScan
public class JjlApiClientConfig {
    private String accessKey;

    private String secretKey;

    @Bean
    public JjlApiClient jjlApiClient(){
        //使用一个ak和sk创建一个JjlApiClient实例
        return new JjlApiClient(accessKey,secretKey);
    }
}
