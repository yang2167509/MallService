package com.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer028762Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer028762Application.class, args);
    }

}
