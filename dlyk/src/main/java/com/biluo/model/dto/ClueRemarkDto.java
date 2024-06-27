package com.biluo.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ClueRemarkDto {
	/**
	 * 主键，自动增长，线索备注ID
	 */
	private Integer id;

	/**
	 * 线索ID
	 */
	private Integer clueId;

	/**
	 * 跟踪方式
	 */
	private Integer noteWay;

	/**
	 * 跟踪方式名称
	 */
	private String noteWayName;

	/**
	 * 跟踪内容
	 */
	private String noteContent;

	/**
	 * 跟踪时间
	 */
	private Date createTime;

	/**
	 * 跟踪人
	 */
	private Integer createBy;

	/**
	 * 跟踪人名称
	 */
	private String createName;

	/**
	 * 编辑时间
	 */
	private Date editTime;

	/**
	 * 编辑人
	 */
	private Integer editBy;

	/**
	 * 编辑人名称
	 */
	private String editName;

	/**
	 * 删除状态（0正常，1删除）
	 */
	private Integer deleted;

}
