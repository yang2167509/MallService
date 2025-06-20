package com.ms.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@EnableHystrix
@RestController
public class favController {

    private static final Logger logger = LoggerFactory.getLogger(favController.class);

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/test")
    public String test(){

        String re = favList();

        return "test"+re;
    }

    @HystrixCommand(
            commandKey = "createOrder",
            commandProperties = {
                    @HystrixProperty(name="execution.timeout.enabled", value="true"),
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="10000"),
            },
            fallbackMethod = "createOrderFallbackMethod4Timeout"
    )
    @RequestMapping("/favList")
    public String favList(){
        String res = "There are Fav List!<br/>";

        int sleepSeconds =  0;

// 生成一个随机的毫秒数，范围从 0 到 999（包含）
        long randomMilliseconds = 1000 + ThreadLocalRandom.current().nextLong(500);
        sleepSeconds = (int) randomMilliseconds ;

        try {
            Thread.sleep(sleepSeconds ); // 将秒转换为毫秒
            logger.info("Sleeping for {} seconds", sleepSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重新中断当前线程
            logger.error("Thread was interrupted", e);
            return "Error: Thread was interrupted.<br/>";
        }
        return res;
    }

    public String createOrderFallbackMethod4Timeout() throws Exception {
        System.err.println("-------Timeout degradation strategy execution------------");//超时降级策略执行
        return "favorite service is busy, please try again later.";
    }


    @RequestMapping("/state")
    public String getState(){
        return "200";
    }

    @RequestMapping("/test_k8s")
    public String test_k8s(){
        String res = "this 测试能否在k8s内部自动识别ad-service-8002";

        String url = "http://ad-service-8002:8002/autoGetAdInfo"; //gjw  测试能否在k8s内部自动识别ad-service-8002

        String ad = restTemplate.getForObject(url, String.class);
        return res+ad;
    }
}
