package com.javatest.aspect;

import com.alibaba.fastjson.JSON;
import com.javatest.po.ExceptionLog;
import com.javatest.po.OperationLog;
import com.javatest.service.ExceptionLogService;
import com.javatest.service.OperationLogService;
import com.javatest.util.IpAdressUtil;
import com.javatest.util.annotation.OperLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect     // 指定当前类为切面类
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

    // TODO 环绕通知改造尚未完成
    //正常的请求日志是在返回时记录所有数据的
    @Around(value = "operLogPt()")
    public void addOperLog(ProceedingJoinPoint joinPoint) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            // 获取通知前的时间
            Date startTime = new Date();

            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();

            // 获取请求的类路径
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;

            // TODO 以下仍待改造
            // 获取POST请求参数
//            Map<String,String> paramMap = new HashMap<>();
//            Map<String, String[]> parameterMap = request.getParameterMap();
//            for (String key : parameterMap.keySet()) {
//                paramMap.put(key,parameterMap.get(key)[0]);
//            }
//            String param = JSON.toJSONString(paramMap);
//
//            // 获取注解上的信息
//            OperLog annotation = method.getAnnotation(OperLog.class);
//            if (annotation != null) {
//                String type = annotation.type();
//                String module = annotation.module();
//                String requestDes = annotation.requestDes();
//            }
//
//
//            // 完善信息,id为int类型自增。可改用String传uuid
//            OperationLog operationLog = new OperationLog();
//            // 从session中获取，本地测试写死
////            operationLog.setUserId((String) request.getSession().getAttribute("userId"));
////            operationLog.setUserName((String) request.getSession().getAttribute("userName"));
//            operationLog.setUserId("1");
//            operationLog.setUserName("azure");
//            operationLog.setIp(IpAdressUtil.getIpAddr(request));
//            operationLog.setUrl(request.getRequestURI());
//            operationLog.setMethodName(methodName);
//            operationLog.setRequestParam(param);
//            operationLog.setReturnData(JSON.toJSONString(returnData));
////            operationLog.setStartTime();
////            operationLog.setFinishTime();
//            operationLog.setReturnTime(new Date());
//
//            operationLogService.insertSelective(operationLog);
        } catch (Exception e) {
            logger.error("插入日志失败",e);
        }
    }

    // TODO 等待并入到上面的环绕通知中
//    @AfterThrowing(pointcut = "excepLogPt()",throwing = "e")
    public void addExcepLog(JoinPoint joinPoint, Throwable e){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            ExceptionLog exceptionLog = new ExceptionLog();

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName;

            // 获取请求参数
            Object[] args = joinPoint.getArgs();
            String param = JSON.toJSONString(args);

            // 获取异常信息
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage()).append(";\n");
            for (StackTraceElement element : e.getStackTrace()) {
                sb.append(element).append("\n");
            }

            //完善数据，id为int类型自增
//        exceptionLog.setId();
            // 同上
            exceptionLog.setUserId("1");
            exceptionLog.setUserName("azure");
            exceptionLog.setExceptionName(e.getClass().getName());
            exceptionLog.setExceptionMsg(sb.toString());
            exceptionLog.setMethodName(methodName);
            exceptionLog.setIp(IpAdressUtil.getIpAddr(request));
            exceptionLog.setUrl(request.getRequestURI());
            exceptionLog.setRequestParam(param);
            exceptionLog.setCreateTime(new Date());

            exceptionLogService.insertSelective(exceptionLog);
        } catch (Exception e1) {
            logger.error("插入异常日志失败",e1);
        }
    }

}
