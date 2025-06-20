package com.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TrafficService8010Application {

    public static void main(String[] args) {
        SpringApplication.run(TrafficService8010Application.class, args);
    }
    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
