package com.wangwb.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableEurekaServer
public class RegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterApplication.class, args);
	}
	
	/*@EnableWebSecurity
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
//	        // Spring Security 默认开启了所有 CSRF 攻击防御，需要关闭 /eureka 的防御
	    	http.csrf().ignoringAntMatchers("/eureka/**");
	        super.configure(http);
	    }
	}*/

}

