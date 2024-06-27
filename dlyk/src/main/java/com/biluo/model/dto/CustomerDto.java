package com.biluo.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CustomerDto {
	/**
	 * 主键，自动增长，客户ID
	 */
	private Integer id;

	/**
	 * 线索ID
	 */
	private Integer clueId;

	/**
	 * 负责人名称
	 */
	private String ownerName;

	/**
	 * 活动名称
	 */
	private String activityName;

	/**
	 * 姓名
	 */
	private String fullName;

	/**
	 * 称呼
	 */
	private String appellationName;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 微信
	 */
	private String weixin;

	/**
	 * qq
	 */
	private String qq;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 职业
	 */
	private String job;

	/**
	 * 年收入
	 */
	private BigDecimal yearIncome;

	/**
	 * 住址
	 */
	private String address;

	/**
	 * 是否贷款
	 */
	private String needLoanName;

	/**
	 * 意向状态
	 */
	private String intentionStateName;

	/**
	 * 线索状态
	 */
	private String stateName;

	/**
	 * 线索来源
	 */
	private String sourceName;

	/**
	 * 意向产品
	 */
	private String intentionProductName;

	/**
	 * 选购产品
	 */
	private Integer product;

	/**
	 * 客户描述
	 */
	private String description;

	/**
	 * 下次联系时间
	 */
	private Date nextContactTime;

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
}
