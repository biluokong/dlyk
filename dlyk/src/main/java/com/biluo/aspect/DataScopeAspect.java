package com.biluo.aspect;

import com.biluo.annotation.DataScope;
import com.biluo.model.po.TUser;
import com.biluo.model.query.BaseQuery;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class DataScopeAspect {

	@Pointcut("@annotation(com.biluo.annotation.DataScope)")
	public void dataScopePointCut() {}

	@Before("dataScopePointCut()")
	public void doBefore(JoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		DataScope dataScope = signature.getMethod().getDeclaredAnnotation(DataScope.class);
		String tableAlias = dataScope.tableAlias();
		String tableField = dataScope.tableField();
		// 获取当前用户
		TUser user = (TUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> roleList = user.getRoleList();
		if (!roleList.contains("admin")) {
			Object arg = point.getArgs()[0];
			if (arg instanceof BaseQuery query) {
				query.setFilterSQL(" and " + tableAlias + "." + tableField + " = " + user.getId());
			}
		}
	}
}
