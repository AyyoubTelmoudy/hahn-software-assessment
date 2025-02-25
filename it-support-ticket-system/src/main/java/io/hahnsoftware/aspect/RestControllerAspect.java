package io.hahnsoftware.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RestControllerAspect {
    @Pointcut("within(io.hahnsoftware.controller..*)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void logMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String functionName = signature.getName();

        log.info("------------------------------------------------------");
        log.info("ENTRY POINT : {}", functionName);
        log.info("------------------------------------------------------");
    }
}