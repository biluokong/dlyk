package com.biluo.utils;

import com.biluo.model.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.biluo.model.result.CodeEnum.ACCESS_DENIED;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(GlobalException.class)
	public R handleGlobalException(GlobalException e) {
		log.error("全局异常处理：{}", e.getMessage());
		// e.printStackTrace();
		return R.FAIL(e.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public R handleAccessDeniedException(AccessDeniedException e) {
		log.error("权限不足异常处理：{}", e.getMessage());
		// e.printStackTrace();
		return R.FAIL(ACCESS_DENIED);
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e) {
		log.error("全局异常处理：{}", e.getMessage());
		e.printStackTrace();
		return R.FAIL("服务器内部异常");
	}
}
