package com.ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class msgController {

    private static final Logger logger = LoggerFactory.getLogger(msgController.class);

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/state")
    public String getState(){
        return "200";
    }

    @GetMapping(value = "/{name}/{age}")
    public String query(@PathVariable("name") String name,
                      @PathVariable("age") String age
    )
    {
        System.out.println(name+age);
        return name;
    }


    @RequestMapping("/checkMsg")
    public String checkMsg(){
        String res = "Enter the store!<br/>";
        Random random = new Random();
        int sleepSeconds = 100 + random.nextInt(20);
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



//        String str = getStr("store-service");
        String str = "8011";

//        String adUrl = "http://192.168.100.26:"+str+"/communicate";
//        String adUrl = "http://localhost:"+str+"/communicate";
        String adUrl = "http://store-service-8011:8011/communicate";


        String adRes = restTemplate.getForObject(adUrl, String.class);

        return res+adRes;
    }

    @Value("${server.port}")
    private int serverPort;
    /**
     * 对服务进行启动
     * @param serviceName
     * @return
     */
    public String getStr(String serviceName){

//        String url = "http://192.168.100.26:6789/startService?serviceName={serviceName}&servicePort={servicePort}";
        String url = "http://localhost:6789/startService?serviceName={serviceName}&servicePort={servicePort}";

        HashMap<String, String> map = new HashMap<>();
        System.out.println("serverPort");
        map.put("serviceName", serviceName);
        map.put("servicePort", String.valueOf(serverPort));
        return restTemplate.getForObject(url, String.class, map);
    }
}
