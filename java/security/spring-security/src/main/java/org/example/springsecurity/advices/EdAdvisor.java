package org.example.springsecurity.advices;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class EdAdvisor {


    private final Log logger = LogFactory.getLog(getClass());

    @Around("execution(* org.example..*.*(..) )")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("************ before " + pjp.toString());
        Object object = pjp.proceed();
        logger.info("************ after " + pjp.toString());
        return object;
    }
}
