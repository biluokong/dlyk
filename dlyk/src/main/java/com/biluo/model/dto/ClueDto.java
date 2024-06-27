package com.biluo.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ClueDto {
	/**
	 * 主键，自动增长，线索ID
	 */
	private Integer id;

	/**
	 * 线索所属人ID
	 */
	private Integer ownerId;

	/**
	 * 线索所属人名称
	 */
	private String ownerName;

	/**
	 * 活动ID
	 */
	private Integer activityId;

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
	private Integer appellation;

	/**
	 * 称呼的中文名称
	 */
	private String appellationName;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 微信号
	 */
	private String weixin;

	/**
	 * QQ号
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
	 * 地址
	 */
	private String address;

	/**
	 * 是否需要贷款（0不需要，1需要）
	 */
	private Integer needLoan;

	/**
	 * 是否需要贷款的中文描述
	 */
	private String needLoanName;

	/**
	 * 意向状态
	 */
	private Integer intentionState;

	/**
	 * 意向状态的中文描述
	 */
	private String intentionStateName;

	/**
	 * 意向产品
	 */
	private Integer intentionProduct;

	/**
	 * 意向产品中文描述
	 */
	private String intentionProductName;

	/**
	 * 线索状态
	 */
	private Integer state;

	/**
	 * 线索状态的中文描述
	 */
	private String stateName;

	/**
	 * 线索来源
	 */
	private Integer source;

	/**
	 * 显示来源的中文描述
	 */
	private String sourceName;

	/**
	 * 线索描述
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
	 * 创建人名称
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
}
