package com.rookies4.mylabspringboot.config;

import com.rookies4.mylabspringboot.config.env.MyEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfig {
    @Bean
    public MyEnvironment myEnvironment(){
        return MyEnvironment.builder().mode("개발환경").build();
    }
}
