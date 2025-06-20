package com.ms.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
@EnableHystrix
@RestController
public class goodsController {
    private static final Logger logger = LoggerFactory.getLogger(goodsController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/checkGoodsDetail")
    public String checkGoodsDetail(){
        String res = "check Goods Detail.<br/>";
//        String str = getStr("cart-service");
        String str="8005";
//        String url = "http://192.168.100.26:"+str+"/addToCart";
//        String url = "http://localhost:"+str+"/addToCart";
        String url = "http://cart-service-8005:8005/addToCart";

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

        String cartRes = restTemplate.getForObject(url, String.class);
        return res+cartRes;
    }

    @PostMapping("/showGoods")
    @HystrixCommand(fallbackMethod = "fallbackCalculatePrice")
    public String showGoods(@RequestParam("goodsNum") Integer goodsNum){
        // 直接返回一个静态的商品信息字符串，忽略传入的goodsId
        Integer price = 100/goodsNum;
        return "Product Information: The product with ID : 'Sample Product' price : " + price + " units.";
    }
    public String fallbackCalculatePrice(Integer goodsNum,Throwable e) {
        // 备用逻辑，例如返回一个默认价格
        return "An internal service error occurred while processing the request for product.";
    }


    @RequestMapping("/state")
    public String getState(){
        return "200";
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
