package com.ms.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.netty.util.internal.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
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

@EnableHystrix
@RestController
public class frontedController {
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(frontedController.class);


    @RequestMapping("/state")
    public String getState(){


        return "200";
    }


    @HystrixCommand(
            commandKey = "createOrder",
            commandProperties = {
                    @HystrixProperty(name="execution.timeout.enabled", value="true"),
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000"),
            },
            fallbackMethod = "createOrderFallbackMethod4Timeout"
    )
    @RequestMapping("/login")
    public String login(){
        String res = "access into index!<br/>";

//        String port1 = getStr("ad-service", String.valueOf(serverPort ));
        String port1="8002/";

//        String adUrl = "http://192.168.100.26:"+port1+"autoGetAdInfo";
//        String adUrl = "http://localhost:"+port1+"autoGetAdInfo";

        String adUrl = "http://ad-service-8002:8002/autoGetAdInfo";

        String adRes = restTemplate.getForObject(adUrl, String.class);

//        String port2 = getStr("recommend-service",port1);
        String port2="8003/";

//        String recommendUrl = "http://192.168.100.26:"+port2+"autoGetRecommendInfo";
//        String recommendUrl = "http://localhost:"+port2+"autoGetRecommendInfo";
        String recommendUrl = "http://recommend-service-8003:8003/autoGetRecommendInfo";

        String recommendRes = restTemplate.getForObject(recommendUrl, String.class);

        int sleepSeconds =  0;
        // 生成一个随机的毫秒数，范围从 0 到 999（包含）
        long randomMilliseconds = 1000 + ThreadLocalRandom.current().nextLong(500);
        sleepSeconds = (int) randomMilliseconds ;

        try {
            Thread.sleep(sleepSeconds); // 将秒转换为毫秒
            logger.info("Sleeping for {} seconds", sleepSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重新中断当前线程
            logger.error("Thread was interrupted", e);
            return "Error: Thread was interrupted.<br/>";
        }
        return res+adRes+recommendRes;
    }

    public String createOrderFallbackMethod4Timeout() throws Exception {
        System.err.println("-------超时降级策略执行------------");
        return "login timeout";
    }

    @RequestMapping("/msg")
    public String msg(){
        String res = "There are new message!<br/>";

//        String str = getStr("message-service", String.valueOf(serverPort));
        String str = "8009";

//        String adUrl = "http://192.168.100.26:"+str+"checkMsg";
//        String adUrl = "http://localhost:"+str+"checkMsg";

        String adUrl = "http://message-service-8009:8009/checkMsg";
        int sleepSecond = 0;
        // 生成一个随机的毫秒数，范围从 0 到 999（包含）
        long randomMilliseconds = 1000 + ThreadLocalRandom.current().nextLong(500);
        sleepSecond = (int) randomMilliseconds ;

        try {
            Thread.sleep(sleepSecond); // 将秒转换为毫秒
            logger.info("Sleeping for {} seconds", sleepSecond);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重新中断当前线程
            logger.error("Thread was interrupted", e);
            return "Error: Thread was interrupted.<br/>";
        }


        String adRes = restTemplate.getForObject(adUrl, String.class);

        return res+adRes;
    }

    @RequestMapping("/goFav")
    public String goFav(){

//        String str = getStr("favorite-service", String.valueOf(serverPort));
        String str = "8013";

        String res = "check my Fav!<br/>";
//        String adUrl = "http://192.168.100.26:"+str+"/favList";
//        String adUrl = "http://localhost:"+str+"/favList";  //FAVORITE-SERVICE
        String adUrl = "http://favorite-service-8013:8013/favList";

        int sleepSeconds = 0;
        // 生成一个随机的毫秒数，范围从 0 到 999（包含）
        long randomMilliseconds = 1000 + ThreadLocalRandom.current().nextLong(500);
        sleepSeconds = (int) randomMilliseconds ;

        try {
            Thread.sleep(sleepSeconds); // 将秒转换为毫秒
            logger.info("Sleeping for {} seconds", sleepSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重新中断当前线程
            logger.error("Thread was interrupted", e);
            return "Error: Thread was interrupted.<br/>";
        }

        String adRes = restTemplate.getForObject(adUrl, String.class);
        return res+adRes;
    }

    @RequestMapping("/b1")
    public String b1(){
        String url = "http://fronted-service/b2";
        String res = restTemplate.getForObject(url, String.class);
        String content = "this is b1";
        return content+res;
    }

    @RequestMapping("/span")
    public String span(){
        String url = "http://favorite-service/test";
        String res = restTemplate.getForObject(url, String.class);
        String content = "8001";

        return content+res;
    }



    @RequestMapping("/b2")
    public String b2(){
        String url1 = "http://member-service/c1";
        String url2 = "http://traffic-service/d1";

        String res1 = restTemplate.getForObject(url1, String.class);
        String res2 = restTemplate.getForObject(url2, String.class);

        String content = "this is b2";

        Random random = new Random();
        boolean isSuccessful = random.nextBoolean(); // 随机生成注册是否成功

        if (isSuccessful) {
            // 用户注册成功
            int userId = random.nextInt(1000);  // 随机生成用户ID
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("user_id", userId);
            result.put("message", "用户注册成功");
            result.put("content", "this is b2"+res1+res2);
            return result.toString();
        } else {
            // 用户注册失败
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "用户注册失败，请稍后重试");
            result.put("content", "this is b2");
            return result.toString();
        }

    }


//    @Value("${server.port}")
//    private int serverPort;
//    /**
//     * 对服务进行启动
//     * @param serviceName
//     * @return
//     */
//    public String getStr(String serviceName,String serverPort){
//
////        String url = "http://192.168.100.26:6789/startService?serviceName={serviceName}&servicePort={servicePort}";
//        String url = "http://localhost:6789/startService?serviceName={serviceName}&servicePort={servicePort}";
//
//        HashMap<String, String> map = new HashMap<>();
//        System.out.println("serverPort");
//        map.put("serviceName", serviceName);
//        map.put("servicePort", serverPort);
//        return restTemplate.getForObject(url, String.class, map);
//    }

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
