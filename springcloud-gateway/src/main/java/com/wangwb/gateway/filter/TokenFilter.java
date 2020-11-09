package com.wangwb.gateway.filter;

import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wangwb.gateway.util.RedisUtil;

import reactor.core.publisher.Mono;

/**
 * token过滤器
 * @author wangwb
 *
 */
@Component
public class TokenFilter implements GlobalFilter, Ordered {
	
	@Resource
	private RedisUtil redisUtil;
	
	//密钥
	@Value("${jwt.secret}")
	private String secret;
	
	//无需过滤的url
	@Value("${jwt.passUrls}")
	private String[] passUrls;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		URI uri = exchange.getRequest().getURI();
		//获得请求的url
		String url = uri.getPath();
		//跳过不需要验证的请求
		if(!isNeedFilter(url)){
			return chain.filter(exchange);
        }
		ServerHttpResponse response = exchange.getResponse();
		ServerHttpRequest request = exchange.getRequest();
		//优先取header中的token
		String token = request.getHeaders().getFirst("token");
		if(token == null || token.isEmpty() || "null".equals(token)) {
				//1.返回信息方式
				JSONObject message = new JSONObject();
            message.put("code", -100);
            message.put("success",false);
            message.put("data", "token缺失！");
            byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            	//指定编码，否则在浏览器中会中文乱码
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
				//2.重定向到登录页方式(ajax请求无法跳转)
//				String redirectUrl = " http://192.168.120.208:9500/public/login.html ";
//            response.setStatusCode(HttpStatus.SEE_OTHER);
//            response.getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
//            return response.setComplete();
		}else {
			System.out.println("传来的token是："+token);
        	//判断token是否存在
        	boolean isHasKey = redisUtil.hasKey("token:"+token);
        	if(isHasKey) {
        		String loginId = redisUtil.get("token:"+token).toString();
        		redisUtil.expire("token:"+token, 7200);
        		return chain.filter(exchange);
        	} else {
        		JSONObject message = new JSONObject();
            message.put("code", -100);
            message.put("success",false);
            message.put("data", "token不存在或已失效！");
            byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            	//指定编码，否则在浏览器中会中文乱码
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        	}
//			try {
//				//验证器验证该token获得解码后的信息
//				JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
//				DecodedJWT jwt = verifier.verify(token);
//			} catch (JWTVerificationException e) {
//				e.printStackTrace();
//				JSONObject message = new JSONObject();
//	            message.put("code",-1);
//	            message.put("data", "无效token");
//	            byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
//	            DataBuffer buffer = response.bufferFactory().wrap(bits);
//	            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//	            //指定编码，否则在浏览器中会中文乱码
//	            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//	            return response.writeWith(Mono.just(buffer));
//			}
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }
	
    
    //判断请求的url是否需要过滤
  	private boolean isNeedFilter(String requestUrl) {
  		for (String unNeedFilterUrl : passUrls) {
              if(requestUrl.contains(unNeedFilterUrl)) {
                  return false;
              }
          }
          return true;
  	}
    
    
	
}
