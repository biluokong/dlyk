package com.biluo.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
	/**
	 * 主键，自动增长，用户ID
	 */
	private Integer id;

	/**
	 * 登录账号
	 */
	private String loginAct;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 用户手机
	 */
	private String phone;

	/**
	 * 用户邮箱
	 */
	private String email;

	/**
	 * 账户是否没有过期，0已过期 1正常
	 */
	private Integer accountNoExpired;

	/**
	 * 密码是否没有过期，0已过期 1正常
	 */
	private Integer credentialsNoExpired;

	/**
	 * 账号是否没有锁定，0已锁定 1正常
	 */
	private Integer accountNoLocked;

	/**
	 * 账号是否启用，0禁用 1启用
	 */
	private Integer accountEnabled;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private Integer createBy;

	/**
	 * 编辑时间
	 */
	private Date editTime;

	/**
	 * 编辑人
	 */
	private Integer editBy;

	/**
	 * 最近登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 一对多关联：创建人、修改人
	 */
	private Integer createId;
	private String createName;
	private Integer editId;
	private String editName;
}
