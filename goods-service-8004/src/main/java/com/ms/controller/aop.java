package com.ms.controller;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class aop {

    private static final Logger logger = LoggerFactory.getLogger(aop.class);
    private final Tracer tracer = GlobalTracer.get(); // 获取全局 Tracer 实例

    @Pointcut("execution(* com.ms.controller.goodsController.* (..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Span span = tracer.buildSpan(joinPoint.getSignature().getName()).start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed(); // 继续执行原方法
            long endTime = System.currentTimeMillis();
            long elapsedTimeMillis = endTime - startTime;

            // 在 Span 中添加执行时间和其他相关信息
            span.setTag("execution.time", elapsedTimeMillis);
            span.setTag("class", joinPoint.getTarget().getClass().getSimpleName());
            span.setTag("method", joinPoint.getSignature().getName());
            if (result != null) {
                span.setTag("return", result.toString());
            }

            logger.info("Method executed in {} ms", elapsedTimeMillis);
            return result;
        } catch (Throwable ex) {
            Map<String, Object> errorAttributes = new HashMap<>();
            errorAttributes.put("event", "error");
            errorAttributes.put("error.object", ex);
            span.log(errorAttributes);
            throw ex;
        } finally {
            span.finish(); // 确保 Span 被完成
        }
    }
}