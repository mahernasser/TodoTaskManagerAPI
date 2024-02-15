package com.maher.aspect;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PrintAspect {


    @After("execution(* com.maher.services.*.*(..))")
    public void printHello(){
        System.out.println("Hello aspect");
    }
}
