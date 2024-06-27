package com.biluo.aspect;

import com.biluo.annotation.InsertAutoFill;
import com.biluo.annotation.UpdateAutoFill;
import com.biluo.model.po.TUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class AutoFillAspect {

	@Pointcut("@annotation(com.biluo.annotation.UpdateAutoFill)")
	public void updateAutoFillPointCut() {
	}

	@Pointcut("@annotation(com.biluo.annotation.InsertAutoFill)")
	public void insertAutoFillPointCut() {
	}

	@Before("updateAutoFillPointCut()")
	public void updateAutoFill(JoinPoint point) throws Exception {
		MethodSignature signature = (MethodSignature) point.getSignature();
		UpdateAutoFill autoFill = signature.getMethod().getDeclaredAnnotation(UpdateAutoFill.class);

		// 获取注解中设置的属性名，从而获取set方法名
		String editTimeField = autoFill.editTimeField();
		String editByField = autoFill.editByField();
		String editTimeSetMethodName = "set" + editTimeField.substring(0, 1).toUpperCase()
				+ editTimeField.substring(1);
		String editBySetMethodName = "set" + editByField.substring(0, 1).toUpperCase()
				+ editByField.substring(1);
		Object arg = point.getArgs()[0];

		// 通过方法名和参数类型获取set方法对象，并调用方法填充属性值
		Method editTimeSetMethod = arg.getClass().getDeclaredMethod(editTimeSetMethodName, Date.class);
		editTimeSetMethod.invoke(arg, new Date());
		Method editBySetMethod = arg.getClass().getDeclaredMethod(editBySetMethodName, Integer.class);
		TUser user = (TUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		editBySetMethod.invoke(arg, user.getId());
	}

	@Before("insertAutoFillPointCut()")
	public void insertAutoFill(JoinPoint point) throws Exception {
		MethodSignature signature = (MethodSignature) point.getSignature();
		InsertAutoFill autoFill = signature.getMethod().getDeclaredAnnotation(InsertAutoFill.class);

		// 获取注解中设置的属性名，从而获取set方法名
		String editTimeField = autoFill.editTimeField();
		String editByField = autoFill.editByField();
		String createTimeField = autoFill.createTimeField();
		String createByField = autoFill.createByField();
		String editTimeSetMethodName = "set" + editTimeField.substring(0, 1).toUpperCase()
				+ editTimeField.substring(1);
		String editBySetMethodName = "set" + editByField.substring(0, 1).toUpperCase()
				+ editByField.substring(1);
		String createTimeSetMethodName = "set" + createTimeField.substring(0, 1).toUpperCase()
				+ createTimeField.substring(1);
		String createBySetMethodName = "set" + createByField.substring(0, 1).toUpperCase()
				+ createByField.substring(1);

		Object arg = point.getArgs()[0];


		// 通过方法名和参数类型获取set方法对象，并调用方法填充属性值
		Method editTimeSetMethod = arg.getClass().getDeclaredMethod(editTimeSetMethodName, Date.class);
		editTimeSetMethod.invoke(arg, new Date());
		Method editBySetMethod = arg.getClass().getDeclaredMethod(editBySetMethodName, Integer.class);
		TUser user = (TUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		editBySetMethod.invoke(arg, user.getId());
		Method createTimeSetMethod = arg.getClass().getDeclaredMethod(createTimeSetMethodName, Date.class);
		createTimeSetMethod.invoke(arg, new Date());
		Method createBySetMethod = arg.getClass().getDeclaredMethod(createBySetMethodName, Integer.class);
		createBySetMethod.invoke(arg, user.getId());
	}
}
