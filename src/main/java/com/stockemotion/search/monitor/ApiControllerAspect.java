package com.stockemotion.search.monitor;



import com.stockemotion.common.utils.JsonUtils;
import com.stockemotion.search.controller.BaseController;
import com.stockemotion.search.exception.NullParamException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Created by wode4 on 2016/10/18.
 */
/*
 * 定义切面执行的优先级，数字越低，优先级越高
 * 在切入点之前执行：按order值有小到大的顺序执行
 * 在切入点之后执行：按order值由大到小的顺序执行
 */
@Slf4j
@Aspect
@Component
@Order(5)
public class ApiControllerAspect
{
   
    private ThreadLocal<Long> time = new ThreadLocal<>();

    @Pointcut("execution(* com.stockemotion.search.controller..*(..))")
    public void controllerMethodPointcut() {}

    @Pointcut("execution(* com.stockemotion.search.controller..*(..))")
    public void controllerExceptionPointcut() {}


    @Before("controllerMethodPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        time.set(System.currentTimeMillis()); //记录执行开始时间

        ServletRequestAttributes attributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
       /* String ip = request.getRemoteAddr();
        if(ip.indexOf(":0") > -1) { //说明是本地ip
            ip = IpUtil.getLocalRealIp();
        }*/

       // log.info("IP : " + ip);
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        for (Object arg : joinPoint.getArgs()) {
            if(arg instanceof HttpServletRequest){
                log.info("token :"+ ((HttpServletRequest)arg).getHeader("Authorization"));
                continue;
            }
           log.info(JsonUtils.TO_JSON(arg));
        }

        //获取所有参数方法一：
        Enumeration<String> enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            log.info(paraName+": "+request.getParameter(paraName));
        }
    }

    @AfterReturning(value = "controllerMethodPointcut()", returning = "returnVal")
    public void doAfterReturning(JoinPoint joinPoint, Object returnVal){
        log.info(joinPoint.getSignature().getName() + ": return :" + JsonUtils.TO_JSON(returnVal));
        log.info("time-consuming:"+ (System.currentTimeMillis() - time.get()) / 1000);
        // 处理完请求，返回内容
        log.info("WebLogAspect.doAfterReturning()");
    }

    @Around("controllerExceptionPointcut()")
    public Object handle(ProceedingJoinPoint joinPoint){
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (NullParamException npe){
            BaseController.getNullParamResponse();
        }
        catch (Throwable npe){
            log.error("exception :", npe);
            result = BaseController.getErrorServerResponse();
        }
        return result;
    }
}
