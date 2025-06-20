//package com.ms.controller;
//
//import brave.Span;
//import brave.Tracer;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//@Aspect
//@Component
//public class returnValue {
//
//    @Autowired
//    private Tracer tracer;
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Value("${spring.application.name}")
//    private String moduleName;
//
//    //    @Value("${service.port}")
////    private int servicePort;
//    private static final Logger logger = LoggerFactory.getLogger(com.ms.controller.returnValue.class);
//
//
//    @Pointcut("execution(* com.ms.controller.memberController.* (..))")
////    execution: 表示匹配方法执行的切点。
////            *: 匹配任意返回类型的方法。
////    com.ms.controller: 匹配包名为 "com.ms.controller" 的类。
////            ..: 表示匹配任意子包。
////            *: 匹配任意类名。
////            *(..): 匹配任意方法名和任意参数。
//    public void serviceMethods() {}
//
//    //    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
//    @Around("serviceMethods()")
//
//    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
//
//
//        StringBuilder logMessageBuilder = new StringBuilder(); // 创建StringBuilder对象用于构建日志字符串
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
////        String format = sdf.format(System.currentTimeMillis());
//        long startTime = System.currentTimeMillis();
//        String start=sdf.format(startTime);
//
//        Object result = joinPoint.proceed();
//
//        long endTime = System.currentTimeMillis();
//        String end=sdf.format(endTime);
//
//        long elapsedTimeMillis = endTime - startTime;
//        logger.info("开始时间={}，结束时间={}，耗时={}ms",start,end,elapsedTimeMillis);
//
//        String classNameLog = joinPoint.getTarget().getClass().getSimpleName();
//        Logger classLogger = LoggerFactory.getLogger(classNameLog);
//
//        // 获取返回结果
//        String returnValue = result.toString();
//
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String host = request.getServerName();
//        String path = request.getRequestURI().substring(request.getContextPath().length());
//        int port = request.getServerPort();
//        // 获取当前用户
//        //String username = SecurityUtils.getSubject().getPrincipal().toString();
//
//        //节点，容器指标数据
//        //节点数据
//        String node_CPU_Utilization = "(1-(sum(increase(node_cpu_seconds_total%7Bmode=%22idle%22%7D%5B1m%5D))by(instance))%20/%20(sum(increase(node_cpu_seconds_total%5B1m%5D))by(instance)))%20*100";
//        String node_Memory_Utilization = "%281-%20%28node_memory_Buffers_bytes%20%2B%20node_memory_Cached_bytes%20%2B%20node_memory_MemFree_bytes%29%20%2F%20node_memory_MemTotal_bytes%29%20%2a%20100";
//        String node_Disk_Utilization= "%281%20-%20node_filesystem_avail_bytes%7Bfstype%3D~%22ext4%7Cxfs%22%7D%20%2F%20node_filesystem_size_bytes%7Bfstype%3D~%22ext4%7Cxfs%22%7D%29%20%2a%20100";
//        String node_Net_Rev = "sum%20%28node_network_receive_bytes_total%29%20by%20%28instance%29";
//        String node_Net_Sent = "sum(node_network_transmit_bytes_total)%20by%20(instance)";
//
//        String container_name = String.valueOf(port);
//        //容器数据
//        String container_CPU_Utilization = "sum(rate(container_cpu_usage_seconds_total%7Bname!='cadvisor',image!=%22%22,name=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(name,instance)%20*%20100";
//        String container_Memory_Usage = "container_memory_usage_bytes%7Bname=%22"+container_name+"%22%7D/1024/1024";
//        //将字节（bytes）转换为兆字节（MB）
//        String container_Fs_Read = "sum(rate(container_fs_read_seconds_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)";
//        String container_Fs_Write = "sum(rate(container_fs_write_seconds_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)";
//        String container_Net_Rev = "sum(rate(container_network_receive_bytes_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)";
//        String container_Net_Sent = "sum(rate(container_network_transmit_bytes_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)" ;
////        String[] metrics_names = {
////                "node_CPU_Utilization",
////                "node_Memory_Utilization",
////                "node_Disk_Utilization",
////                "node_Net_Rev",
////                "node_Net_Sent",
////                "container_CPU_Utilization",
////                "container_Memory_Usage",
////                "container_Fs_Read",
////                "container_Fs_Write",
////                "container_Net_Rev",
////                "container_Net_Sent"
////        };
//
//        String[] metrics = {
//                "(1-(sum(increase(node_cpu_seconds_total%7Bmode=%22idle%22%7D%5B1m%5D))by(instance))%20/%20(sum(increase(node_cpu_seconds_total%5B1m%5D))by(instance)))%20*100",
//                "%281-%20%28node_memory_Buffers_bytes%20%2B%20node_memory_Cached_bytes%20%2B%20node_memory_MemFree_bytes%29%20%2F%20node_memory_MemTotal_bytes%29%20%2a%20100",
//                "%281%20-%20node_filesystem_avail_bytes%7Bfstype%3D~%22ext4%7Cxfs%22%7D%20%2F%20node_filesystem_size_bytes%7Bfstype%3D~%22ext4%7Cxfs%22%7D%29%20%2a%20100",
//                "sum%20%28node_network_receive_bytes_total%29%20by%20%28instance%29",
//                "sum(node_network_transmit_bytes_total)%20by%20(instance)",
//                "sum(rate(container_cpu_usage_seconds_total%7Bname!='cadvisor',image!=%22%22,name=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(name,instance)%20*%20100",
//                "container_memory_usage_bytes%7Bname=%22"+container_name+"%22%7D/1024/1024",
//                "sum(rate(container_fs_read_seconds_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)",
//                "sum(rate(container_fs_write_seconds_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)",
//                "sum(rate(container_network_receive_bytes_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)",
//                "sum(rate(container_network_transmit_bytes_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)"
//        };
//
//        Map<String, String> metrics_map = new HashMap<>();
//        metrics_map.put("node_CPU_Utilization", "(1-(sum(increase(node_cpu_seconds_total%7Bmode=%22idle%22%7D%5B1m%5D))by(instance))%20/%20(sum(increase(node_cpu_seconds_total%5B1m%5D))by(instance)))%20*100");
//        metrics_map.put("node_Memory_Utilization", "%281-%20%28node_memory_Buffers_bytes%20%2B%20node_memory_Cached_bytes%20%2B%20node_memory_MemFree_bytes%29%20%2F%20node_memory_MemTotal_bytes%29%20%2a%20100");
//        metrics_map.put("node_Disk_Utilization", "%281%20-%20node_filesystem_avail_bytes%7Bfstype%3D~%22ext4%7Cxfs%22%7D%20%2F%20node_filesystem_size_bytes%7Bfstype%3D~%22ext4%7Cxfs%22%7D%29%20%2a%20100");
//        metrics_map.put("node_Net_Rev", "sum%20%28node_network_receive_bytes_total%29%20by%20%28instance%29");
//        metrics_map.put("node_Net_Sent", "sum(node_network_transmit_bytes_total)%20by%20(instance)");
//        metrics_map.put("container_CPU_Utilization", "sum(rate(container_cpu_usage_seconds_total%7Bname!='cadvisor',image!=%22%22,name=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(name,instance)%20*%20100");
//        metrics_map.put("container_Memory_Usage", "container_memory_usage_bytes%7Bname=%22"+container_name+"%22%7D/1024/1024");
//        metrics_map.put("container_Fs_Read", "sum(rate(container_fs_read_seconds_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)");
//        metrics_map.put("container_Fs_Write", "sum(rate(container_fs_write_seconds_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)");
//        metrics_map.put("container_Net_Rev", "sum(rate(container_network_receive_bytes_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)");
//        metrics_map.put("container_Net_Sent", "sum(rate(container_network_transmit_bytes_total%7Bname=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(instance,name)");
//
//
//
//
//        System.out.println("服务名称：" + moduleName);
//        System.out.println("服务主机名称："+host);
//        System.out.println("服务端口："+port);
//
//
//        System.out.println("服务路径：" + path);
//        String url=host+":"+port+path;
//
//        System.out.println("url为："+url);
//        System.out.println("返回结果：" + returnValue);
//
//        // 获取类名和方法名
//        String className = joinPoint.getSignature().getDeclaringTypeName();
//        String methodName = joinPoint.getSignature().getName();
//
//        Object[] args = joinPoint.getArgs();
//
//        // 遍历参数数组，打印每个参数的值及其类型
//        for (int i = 0; i < args.length; i++) {
//            Object arg = args[i];
//            System.out.println("Parameter " + i + ": Value=" + arg );
//
//            logMessageBuilder.append("Parameter ").append(i).append(": Value=").append(arg).append("||");
//        }
//
//        // 将构建好的字符串转换为日志消息
//        String logMessage = logMessageBuilder.toString();
//
//        // 将日志消息打印到控制台或写入日志文件
//        System.out.println(logMessage);
//
//
//
//        String inserted_by=this.getClass().getName();
//        System.out.println("负责入库方法：" + this.getClass().getName());
//
//
//        Span currentSpan = tracer.currentSpan();
//        logger.info("Trace ID: {}", currentSpan.context().traceId());
//        logger.info("Span ID: {}", currentSpan.context().spanId());
//        logger.info("Parent Span ID: {}", currentSpan.context().parentId());
//
//        //metrics
//        Map<String, String> get_metrics_map = new HashMap<>();
//        Iterator<Map.Entry<String, String>> iterator = metrics_map.entrySet().iterator();
//
//        while (iterator.hasNext()) {
//            Map<String, String> map = new HashMap<>();
//            Map.Entry<String, String> entry = iterator.next();
//            String key = entry.getKey();
//            String value = entry.getValue();
//            map= httpEntity(key, value,host+":"+"9100", container_name);
//            // Do something with responseEntity...
//            get_metrics_map.putAll(map);
//            System.out.println(get_metrics_map);
//
//        }
//
//
//        jdbcTemplate.update("INSERT INTO return1 (\n" +
//                        "    service_name,\n" +
//                        "    className,\n" +
//                        "    methodName,\n" +
//                        "    args,\n" +
//                        "    host,\n" +
//                        "    port,\n" +
//                        "    path,\n" +
//                        "    url,\n" +
//                        "    return_value,\n" +
//                        "    start,\n" +
//                        "    end,\n" +
//                        "    elapsed,\n" +
//                        "    trace_id,\n" +
//                        "    span_id,\n" +
//                        "    parent_span_id,\n" +
//                        "    inserted_by,\n" +
//                        "    node_CPU_Utilization,\n" +
//                        "    node_Memory_Utilization,\n" +
//                        "    node_Disk_Utilization,\n" +
//                        "    node_Net_Rev,\n" +
//                        "    node_Net_Sent,\n" +
//                        "    container_CPU_Utilization,\n" +
//                        "    container_Memory_Usage,\n" +
//                        "    container_Fs_Read,\n" +
//                        "    container_Fs_Write,\n" +
//                        "    container_Net_Rev,\n" +
//                        "    container_Net_Sent\n" +
//                        ") VALUES (\n" +
//                        "    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?\n" +
//                        ")",
//                moduleName,className,methodName,logMessage,host,port,path,url,returnValue,start,end,elapsedTimeMillis,currentSpan.context().traceId(),
//                currentSpan.context().spanId(),currentSpan.context().parentId(),inserted_by,
//                get_metrics_map.get("node_CPU_Utilization"),get_metrics_map.get("node_Memory_Utilization"),get_metrics_map.get("node_Disk_Utilization"),
//                get_metrics_map.get("node_Net_Rev"),get_metrics_map.get("node_Net_Sent"),
//                get_metrics_map.get("container_CPU_Utilization"), get_metrics_map.get("container_Memory_Usage"),
//                get_metrics_map.get("container_Fs_Read"), get_metrics_map.get("container_Fs_Write"),
//                get_metrics_map.get("container_Net_Rev"),get_metrics_map.get("container_Net_Sent")
//
//        );
//
//
//        System.out.println();
//        System.out.println("数据库插入数据成功！");
////        jdbcTemplate.update("TRUNCATE TABLE " + "test02");
//        return returnValue;
//    }
//
//
//    public static Map<String, String> httpEntity(String metric_name, String metric, String host_name, String container_name) throws IOException {
//        String baseurl = "http://10.69.37.140:9090/api/v1/query?query=";
//        String url = baseurl+metric;
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpGet httpGet= new HttpGet(url);
//
//        CloseableHttpResponse response = null;
//        HttpEntity responseEntity = null;
//        Map<String, String> get_metrics_map = new HashMap<>();
//
//        try {
//            response = client.execute(httpGet);         //执行请求
//            org.apache.http.HttpEntity apacheEntity = response.getEntity();      //获取请求信息
//            String content = EntityUtils.toString(apacheEntity);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            responseEntity = new HttpEntity<>(content, headers);
//
//            String responseEntityAsString = responseEntity.getBody().toString();
//
//            JSONObject datas = JSONObject.parseObject(responseEntityAsString);//转换成JSON格式
//            JSONObject data = JSONObject.parseObject(datas.get("data").toString());//获取data中的数据
//
//            JSONArray result = JSONObject.parseArray(data.get("result").toString());
//
////            System.out.println(result);
////            System.out.println(result.getJSONObject(0));
//
//            for (int i = 0; i < result.size(); i++) {
//
//                JSONObject jsonObject = result.getJSONObject(i);
//                JSONObject metric1 = jsonObject.getJSONObject("metric");
//                String instance = metric1.getString("instance");
//                String job = metric1.getString("job");
//                String name = metric1.getString("name");
//
//                //过滤
//                if (host_name.equals(instance) ) {//&& "node-exporter".equals(job)
//                    JSONArray value = jsonObject.getJSONArray("value");
//                    if (value.size() > 1) {
//                        String secondValue = value.getString(1);
//                        System.out.println(metric_name+" = "+secondValue);
//                        get_metrics_map.put(metric_name,secondValue);
//
//                        break;
//                    }
//                }
//
//
//                //ip地址为10.69.37.141:8080, 容器name为8080
//                if (container_name.equals(name) ) {//&& "node-exporter".equals(job)
//                    JSONArray value = jsonObject.getJSONArray("value");
//                    if (value.size() > 1) {
//                        String secondValue = value.getString(1);
//                        System.out.println(metric_name+" = "+secondValue);
//                        get_metrics_map.put(metric_name,secondValue);
//
//                        break;
//                    }
//                }
//
//            }
//// ...
////            System.out.println(result);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        client.close();
////        return responseEntity;
//        return get_metrics_map;
//    }
//}
