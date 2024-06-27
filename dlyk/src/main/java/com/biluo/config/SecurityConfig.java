package com.biluo.config;

import com.biluo.filter.TokenFilter;
import com.biluo.model.po.TUser;
import com.biluo.model.result.R;
import com.biluo.utils.JWTUtils;
import com.biluo.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.concurrent.TimeUnit;

import static com.biluo.model.result.CodeEnum.ACCESS_DENIED;
import static com.biluo.utils.Constants.*;
import static com.biluo.utils.JWTUtils.DEFAULT_EXPIRE_TIME;
import static com.biluo.utils.JWTUtils.EXPIRE_TIME;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final StringRedisTemplate redisTemplate;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.formLogin((config) -> {
					config.loginProcessingUrl(LOGIN_URL)
						.usernameParameter("loginAct")
						.passwordParameter("loginPwd")
						.successHandler((request, response, authentication) -> {
							TUser user = (TUser) authentication.getPrincipal();
							String userJson = JsonUtil.toJson(user);

							// 生成jwt，如果勾选了记住我，则保存7天，否则保存30分钟
							String jwt = JWTUtils.createJWT(userJson);
							boolean rememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));
							redisTemplate.opsForValue().set(REDIS_TOKEN_KEY + user.getId(), jwt,
									rememberMe ? EXPIRE_TIME : DEFAULT_EXPIRE_TIME, TimeUnit.MINUTES);

							user.setLoginPwd(null);
							String res = JsonUtil.toJson(R.OK(user, jwt));
							response.setContentType("application/json;charset=utf-8");
							response.getWriter().write(res);
						})
						.failureHandler((request, response, exception) -> {
							String json = JsonUtil.toJson(R.FAIL(exception.getMessage()));
							response.setContentType("application/json;charset=utf-8");
							response.getWriter().write(json);
						});

				})
				.logout((config) -> {
					config.logoutUrl(LOGIN_OUT_URL)
						.logoutSuccessHandler((request, response, authentication) -> {
							TUser user = (TUser) authentication.getPrincipal();
							redisTemplate.delete(REDIS_TOKEN_KEY + user.getId());
							response.setContentType("application/json;charset=utf-8");
							response.getWriter().write(JsonUtil.toJson(R.OK()));
						});
				})
				.authorizeHttpRequests((config) -> {
					config.requestMatchers(LOGIN_URL).permitAll()
						.anyRequest().authenticated();
				})
				// 认证或授权失败时触发（配置了全局异常处理器，优先走全局异常处理器逻辑，走不到此处）
				/*.exceptionHandling((config) -> {
					// 没有权限时触发
					config.accessDeniedHandler((request, response, accessDeniedException) -> {
						String json = JsonUtil.toJson(R.FAIL(ACCESS_DENIED));
						response.getWriter().write(json);
					});
				})*/
				.csrf(AbstractHttpConfigurer::disable)
				// 禁用session，前后端分离不需要用session，用jwt
				.sessionManagement((config) -> {
					config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.addFilterBefore(new TokenFilter(redisTemplate), LogoutFilter.class)
				.build();
	}
}
