package com.ms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.NestedServletException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class memberController {

    private static final Logger logger = LoggerFactory.getLogger(memberController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/updateMem")
    public String updateMem(){
        Random random = new Random();
        int sleepSeconds = 1000 + random.nextInt(200);
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


        String res = "Member points have been updated.<br/>";
        return res;
    }

    @RequestMapping("/c1")
    public String c1(){
        String content = "this is c1";
        String url1 = "http://member-service-8008:8008/c2";
        String url2 = "http://traffic-service-8010:8010/d3";

        String res1 = restTemplate.getForObject(url1, String.class).toString();
        String res2 = restTemplate.getForObject(url2, String.class).toString();

        return content+res1+res2;
    }

    @RequestMapping("/c2")
    public String c2(){
        String content = "this is c2";
        // 在此处执行购买逻辑，例如扣除库存、生成订单等
        // 这里只是一个简单的示例，不包含真实的购买逻辑

        Random random = new Random();
        boolean isSuccessful = random.nextBoolean(); // 随机生成购买是否成功

        if (isSuccessful) {
            // 购买成功
            int orderId = random.nextInt(1000);  // 随机生成订单ID
            double totalPrice = random.nextDouble() * 1000; // 随机生成订单总价
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("order_id", orderId);
            result.put("total_price", totalPrice);
            result.put("message", "购买成功");
//            result.put("success_content", "this is c2");
            return result.toString();

        } else {
            // 购买失败
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "购买失败，请稍后重试");
//            result.put("failure_content", "this is c2");
            return result.toString();
        }


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


    @RequestMapping("/state")
    public String getState(){
        return "200";
    }
}
