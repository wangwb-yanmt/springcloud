package com.wangwb.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.bull.javamelody.MonitoringFilter;
import net.bull.javamelody.SessionListener;

@SpringBootApplication	//springboot启动类注解
@EnableEurekaClient  //开启eureka客户端
@EnableFeignClients	//开启feign客户端
@EnableScheduling	//开启定时任务
@EnableTransactionManagement  //其实默认是开启的
public class ServiceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDemoApplication.class, args);
	}
	
	@Bean
	@Order(Integer.MAX_VALUE - 1)
	public FilterRegistrationBean monitoringFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(new MonitoringFilter());
	    registration.addUrlPatterns("/*");
	    registration.setName("monitoring");
	    return registration;
	}
	  
	@Bean
	public ServletListenerRegistrationBean<SessionListener> servletListenerRegistrationBean() {
	    ServletListenerRegistrationBean<SessionListener> slrBean = new ServletListenerRegistrationBean<SessionListener>();
	    slrBean.setListener(new SessionListener());
	    return slrBean;
	}
	
}

