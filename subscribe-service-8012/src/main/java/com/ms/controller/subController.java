package com.ms.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@EnableHystrix
@RestController
public class subController {
    private static final Logger logger = LoggerFactory.getLogger(subController.class);

    @RequestMapping("/state")
    public String getState(){
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
    @RequestMapping("/subscribe")
    public String subscribe(){
        String res = "Subscribe to stores!<br/>";

        Random random = new Random();
        int sleepSeconds =  800 + random.nextInt(200);

// 生成一个随机的毫秒数，范围从 0 到 999（包含）
        long randomMilliseconds = ThreadLocalRandom.current().nextLong(1000);
        sleepSeconds = (int) randomMilliseconds + sleepSeconds;

        try {
            Thread.sleep(sleepSeconds); // 将秒转换为毫秒
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
        return "subscribe service is busy, please try again later.";
    }
}
