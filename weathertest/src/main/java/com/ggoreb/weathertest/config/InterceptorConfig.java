package com.ggoreb.weathertest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ggoreb.weathertest.interceptor.SignInCheckInterceptor;




@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Autowired
	private SignInCheckInterceptor signInCheckInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(signInCheckInterceptor).addPathPatterns("/board/write").excludePathPatterns("/signin");
		registry.addInterceptor(signInCheckInterceptor).addPathPatterns("/board/update/**").excludePathPatterns("/signin");
		registry.addInterceptor(signInCheckInterceptor).addPathPatterns("/board/delete/**").excludePathPatterns("/signin");
		registry.addInterceptor(signInCheckInterceptor).addPathPatterns("/contact").excludePathPatterns("/signin");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}