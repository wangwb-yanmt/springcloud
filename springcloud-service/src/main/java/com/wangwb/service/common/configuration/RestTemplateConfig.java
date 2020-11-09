package com.wangwb.service.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 	RestTemplate配置类，一般不用任何配置也可使用
 * @author wangwb
 *
 */
public class RestTemplateConfig {
	
	@Bean
	public RestTemplate registerTemplate() {
		RestTemplate restTemplate = new RestTemplate(getFactory());
		//这个地方需要配置消息转换器，不然收到消息后转换会出现异常
//        restTemplate.setMessageConverters(getConverts());
        return restTemplate;
	}
	
	/**
	 * 	初始化请求工厂
	 * @return
	 */
	private SimpleClientHttpRequestFactory getFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //设置建立连接的超时时间
        factory.setConnectTimeout(3600);
        //设置传递数据的超时时间
        factory.setReadTimeout(3600);
        return factory;
    }

}
