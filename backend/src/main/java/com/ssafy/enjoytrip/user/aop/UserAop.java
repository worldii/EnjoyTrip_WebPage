package com.ssafy.enjoytrip.user.aop;

import com.ssafy.enjoytrip.user.UserEncoder;
import com.ssafy.enjoytrip.user.model.dto.User;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class UserAop {

    @Before("execution(* com.ssafy.enjoytrip.user.service.*.*(com.ssafy.enjoytrip.user.model.dto.User))")
    public void encodePassword(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        User user = (User) args[0];
        log.info("Encode Password Raw User - {}", user);

        user.setPassword(UserEncoder.hashPassword(user.getPassword()));
        log.info("Encode Password Hashed User- {}", user);
    }

    @Before("execution(* com.ssafy.enjoytrip.user.service.*.*(..))")
    public void printService(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();

        log.info("UserService - {} : Args - {}", signature.getName(), Arrays.toString(args));
    }

    @AfterReturning(value = "execution(* com.ssafy.enjoytrip.user.service.*.*(..))", returning = "result")
    public void printServiceResult(JoinPoint joinPoint, Object result) {
        log.info("UserServiceResult - {} : Result - {}", joinPoint.getSignature().getName(),
            result);
    }
}
