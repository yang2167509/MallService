package com.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAspectJAutoProxy
public class GoodsService8004Application {

    public static void main(String[] args) {
        SpringApplication.run(GoodsService8004Application.class, args);
    }
    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();

    }
}
