package com.ecloud.framework.aspect.impl;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ecloud.framework.aspect.BizAspect;

@Aspect
@Component("bizAspect")
public class BizAspectImpl implements BizAspect{
	
	@Pointcut("execution(public * com.yihui.invms..service..*.*(..)) || execution(public * com.ecloud.frame.platform.service.impl.BaseServiceImpl.*(..))")
	public void aspectBiz(){
		
	}
	
	@Around("aspectBiz()")
	public Object process(ProceedingJoinPoint point) throws Throwable {
		System.out.println("@Around：执行目标方法之前...");
		//访问目标方法的参数：
		Object[] args = point.getArgs();
		if (args != null && args.length > 0 && args[0].getClass() == String.class) {
			//args[0] = "改变后的参数1";
		}
		//用改变后的参数执行目标方法
		Object returnValue = point.proceed(args);
		System.out.println("@Around：执行目标方法之后...");
		System.out.println("@Around：被织入的目标对象为：" + point.getTarget());
		return returnValue ;
	}

	@Before("aspectBiz()")
	public void beforeInsert(JoinPoint jp) {
		String methodName = jp.getSignature().getName();
		System.out.println("@Before：模拟权限检查...");
		System.out.println("==================="+methodName+"=====================");
	}
	
     
    @After("aspectBiz()")
    public void releaseResource(JoinPoint point) {
        System.out.println("@After：模拟释放资源...");
        System.out.println("@After：目标方法为：" +  point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        System.out.println("@After：参数为：" + Arrays.toString(point.getArgs()));
        System.out.println("@After：被织入的目标对象为：" + point.getTarget());
    }
    
    @AfterReturning(pointcut="aspectBiz()",  returning="returnValue")
    public void log(JoinPoint point, Object returnValue) {
    	System.out.println("@AfterReturning：模拟日志记录功能...");
    	System.out.println("@AfterReturning：目标方法为：" + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
    	System.out.println("@AfterReturning：参数为：" + Arrays.toString(point.getArgs()));
    	System.out.println("@AfterReturning：返回值为：" + returnValue);
    	System.out.println("@AfterReturning：被织入的目标对象为：" + point.getTarget());
    	
    }
    
}
