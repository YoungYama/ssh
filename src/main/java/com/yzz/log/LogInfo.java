package com.yzz.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解类 LogInfo.java(获取Controller的日志描述)
 * 
 * @author 杨志钊
 * @date 2017-4-7 下午12:02:51
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogInfo {

	/** 要执行的操作的模块类型名称比如：人员管理模块 **/
	public String modelTypeName();

	/** 要执行的操作类型名称比如：添加 **/
	public String operationTypeName();

	/** 要执行的具体操作比如：XX添加了用户 **/
	public String operationContent();

}