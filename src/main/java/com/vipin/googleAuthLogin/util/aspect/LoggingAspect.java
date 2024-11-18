package com.vipin.googleAuthLogin.util.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.vipin.googleAuthLogin.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Entering method: {}", joinPoint.getSignature().getName() + " " + new Date());
    }

    @After("execution(* com.vipin.googleAuthLogin.controller.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Ending method: {}", joinPoint.getSignature().getName() + " " + new Date());
    }

//    @Around("execution(* com.vipin.googleAuthLogin.controller.*.*(..))")
//    public void logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        proceedingJoinPoint.proceed();
//    }
}
