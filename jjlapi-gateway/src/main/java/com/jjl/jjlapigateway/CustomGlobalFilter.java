package com.jjl.jjlapigateway;

import com.jjlapi.jjlapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1．用户发送请求到AP工网关
        // 2．请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识，" +request.getId());
        log.info("请求路径:" + request.getPath().value( ));
        log.info("请求方法:" + request.getMethod() );
        log.info("请求参数;" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址:" + sourceAddress);
        log.info("请求来源地址:" + request.getRemoteAddress());
        //拿到响应对象
        ServerHttpResponse response = exchange.getResponse();

        // 3. 访问控制（黑白名单)
        if(!IP_WHITE_LIST.contains(sourceAddress)){
            //设置响应状态码为403 Forbidden（禁止访问>
            response.setStatusCode(HttpStatus.FORBIDDEN);
            //返回处理完成的响应
            return response.setComplete();
        }

        // 4．用户鉴权（判断ak. sk是否合法)
        // 首先从请求头中获取参数
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        // todo 实际情况应该是去数据库中查是否已分配给用户
        if (!accessKey.equals("junling")){
            return handleNoAuth(response);
        }
        // 直接校验如果随机数大于1万，则抛出异常，并提示"无权限"
        if (Long.parseLong(nonce) > 10000) {
            return handleNoAuth(response);
        }
        // todo 时间和当前时间不能超过5分钟
        // 首先,获取当前时间的时间戳,以秒为单位
        Long currentTime = System.currentTimeMillis() / 1000;
        // 定义一个常量FIVE_MINUTES,表示五分钟的时间间隔(乘以60,将分钟转换为秒,得到五分钟的时间间隔)。
        final Long FIVE_MINUTES = 60 * 5L;
        // 判断当前时间与传入的时间戳是否相差五分钟或以上,Long.parseLong(timestamp)将传入的时间戳转换成长整型.然后计算当前时间与传入时间戳之间的差值(以秒为单位),如果差值大于等于五分钟,则返回true,否则返回false
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            return handleNoAuth(response);
        }

        // todo 实际情况中是从数据库中查出 secretKey
        String serverSign = SignUtils.genSign(body, "abcdefgh");
        if (!sign.equals(serverSign)) {
            return handleNoAuth(response);
        }

        // 5．请求的模拟接口是否存在?
        // 6．请求转发，调用模拟接口
        // 7．响应日志
        // 8．调用成功，接口调用次数+ 1
        // 9．调用失败，返回一个规范的错误码
        log.info("custom global filter");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

}