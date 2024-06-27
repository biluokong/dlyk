package com.biluo.service;

import com.biluo.model.dto.UserDto;
import com.biluo.model.po.TUser;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
	PageInfo<TUser> list(Integer pageNum, Integer pageSize);

	TUser getUserInfo(Long id);

	UserDto getUserDetail(Long id);

	boolean addUser(TUser user);

	boolean updateUser(TUser user);

	boolean deleteUser(Long id);

	boolean deleteUserBatch(Long[] ids);

	List<TUser> getAllUser();
}
