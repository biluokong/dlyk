package com.biluo.annotation;

import java.lang.annotation.*;

/**
 * 插入记录时自动填充date和user
 * @aspect: AutoFillAspect
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InsertAutoFill {
	/**
	 * 创建时间属性名称
	 */
	String createTimeField() default "createTime";

	/**
	 * 创建人属性名称
	 */
	String createByField() default "createBy";

	/**
	 * 修改时间属性名称
	 */
	String editTimeField() default "editTime";

	/**
	 * 修改人属性名称
	 */
	String editByField() default "editBy";
}
