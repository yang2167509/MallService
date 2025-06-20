package com.ms.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@EnableHystrix
@RestController
public class adController {

    private static final Logger logger = LoggerFactory.getLogger(adController.class);

    @GetMapping("/state")
    public String getState() {
        logger.info("Accessing state");
        return "200";
    }

    @HystrixCommand(
            commandKey = "createOrder",
            commandProperties = {
                    @HystrixProperty(name="execution.timeout.enabled", value="true"),
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="10000"),
            },
            fallbackMethod = "createOrderFallbackMethod4Timeout"
    )
    @GetMapping("/autoGetAdInfo")
    public String autoGetAdInfo() {
        Random random = new Random();
        int sleepSeconds = 1 + random.nextInt(2);

        try {
            Thread.sleep(sleepSeconds * 1000L); // 将秒转换为毫秒
            logger.info("Sleeping for {} seconds", sleepSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重新中断当前线程
            logger.error("Thread was interrupted", e);
            return "Error: Thread was interrupted.<br/>";
        }

        return "Auto Get Ad Info.<br/>";
    }
    public String createOrderFallbackMethod4Timeout() throws Exception {
        System.err.println("-------Timeout degradation strategy execution------------");//超时降级策略执行
        return "ad service is busy, please try again later.";
    }
}