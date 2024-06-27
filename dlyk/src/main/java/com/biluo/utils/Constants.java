package com.biluo.utils;

public class Constants {
	// security的登录和登出uri配置
	public static final String LOGIN_URL = "/login";
	public static final String LOGIN_OUT_URL = "/logout";

	// redis的key
	public static final String REDIS_TOKEN_KEY = "dlyk:token:";
	public static final String REDIS_USER_LIST_KEY = "dlyk:user:list";

	// redis中key的TTL（单位分钟）
	public static final Long REDIS_USER_LIST_TTL = 5L;

	// 分页的默认每页条数
	public static final String DEFAULT_PAGE_SIZE = "10";

	// 线索状态
	public static final int CLUE_STATUS_CUSTOMER = -1;
}
