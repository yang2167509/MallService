package com.ms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class payController {
    private static final Logger logger = LoggerFactory.getLogger(payController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/state")
    public String getState(){
        return "200";
    }

    @RequestMapping("/dopay")
    public String dopay(){
        String res = "order paid.<br/>";
        Random random = new Random();
        int sleepSeconds =  1000 + random.nextInt(200);

// 生成一个随机的毫秒数，范围从 0 到 999（包含）
        long randomMilliseconds = ThreadLocalRandom.current().nextLong(500);
        sleepSeconds = (int) randomMilliseconds + sleepSeconds;

        try {
            Thread.sleep(sleepSeconds); // 将秒转换为毫秒
            logger.info("Sleeping for {} seconds", sleepSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重新中断当前线程
            logger.error("Thread was interrupted", e);
            return "Error: Thread was interrupted.<br/>";
        }

//        String port1 = getStr("member-service", String.valueOf(serverPort));
        String port1 = "8008";

//        String memUrl = "http://192.168.100.26:"+port1+"/updateMem";
//        String memUrl = "http://localhost:"+port1+"/updateMem";
        String memUrl = "http://member-service-8008:8008/updateMem";

        String memRes = restTemplate.getForObject(memUrl, String.class);

//        String port2 = getStr("traffic-service",port1);
        String port2 = "8010";

//        String traUrl = "http://192.168.100.26:"+port2+"/shipping";
//        String traUrl = "http://localhost:"+port2+"/shipping";
        String traUrl = "http://traffic-service-8010:8010/shipping";


        String traRes = restTemplate.getForObject(traUrl,String.class);

        return res+memRes+traRes;
    }

    @Value("${server.port}")
    private int serverPort;
    /**
     * 对服务进行启动
     * @param serviceName
     * @return
     */
    public String getStr(String serviceName,String serverPort){

//        String url = "http://192.168.100.26:6789/startService?serviceName={serviceName}&servicePort={servicePort}";
        String url = "http://localhost:6789/startService?serviceName={serviceName}&servicePort={servicePort}";

        HashMap<String, String> map = new HashMap<>();
        System.out.println("serverPort");
        map.put("serviceName", serviceName);
        map.put("servicePort", String.valueOf(serverPort));
        return restTemplate.getForObject(url, String.class, map);
    }
}
