package com.jjlapi.jjlapigateway;

import com.jjlapi.jjlapiclientsdk.utils.SignUtils;
import com.jjlapi.jjlapicommon.model.entity.InterfaceInfo;
import com.jjlapi.jjlapicommon.model.entity.User;
import com.jjlapi.jjlapicommon.service.InnerInterfaceInfoService;
import com.jjlapi.jjlapicommon.service.InnerUserInterfaceInfoService;
import com.jjlapi.jjlapicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService innerUserService;
    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;
    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");
    //接口链接，应该从数据库中的接口表中的链接获取，这里暂时节省。
    // todo 从数据库中获取
    private static final String INTERFACE_HOST = "http://62.234.67.99:8123";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1．用户发送请求到AP工网关
        // 2．请求日志
        ServerHttpRequest request = exchange.getRequest();
        String api = request.getPath().value();
        String path = INTERFACE_HOST + api;
        String method = request.getMethod().toString();
        log.info("请求唯一标识，" +request.getId());
        log.info("请求路径:" + path);
        log.info("请求方法:" + method);
        log.info("请求参数;" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址:" + sourceAddress);
        log.info("请求来源地址:" + request.getRemoteAddress());
        //拿到响应对象
        ServerHttpResponse response = exchange.getResponse();

        // 3. 访问控制（黑白名单)
//        if(!IP_WHITE_LIST.contains(sourceAddress)){
//            //设置响应状态码为403 Forbidden（禁止访问>
//            response.setStatusCode(HttpStatus.FORBIDDEN);
//            //返回处理完成的响应
//            return response.setComplete();
//        }

        // 4．用户鉴权（判断ak. sk是否合法)
        // 首先从请求头中获取参数
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        // todo 实际情况应该是去数据库中查是否已分配给用户(已完善)
        User invokeUser = null;
        try{
            invokeUser = innerUserService.getInvokeUser(accessKey);
        }catch (Exception e){
            log.error("getInvokeUser error",e);
        }
            if(invokeUser == null){
            //如果用户信息为空，处理未授权情况并返回响应
            return handleNoAuth(response);
        }
//        if (!accessKey.equals("jjlapi")){
//            return handleNoAuth(response);
//        }
        // 直接校验如果随机数大于1万，则抛出异常，并提示"无权限"
        if (Long.parseLong(nonce) > 10000) {
            return handleNoAuth(response);
        }
        // 时间和当前时间不能超过5分钟
        // 首先,获取当前时间的时间戳,以秒为单位
        Long currentTime = System.currentTimeMillis() / 1000;
        // 定义一个常量FIVE_MINUTES,表示五分钟的时间间隔(乘以60,将分钟转换为秒,得到五分钟的时间间隔)。
        final Long FIVE_MINUTES = 60 * 5L;
        // 判断当前时间与传入的时间戳是否相差五分钟或以上,Long.parseLong(timestamp)将传入的时间戳转换成长整型.然后计算当前时间与传入时间戳之间的差值(以秒为单位),如果差值大于等于五分钟,则返回true,否则返回false
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            return handleNoAuth(response);
        }

        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.genSign(body,secretKey);
        if (sign == null || !sign.equals(serverSign)) {
            return handleNoAuth(response);
        }

        // 5．请求的模拟接口是否存在?
        // todo 从数据库中查询模拟接口是否存在，以及请求方法是否匹配(还可以校验请求参数)(已完善)
        InterfaceInfo interfaceInfo = null;
        try{
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(api,method);
        }catch (Exception e){
            log.error("getInterfaceInfo error",e);
        }
        if(interfaceInfo == null){
            return handleNoAuth(response);
        }
//        // 6．请求转发，调用模拟接口
//        Mono<Void> filter = chain.filter(exchange);
        //todo 是否有调用次数

        // 7．响应日志
        return handleResponse(exchange,chain,interfaceInfo.getId(),invokeUser.getId());
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,long interfaceInfoId,long userId) {
        try {
            // 获取原始的响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取响应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            // 判断状态码是否为200 OK(按道理来说,现在没有调用,是拿不到响应码的)
            if(statusCode == HttpStatus.OK) {
                // 创建一个装饰后的响应对象(开始穿装备，增强能力)
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    // 重写writeWith方法，用于处理响应体的数据
                    // 这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果，
                    // 就会调用writeWith方法,我们就能根据响应结果做一些自己的处理
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 判断响应体是否是Flux类型
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 返回一个处理后的响应体
                            // (这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 8．调用成功，接口调用次数+ 1
                                try {
                                    innerUserInterfaceInfoService.invokeCount(interfaceInfoId,userId);
                                }catch (Exception e){
                                    log.error("invokeCount error",e);
                                }
                                // 读取响应体的内容并转换为字节数组
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                log.info("响应结果"+data);//log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                // 将处理后的内容重新包装成DataBuffer并返回
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            //9.调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 对于200 OK的请求,将装饰后的响应对象传递给下一个过滤器链,并继续处理(设置repsonse对象为装饰过的)
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 对于非200 OK的请求，直接返回，进行降级处理
            return chain.filter(exchange);
        }catch (Exception e){
            // 处理异常情况，记录错误日志
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }


}