package com.my.example.atm.config;

import com.my.example.atm.dao.entity.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.my.example.atm.service.impl.WithdrawServiceImpl.withdraw(..)) && args(userAccount, withdrawnAmount)")
    public void logBefore(JoinPoint joinPoint, Account userAccount, long withdrawnAmount){
        log.info("Account " + userAccount.getAccountNumber() +
                " is doing withdraw with amount of " + withdrawnAmount);
    }

    @Before("execution(* com.my.example.atm.service.impl.TransferServiceImpl.transfer(..)) " +
            "&& args(userAccount, destinationAccount, transferAmount,..)")
    public void logBefore(JoinPoint joinPoint, Account userAccount,
                          String destinationAccount, String transferAmount){
        log.info("Account " + userAccount.getAccountNumber() +
                " is doing transfer with amount of " + transferAmount + " to " + destinationAccount);
    }

    /*@Pointcut("within(com.my.example.atm.service.impl.TransferServiceImpl) || " +
            "within(com.my.example.atm.service.impl.WithdrawServiceImpl)")
    public void transactionServiceClassPointcut() {}*/

    /*@Around("transactionServiceClassPointcut()")
    public Object logTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        *//*System.out.println("Method args names:");
        Arrays.stream(methodSignature.getParameterNames()).forEach(s -> System.out.println("arg name: " + s));
        System.out.println("Method args types:");
        Arrays.stream(methodSignature.getParameterTypes()).forEach(s -> System.out.println("arg type: " + s));
        System.out.println("Method args values:");
        Arrays.stream(joinPoint.getArgs()).forEach(o -> System.out.println("arg value: " + o.toString()));*//*
        final StopWatch stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        log.info("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");
        return result;
    }*/
}
