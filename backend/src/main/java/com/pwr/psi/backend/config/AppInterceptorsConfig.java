package com.pwr.psi.backend.config;

import com.pwr.psi.backend.interceptor.ServiceInterceptior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class AppInterceptorsConfig implements WebMvcConfigurer {

    @Autowired
    ServiceInterceptior serviceInterceptior;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serviceInterceptior);
    }
}
