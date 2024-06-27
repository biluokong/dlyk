package com.biluo.controller;

import com.biluo.model.po.TUser;
import com.biluo.model.result.R;
import com.biluo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	/**
	 * 根据分页条件查询用户列表
	 */
	@GetMapping("list")
	@PreAuthorize("hasAuthority('user:list')")
	public R list(@RequestParam(defaultValue = "1") Integer pageNum,
				  @RequestParam(defaultValue = "10") Integer pageSize) {
		return R.OK(userService.list(pageNum, pageSize));
	}

	/**
	 * 根据用户id查询用户基本信息
	 */
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('user:view')")
	public R getUserInfo(@PathVariable Long id) {
		return R.OK(userService.getUserInfo(id));
	}

	/**
	 * 根据id获取用户详情（包括创建人、修改人等信息）
	 */
	@GetMapping("detail/{id}")
	@PreAuthorize("hasAuthority('user:view')")
	public R getUserDetail(@PathVariable Long id) {
		return R.OK(userService.getUserDetail(id));
	}

	/**
	 * 添加用户
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('user:add')")
	public R addUser(@RequestBody TUser user) {
		boolean success = userService.addUser(user);
		return success ? R.OK() : R.FAIL();
	}

	/**
	 * 修改用户信息
	 */
	@PutMapping
	@PreAuthorize("hasAuthority('user:edit')")
	public R updateUser(@RequestBody TUser user) {
		boolean success = userService.updateUser(user);
		return success ? R.OK() : R.FAIL();
	}

	/**
	 * 根据id删除用户
	 */
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('user:delete')")
	public R deleteUser(@PathVariable Long id) {
		boolean success = userService.deleteUser(id);
		return success ? R.OK() : R.FAIL();
	}

	/**
	 * 批量删除用户
	 */
	@DeleteMapping("batch")
	@PreAuthorize("hasAuthority('user:delete')")
	public R deleteUserBatch(Long[] ids) {
		boolean success = userService.deleteUserBatch(ids);
		return success ? R.OK() : R.FAIL();
	}

	/**
	 * 获取所有用户的用户名和id
	 */
	@GetMapping("all")
	public R getAllUser() {
		List<TUser> users = userService.getAllUser();
		return R.OK(users);
	}
}
