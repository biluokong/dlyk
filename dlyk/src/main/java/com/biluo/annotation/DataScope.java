package com.biluo.annotation;

import java.lang.annotation.*;

/**
 * 用于表记录查询时的权限控制，根据用户的权限，过滤掉不需要查询的记录
 * @aspect: DataScopeAspect
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
	/**
	 * 表的别名
	 */
	String tableAlias() default "";

	/**
	 * 表的字段名
	 */
	String tableField() default "";
}
