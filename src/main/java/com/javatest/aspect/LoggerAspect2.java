package com.javatest.aspect;

import com.javatest.domain.ExceptionLog;
import com.javatest.domain.OperationLog;
import com.javatest.service.ExceptionLogService;
import com.javatest.service.OperationLogService;
import com.javatest.util.IpAdressUtil;
import com.javatest.util.JacksonUtil;
import com.javatest.util.annotation.OperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author azure
 * 利用aop切面和环绕通知实现日志记录
 *   **注意：如果使用异常统一处理，则不能使用环绕通知，因为异常会首先被切面类捕捉，处理后并没有继续向外抛出，因而不会触发异常统一处理！！**
 */
//@Aspect     // 指定当前类为切面类
@Component  // 创建切面类对象
public class LoggerAspect2 {

    private final Logger logger = LoggerFactory.getLogger(LoggerAspect2.class);
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private ExceptionLogService exceptionLogService;

    // 定义请求日志切入点表达式，扫描被@OperLog注解的方法
    @Pointcut("execution(* com.javatest.controller.*.*(..)) || @annotation(com.javatest.util.annotation.OperLog)")
    public void operLogPt() {
    }

    //正常的请求日志是在返回时记录所有数据的
    @Around(value = "operLogPt()")
    public Object addOperLog(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        OperationLog operationLog = new OperationLog();
        Object object = null;
        try {

            /*【环绕通知】 环绕前*/

            // 获取通知前的时间
            operationLog.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
            // 获取被织入增强的方法签名对象
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取请求类路径+方法名
            String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
            operationLog.setMethodName(methodName);

            // 获取注解上的信息
            OperLog annotation = signature.getMethod().getAnnotation(OperLog.class);
            if (annotation != null) {
                operationLog.setType(annotation.type());
                operationLog.setModule(annotation.module());
                operationLog.setRequestDes(annotation.requestDes());
            }

            // 获取POST请求参数
            Map<String, String> paramMap = new HashMap<>();
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (String key : parameterMap.keySet()) {
                paramMap.put(key, parameterMap.get(key)[0]);
            }
            String param = JacksonUtil.obj2json(paramMap);
            operationLog.setRequestParam(param);

            // 执行方法
            try {
                object = joinPoint.proceed();

                operationLog.setFinishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date())); // 方法执行完时间

                /*【环绕通知】 环绕后*/
                try {
                    // 完善信息,id为int类型自增。可改用String传uuid
                    operationLog.setUserId((String) request.getSession().getAttribute("userId"));
                    operationLog.setUserName((String) request.getSession().getAttribute("userName"));
                    operationLog.setIp(IpAdressUtil.getIpAddr(request));
                    operationLog.setUrl(request.getRequestURI());
                    operationLog.setMethodName(methodName);
                    operationLog.setRequestParam(param);
                    operationLog.setReturnData(JacksonUtil.obj2json(object));
                    operationLog.setReturnTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));

                    operationLogService.insertSelective(operationLog);
                } catch (Exception e) {
                    logger.error("插入操作日志失败", e);
                }

                // 注意：环绕通知必须返回方法执行的结果
            } catch (Throwable e) {
                /*【环绕通知】 环绕异常*/
                e.printStackTrace();

                // 插入异常日志
                ExceptionLog exceptionLog = new ExceptionLog();
                StringBuilder sb = new StringBuilder();
                sb.append(e.getMessage()).append(";\n");
                for (StackTraceElement element : e.getStackTrace()) {
                    sb.append(element).append("\n");
                }
                try {
                    exceptionLog.setUserId((String) request.getSession().getAttribute("userId"));
                    exceptionLog.setUserName((String) request.getSession().getAttribute("userName"));
                    exceptionLog.setExceptionName(e.getClass().getName());
                    exceptionLog.setExceptionMsg(sb.toString());
                    exceptionLog.setMethodName(methodName);
                    exceptionLog.setIp(IpAdressUtil.getIpAddr(request));
                    exceptionLog.setUrl(request.getRequestURI());
                    exceptionLog.setRequestParam(param);
                    exceptionLog.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));

                    exceptionLogService.insertSelective(exceptionLog);
                } catch (Exception e1) {
                    logger.error("插入异常日志失败", e1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

}
