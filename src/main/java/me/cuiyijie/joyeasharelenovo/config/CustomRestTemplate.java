package me.cuiyijie.joyeasharelenovo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomRestTemplate {


    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
