package com.biluo.annotation;

import java.lang.annotation.*;

/**
 * 更新记录时自动填充date和user
 * @aspect: AutoFillAspect
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UpdateAutoFill {
	/**
	 * 修改时间属性名称
	 */
	String editTimeField() default "editTime";

	/**
	 * 修改人属性名称
	 */
	String editByField() default "editBy";
}
