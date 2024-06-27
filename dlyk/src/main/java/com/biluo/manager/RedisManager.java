package com.biluo.manager;

import com.biluo.model.po.TUser;
import com.biluo.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisManager {
	private final StringRedisTemplate redisTemplate;

	/**
	 * 根据key获取String类型的value
	 */
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 根据key获取json类型的value，并将json字符串转换为指定类型
	 */
	public <T> T get(String key, Class<T> clazz) {
		String value = redisTemplate.opsForValue().get(key);
		return value == null ? null : JsonUtil.toObject(value, clazz);
	}

	/**
	 * 根据key获取List，item为String类型
	 * @return 返回非null的List
	 */
	public List<String> getList(String key) {
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	/**
	 * 根据key获取List，item为json类型，并item转换为指定类型
	 * @return 返回非null的List
	 */
	public <T> List<T> getList(String key, Class<T> clazz) {
		List<String> list = redisTemplate.opsForList().range(key, 0, -1);
		if (ObjectUtils.isEmpty(list)) return Collections.emptyList();
		return JsonUtil.toList(list, clazz);
	}

	/**
	 * 设置List，item为json类型，并可以设置过期时间（单位分钟）
	 * @param ttl 过期时间，为null不设置
	 */
	public void setList(String key, List<String> list, Long ttl) {
		redisTemplate.opsForList().rightPushAll(key, list);
		if (ttl != null) {
			redisTemplate.expire(key, ttl, TimeUnit.MINUTES);
		}
	}
}
