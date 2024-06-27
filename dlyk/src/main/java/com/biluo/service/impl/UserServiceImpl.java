package com.biluo.service.impl;

import com.biluo.annotation.InsertAutoFill;
import com.biluo.annotation.UpdateAutoFill;
import com.biluo.manager.RedisManager;
import com.biluo.mapper.TActivityMapper;
import com.biluo.mapper.TPermissionMapper;
import com.biluo.mapper.TRoleMapper;
import com.biluo.mapper.TUserMapper;
import com.biluo.model.dto.UserDto;
import com.biluo.model.po.TActivity;
import com.biluo.model.po.TPermission;
import com.biluo.model.po.TRole;
import com.biluo.model.po.TUser;
import com.biluo.model.query.BaseQuery;
import com.biluo.service.UserService;
import com.biluo.utils.Constants;
import com.biluo.utils.GlobalException;
import com.biluo.utils.JsonUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.biluo.utils.Constants.REDIS_USER_LIST_KEY;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final TUserMapper tUserMapper;
	private final PasswordEncoder passwordEncoder;
	private final TActivityMapper tActivityMapper;
	private final TRoleMapper tRoleMapper;
	private final RedisManager redisManager;
	private final TPermissionMapper tPermissionMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TUser user = tUserMapper.selectByLoginAct(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户不存在");
		}

		// 获取角色列表
		List<TRole> roleList = tRoleMapper.listByUserId(user.getId());
		List<String> StringRoleList = new ArrayList<>();
		for (TRole role : roleList) {
			StringRoleList.add(role.getRole());
		}
		user.setRoleList(StringRoleList);

		// 获取用户菜单权限列表
		List<TPermission> permistionList = tPermissionMapper.menuListByUserId(user.getId());
		user.setMenuPermissionList(permistionList);

		// 获取用户(button)功能权限列表
		permistionList = tPermissionMapper.buttonListByUserId(user.getId());
		List<String> StringPermissionList = new ArrayList<>();
		for (TPermission permission : permistionList) {
			StringPermissionList.add(permission.getCode());
		}
		user.setPermistionList(StringPermissionList);

		return user;
	}

	@Override
	public PageInfo<TUser> list(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TUser> users = tUserMapper.list(new BaseQuery());
		return new PageInfo<>(users);
	}

	@Override
	public TUser getUserInfo(Long id) {
		TUser user = tUserMapper.selectByPrimaryKey(id);
		user.setLoginPwd(null);
		return user;
	}

	@Override
	public UserDto getUserDetail(Long id) {
		return tUserMapper.getUserDetail(id);
	}

	@Override
	@InsertAutoFill
	@Transactional(rollbackFor = Exception.class)
	public boolean addUser(TUser user) {
		user.setLoginPwd(passwordEncoder.encode(user.getLoginPwd()));
		int count = tUserMapper.insertSelective(user);
		return count > 0;
	}

	@Override
	@UpdateAutoFill
	@Transactional(rollbackFor = Exception.class)
	public boolean updateUser(TUser user) {
		if (StringUtils.hasText(user.getLoginPwd())) {
			user.setLoginPwd(passwordEncoder.encode(user.getLoginPwd()));
		}
		int count = tUserMapper.updateByPrimaryKeySelective(user);
		return count > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteUser(Long id) {
		List<TActivity> tActivities = tActivityMapper.listByUserId(id);
		if (tActivities.size() > 0) {
			throw new GlobalException("删除失败，该用户绑定了相关活动");
		}
		int count = tUserMapper.deleteByPrimaryKey(id);
		return count > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteUserBatch(Long[] ids) {
		List<TActivity> tActivities = tActivityMapper.listByUserIds(ids);
		if (tActivities.size() > 0) {
			throw new GlobalException("删除失败，存在用户绑定了相关活动");
		}
		int count = tUserMapper.deleteByIds(ids);
		return count == ids.length;
	}

	@Override
	public List<TUser> getAllUser() {
		// 先从redis中查，查不到再从数据库查，并把数据库查询结果存到redis中（TTL-5分钟）
		List<TUser> userList = redisManager.getList(REDIS_USER_LIST_KEY, TUser.class);
		if (ObjectUtils.isEmpty(userList)) {
			userList = tUserMapper.getAllUser(new BaseQuery());
			List<String> strList = userList.stream().map(JsonUtil::toJson).toList();
			redisManager.setList(REDIS_USER_LIST_KEY, strList, Constants.REDIS_USER_LIST_TTL);
		}
		return userList;
	}
}
