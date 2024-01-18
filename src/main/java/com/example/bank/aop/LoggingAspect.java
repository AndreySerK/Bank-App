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

    @Before("execution(* com.example.bank.service.AccountService.*(..))")
    public void logBeforeAllMethods_AccountService(JoinPoint joinPoint) {
        LOGGER.info("AccountService method called: {}", joinPoint.getSignature().getName());
    }

    @Before("execution(* com.example.bank.service.AccountService.*(..))")
    public void logBeforeAllMethods_AgreementService(JoinPoint joinPoint) {
        LOGGER.debug("AgreementService method called: {}", joinPoint.getSignature().getName());
    }

    @Before("execution(* com.example.bank.service.AccountService.*(..))")
    public void logBeforeAllMethods_ProductService(JoinPoint joinPoint) {
        LOGGER.debug("ProductService method called: {}", joinPoint.getSignature().getName());
    }

    @Before("execution(* com.example.bank.service.AccountService.*(..))")
    public void logBeforeAllMethods_ManagerService(JoinPoint joinPoint) {
        LOGGER.debug("ManagerService method called: {}", joinPoint.getSignature().getName());
    }

    @Before("execution(* com.example.bank.service.AccountService.*(..))")
    public void logBeforeAllMethods_ClientService(JoinPoint joinPoint) {
        LOGGER.debug("ClientService method called: {}", joinPoint.getSignature().getName());
    }

    @Before("execution(* com.example.bank.service.AccountService.*(..))")
    public void logBeforeAllMethods_TransactionService(JoinPoint joinPoint) {
        LOGGER.info("TransactionService method called: {}", joinPoint.getSignature().getName());
    }
}
