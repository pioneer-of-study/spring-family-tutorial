package Demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/*
    把一个类变成切面类，需要两步，
    ① 在类上使用 @Component 注解 把切面类加入到IOC容器中
    ② 在类上使用 @Aspect 注解 使之成为切面类
 */

@Component
@Aspect
public class DemoAspect {
    private Logger logger = LoggerFactory.getLogger(DemoAspect.class);
    //前置通知
    @Before("execution(public * Demo.controller.DemoController.*(..))")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }
    // 在执行前后执行:
    @Around("execution(public * Demo.controller.DemoController.*(..))")
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println();
        logger.info("[Around] start " + pjp.getSignature());
        Object retVal = pjp.proceed();
        logger.info("[Around] done " + pjp.getSignature());
        return retVal;
    }
}
