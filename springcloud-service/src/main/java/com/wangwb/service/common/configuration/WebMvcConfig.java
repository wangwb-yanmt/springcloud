package com.wangwb.service.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 	WebMvc配置
 * @author wangwb
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	/**
	 * 设置默认访问页面
	 */
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/public/login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
