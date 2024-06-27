package com.biluo.filter;

import com.biluo.model.po.TUser;
import com.biluo.model.result.CodeEnum;
import com.biluo.model.result.R;
import com.biluo.utils.Constants;
import com.biluo.utils.JWTUtils;
import com.biluo.utils.JsonUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.biluo.utils.Constants.LOGIN_OUT_URL;
import static com.biluo.utils.Constants.REDIS_TOKEN_KEY;
import static com.biluo.utils.JWTUtils.DEFAULT_EXPIRE_TIME;
import static com.biluo.utils.JWTUtils.EXPIRE_TIME;

@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
	private final StringRedisTemplate redisTemplate;
	// 创建一个单线程的线程池
	private final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (request.getRequestURI().contains(Constants.LOGIN_URL)) {
			filterChain.doFilter(request, response);
			return;
		}
		response.setContentType("application/json;charset=utf-8");
		String token = request.getHeader("Authorization");
		if (!StringUtils.hasText(token)) {
			String resJson = JsonUtil.toJson(R.FAIL(CodeEnum.TOKEN_IS_EMPTY));
			response.setStatus(401);
			response.getWriter().write(resJson);
			return;
		}
		if (!JWTUtils.verifyJWT(token)) {
			String resJson = JsonUtil.toJson(R.FAIL(CodeEnum.TOKEN_IS_ILLEGAL));
			response.setStatus(401);
			response.getWriter().write(resJson);
			return;
		}
		TUser user = JWTUtils.parseJWTGetUser(token);
		String redisToken = redisTemplate.opsForValue().get(REDIS_TOKEN_KEY + user.getId());
		if (redisToken == null) {
			String resJson = JsonUtil.toJson(R.FAIL(CodeEnum.TOKEN_IS_EXPIRE));
			response.setStatus(401);
			response.getWriter().write(resJson);
			return;
		}
		if (!token.equals(redisToken)) {
			String resJson = JsonUtil.toJson(R.FAIL(CodeEnum.TOKEN_IS_ILLEGAL));
			response.setStatus(401);
			response.getWriter().write(resJson);
			return;
		}
		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(user, user.getId(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 异步给token续续期
		EXECUTOR_SERVICE.submit(() -> {
			if (request.getRequestURI().contains(LOGIN_OUT_URL)) return;
			boolean rememberMe = Boolean.parseBoolean(request.getHeader("rememberMe"));
			if (rememberMe) {
				redisTemplate.expire(REDIS_TOKEN_KEY + user.getId(), EXPIRE_TIME, TimeUnit.MINUTES);
			} else {
				redisTemplate.expire(REDIS_TOKEN_KEY + user.getId(), DEFAULT_EXPIRE_TIME, TimeUnit.MINUTES);
			}
		});
		filterChain.doFilter(request, response);
	}
}
