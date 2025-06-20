package com.ms.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.NestedServletException;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
@EnableHystrix
@RestController
public class trafficController {
    private static final Logger logger = LoggerFactory.getLogger(trafficController.class);

    int number =1;
    @HystrixCommand(
            commandKey = "createOrder",
            commandProperties = {
                    @HystrixProperty(name="execution.timeout.enabled", value="true"),
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="10000"),
            },
            fallbackMethod = "createOrderFallbackMethod4Timeout"
    )
    @RequestMapping("/shipping")
    public String shipping(){
        String res = "The product is being delivered<br/>";

        Random random = new Random();
        int sleepSeconds =  200 + random.nextInt(20);

// 生成一个随机的毫秒数，范围从 0 到 999（包含）
        long randomMilliseconds = ThreadLocalRandom.current().nextLong(100);
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
        return "traffic service is busy, please try again later.";
    }

    @RequestMapping("/d1")
    public String d1(){
        String res = "this is d1";
        return res;
    }

    @RequestMapping("/d3")
    public String d3(){
        String res = "this is d3 ,";

        for (int i = 0; i < 1000000; i++) {
            String item = res+"商品配购买数量为"+number;
            number++;
            return item;
        }
        return res+"商品已经全部卖掉,数量为1000000";
    }


    @RequestMapping("/state")
    public String getState(){
        return "200";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        HttpStatus status = getStatus(ex);
//        return "An error occurred: " + ex.getMessage();
        return "An error occurred: "+status+"+"+ex.toString();
    }

    private HttpStatus getStatus(Exception ex) {
        if (ex instanceof ResponseStatusException) {
            ResponseStatusException rse = (ResponseStatusException) ex;
            return rse.getStatus();
        } else if (ex instanceof NestedServletException) {
            NestedServletException nse = (NestedServletException) ex;
            return getStatus((Exception) nse.getRootCause());
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
