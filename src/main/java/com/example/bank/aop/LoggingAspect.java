package com.example.bank.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.example.bank.services.AccountService.*(..))")
    public void logBeforeAllMethods(JoinPoint joinPoint) {
        LOGGER.debug("Service methode called: {}", joinPoint.getSignature().getName());
    }
}
