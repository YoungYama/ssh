package com.yzz.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yzz.entity.SysUser;
import com.yzz.util.ConstantUtil;

@Aspect
@Component
public class LogAspect {

	private HttpServletRequest request;
	private HttpSession session;
	private SysUser sysUser;

	@Before("within(com.yzz.ctrl..*) && @annotation(logInfo)")
	public void before(final LogInfo logInfo) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		session = request.getSession();
	}

	// 此注解是后置增强，方法执行成功后会执行
	@AfterReturning("within(com.yzz.ctrl..*) && @annotation(logInfo)")
	public void OperationSuccessLog(final JoinPoint joinPoint, final LogInfo logInfo) {
		sysUser = ((SysUser) session.getAttribute(ConstantUtil.LOGINING_SYSUSER));

		String modelTypeName = logInfo.modelTypeName();// 要执行的操作的模块名称比如：人员管理模块
		String operationTypeName = logInfo.operationTypeName();// 要执行的操作类型名称比如：添加
		String operationContent = logInfo.operationContent();// 要执行的具体操作比如：XX添加了用户

		String targetName = joinPoint.getTarget().getClass().getName();// 获取目标类名
		String methodName = joinPoint.getSignature().getName(); // 获取目标方法名
		Object[] args = joinPoint.getArgs();// 获取目标方法参数
		System.out.println(" modelTypeName:" + modelTypeName + " operationTypeName:" + operationTypeName
				+ " operationContent:" + operationContent + " targetName:" + targetName + " methodName:" + methodName
				+ " args:" + args);
	}

	// 该方法体为异常通知，当目标方法出现异常时，执行该方法体
	@AfterThrowing(pointcut = "within(com.yzz.ctrl..*) && @annotation(logInfo)", throwing = "e")
	public void OperationFailureLog(final JoinPoint joinPoint, final LogInfo logInfo, Exception e) {
		sysUser = ((SysUser) session.getAttribute(ConstantUtil.LOGINING_SYSUSER));

		String modelTypeName = logInfo.modelTypeName();// 要执行的操作的模块名称比如：人员管理模块
		String operationTypeName = logInfo.operationTypeName();// 要执行的操作类型名称比如：添加
		String operationContent = logInfo.operationContent();// 要执行的具体操作比如：XX添加了用户

		String targetName = joinPoint.getTarget().getClass().getName();// 获取目标类名
		String methodName = joinPoint.getSignature().getName(); // 获取目标方法名
		Object[] args = joinPoint.getArgs();// 获取目标方法参数
		System.out.println(" modelTypeName:" + modelTypeName + " operationTypeName:" + operationTypeName
				+ " operationContent:" + operationContent + " targetName:" + targetName + " methodName:" + methodName
				+ " args:" + args);
		System.out.println("-------------" + e.getMessage());
	}

}
