package com.ms.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
@EnableHystrix
@RestController
public class orderController {

    private static final Logger logger = LoggerFactory.getLogger(orderController.class);
    @Autowired
    private RestTemplate restTemplate;

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
    @RequestMapping("/placeOrder")
    public String placeOrder(){
        String res = "The goods have been ordered.Please pay!<br/>";

        Random random = new Random();
        int sleepSeconds =  600+random.nextInt(200);

        // 生成一个随机的毫秒数，范围从 0 到 999（包含）
        long randomMilliseconds = ThreadLocalRandom.current().nextLong(200);
        sleepSeconds = (int) randomMilliseconds + sleepSeconds;

        try {
            Thread.sleep(sleepSeconds); // 将秒转换为毫秒
            logger.info("Sleeping for {} seconds", sleepSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重新中断当前线程
            logger.error("Thread was interrupted", e);
            return "Error: Thread was interrupted.<br/>";
        }

//        String str = getStr("pay-service");
        String str = "8007";

//        String url = "http://192.168.100.26:"+str+"/dopay";
//        String url = "http://localhost:"+str+"/dopay";
        String url = "http://pay-service-8007:8007/dopay";

        String orderRes = restTemplate.getForObject(url, String.class);
        return res+orderRes;
    }

    public String createOrderFallbackMethod4Timeout() throws Exception {
        System.err.println("-------Timeout degradation strategy execution------------");//超时降级策略执行
        return "order service is busy, please try again later.";
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
