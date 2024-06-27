package com.biluo.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityRemarkDto {
	/**
	 * 主键，自动增长，活动备注ID
	 */
	private Integer id;

	/**
	 * 活动ID
	 */
	private Integer activityId;

	/**
	 * 备注内容
	 */
	private String noteContent;

	/**
	 * 备注创建时间
	 */
	private Date createTime;

	/**
	 * 备注创建人
	 */
	private Integer createBy;

	/**
	 * 备注创建人名称
	 */
	private String createName;

	/**
	 * 备注编辑时间
	 */
	private Date editTime;

	/**
	 * 备注编辑人
	 */
	private Integer editBy;

	/**
	 * 备注编辑人名称
	 */
	private String editName;

	/**
	 * 删除状态（0正常，1删除）
	 */
	private Integer deleted;
}
