//package com.ms.controller;
//
//import brave.Span;
//import brave.Tracer;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.text.SimpleDateFormat;
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
//    @Pointcut("execution(* com.ms.controller.cartController.* (..))") //C:\Users\gujiawei\Desktop\sw-ms5\goods-service-8004\src\main\java\com\ms\controller\goodsController.java
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
//        System.out.println("服务名称：" + moduleName);
//        System.out.println("服务主机名称："+host);
//        System.out.println("服务端口："+port);
//
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
//        jdbcTemplate.update("insert into return7(service_name,className,methodName,host,port,path,url,return_value," +
//                        "start,end,elapsed,trace_id,span_id,parent_span_id,inserted_by) " +
//                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
//                moduleName,className,methodName,host,port,path,url,returnValue,start,end,elapsedTimeMillis,currentSpan.context().traceId(),
//                currentSpan.context().spanId(),currentSpan.context().parentId(),inserted_by
//        );
//
//        System.out.println();
//        System.out.println("数据库插入数据成功！");
////        jdbcTemplate.update("TRUNCATE TABLE " + "test02");
//        return returnValue;
//    }
//
//}
//
