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

@RestController
public class cartController {
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(cartController.class);

    @RequestMapping("/state")
    public String getState(){
        return "200";
    }

    @RequestMapping("/addToCart")
    public String addToCart(){
        String res = "goods added to cart.<br/>";

//        String str = getStr("order-service");
          String str="8006";

          String url = "http://order-service-8006:8006/placeOrder"; //gjw  ORDER-SERVICE

        String odRes = restTemplate.getForObject(url, String.class);

        Random random = new Random();
        int sleepSeconds = 600+random.nextInt(80);

        try {
            Thread.sleep(sleepSeconds); // 将秒转换为毫秒
            logger.info("Sleeping for {} seconds", sleepSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重新中断当前线程
            logger.error("Thread was interrupted", e);
            return "Error: Thread was interrupted.<br/>";
        }
        return res+odRes;
    }



//    @Value("${server.port}")
//    private int serverPort;
//    /**
//     * 对服务进行启动
//     * @param serviceName
//     * @return
//     */
//    public String getStr(String serviceName){
//
////        String url = "http://192.168.100.26:6789/startService?serviceName={serviceName}&servicePort={servicePort}";
//
////        String url = "http://10.34.57.165:6789/startService?serviceName={serviceName}&servicePort={servicePort}";
//        String url = "http://localhost:6789/startService?serviceName={serviceName}&servicePort={servicePort}"; //gjw
//
//        HashMap<String, String> map = new HashMap<>();
//        System.out.println("serverPort");
//        map.put("serviceName", serviceName);
//        map.put("servicePort", String.valueOf(serverPort));
//        return restTemplate.getForObject(url, String.class, map);
//    }
}
