package Demo.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(0)
public class Demo2Aspect {
    @Pointcut("@annotation(Demo.customAnnotation.PermissionAnnotation)")
    private void PermissionCheck(){}
    //验证输入的用户名
    @Around("PermissionCheck()")
    public Object permissionCheck(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("==========第二个切面=========");
        Object[] args = pjp.getArgs();
        Long id = ((JSONObject) args[0]).getLong("id");
        String name = ((JSONObject) args[0]).getString("name");
        System.out.println("--->"+id);
        System.out.println("--->"+name);
        if(!name.equals("hanf")){
            System.out.println("{\"message\":\"not admin\",\"code\":402}");
            return JSONObject.parseObject("{\"message\":\"not admin\",\"code\":402}");
        }
        return pjp.proceed();
    }
}
