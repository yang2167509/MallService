//package com.ms.controller;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
////        261050699
//public class getData {
//    public static void main(String[] args)  {
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                String container_name = String.valueOf(8008);
//                System.out.println(container_name);
//
//                //节点数据
//                String node_CPU_Utilization = "(1-(sum(increase(node_cpu_seconds_total%7Bmode=%22idle%22%7D%5B1m%5D))by(instance))%20/%20(sum(increase(node_cpu_seconds_total%5B1m%5D))by(instance)))%20*100";
//                String node_Memory_Utilization = "%281-%20%28node_memory_Buffers_bytes%20%2B%20node_memory_Cached_bytes%20%2B%20node_memory_MemFree_bytes%29%20%2F%20node_memory_MemTotal_bytes%29%20%2a%20100";
//                String node_Disk_Utilization= "%281%20-%20node_filesystem_avail_bytes%7Bfstype%3D~%22ext4%7Cxfs%22%7D%20%2F%20node_filesystem_size_bytes%7Bfstype%3D~%22ext4%7Cxfs%22%7D%29%20%2a%20100";
//                String node_Net_Rev = "sum%20%28node_network_receive_bytes_total%29%20by%20%28instance%29";
//                String node_Net_Sent = "sum(node_network_transmit_bytes_total)%20by%20(instance)";
//
//                //容器数据
//                String container_CPU_Utilization = "sum(rate(container_cpu_usage_seconds_total%7Bname!='cadvisor',image!=%22%22,name=%22"+container_name+"%22%7D%5B30s%5D))%20by%20(name,instance)%20*%20100";
//                String container_Memory_Usage = "container_memory_usage_bytes%7Bname=%228008%22%7D/1024/1024";
////                String container_Memory_Usage ="container_memory_usage_bytes/1024/1024";
//
//                //将字节（bytes）转换为兆字节（MB）
//                String container_Fs_Read = "sum(rate(container_fs_read_seconds_total%7Bname=%228008%22%7D%5B30s%5D))%20by%20(instance,name)";
//                String container_Fs_Write = "sum(rate(container_fs_write_seconds_total%7Bname=%228008%22%7D%5B30s%5D))%20by%20(instance,name)";
//                String container_Net_Rev = "sum(rate(container_network_receive_bytes_total%7Bname=%228008%22%7D%5B30s%5D))%20by%20(instance,name)";
//                String container_Net_Sent = "sum(rate(container_network_transmit_bytes_total%7Bname=%228008%22%7D%5B30s%5D))%20by%20(instance,name)" ;
//
//                try {
////                    httpEntity(Node_Memory_Utilization);
////                    httpEntity(CPU_Utilization);
//                    httpEntity(container_Memory_Usage);
//
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(task,0,1000);
//    }
//    /** ---------------http-client------------ */
//    public static HttpEntity httpEntity(String metric) throws IOException {
//        String baseurl = "http://10.69.37.140:9090/api/v1/query?query=";
//        String url = baseurl+metric;
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpGet httpGet= new HttpGet(url);
//
//        CloseableHttpResponse response = null;
//        HttpEntity responseEntity = null;
//        try {
//            response = client.execute(httpGet);         //执行请求
//            org.apache.http.HttpEntity apacheEntity = response.getEntity();      //获取请求信息
//            String content = EntityUtils.toString(apacheEntity);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            responseEntity = new HttpEntity<>(content, headers);
////            System.out.println(responseEntity);
//
//            String responseEntityAsString = responseEntity.getBody().toString();
////            System.out.println(responseEntityAsString);
//
//            JSONObject datas = JSONObject.parseObject(responseEntityAsString);//转换成JSON格式
//            JSONObject data = JSONObject.parseObject(datas.get("data").toString());//获取data中的数据
////            JSONArray result = JSONObject.parseArray(data.get("result").toString());
//
//            // ...
//            JSONArray result = JSONObject.parseArray(data.get("result").toString());
//            System.out.println(result);
//            System.out.println(result.getJSONObject(0));
//            for (int i = 0; i < result.size(); i++) {
//
//                JSONObject jsonObject = result.getJSONObject(i);
//                JSONObject metric1 = jsonObject.getJSONObject("metric");
//                String instance = metric1.getString("instance");
//                String job = metric1.getString("job");
//                String name = metric1.getString("name");
//
//
//                //过滤
//                if ("10.69.37.140:9100".equals(instance) ) {//&& "node-exporter".equals(job)
//                    JSONArray value = jsonObject.getJSONArray("value");
//                    if (value.size() > 1) {
//                        String secondValue = value.getString(1);
//                        System.out.println(secondValue);
//                        break;
//                    }
//                }
//
//
//                if ("10.69.37.141:8080".equals(instance) ) {//&& "node-exporter".equals(job)
//                    JSONArray value = jsonObject.getJSONArray("value");
//                    if (value.size() > 1) {
//                        String secondValue = value.getString(1);
//                        System.out.println(secondValue);
//                        break;
//                    }
//                }
//
//                //ip地址为10.69.37.141:8080, 容器name为8080
//                if ("8008".equals(name) ) {//&& "node-exporter".equals(job)
//                    JSONArray value = jsonObject.getJSONArray("value");
//                    if (value.size() > 1) {
//                        String secondValue = value.getString(1);
//                        System.out.println(secondValue);
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
//        return responseEntity;
//    }
//}
