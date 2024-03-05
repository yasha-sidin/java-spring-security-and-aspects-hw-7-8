package ru.overthantutor.javaspringaoptask1.aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Aspect
@Component
@Log
public class UserControlAspect {

    @Around("@annotation(TrackUserAction)")
    public Object checkUserActivity(ProceedingJoinPoint joinPoint) throws Throwable {
        String args = Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.log(Level.INFO, joinPoint + ", args=[" + args + "]");
        return joinPoint.proceed();
    }
}
