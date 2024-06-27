package com.biluo.model.dto;

import com.biluo.model.po.TDicValue;
import lombok.Data;

import java.util.List;

@Data
public class DictDto {
	/**
	 * 主键，自动增长，字典类型ID
	 */
	private Integer id;

	/**
	 * 字典类型代码
	 */
	private String typeCode;

	/**
	 * 字典类型名称
	 */
	private String typeName;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 对值的关联（一对多）
	 */
	private List<TDicValue> valueList;
}
