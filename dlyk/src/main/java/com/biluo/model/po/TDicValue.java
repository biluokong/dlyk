package com.biluo.model.po;

import java.io.Serializable;
import lombok.Data;

/**
 * 字典值表
 * @TableName t_dic_value
 */
@Data
public class TDicValue implements Serializable {
    /**
     * 主键，自动增长，字典值ID
     */
    private Integer id;

    /**
     * 字典类型代码
     */
    private String typeCode;

    /**
     * 字典值
     */
    private String typeValue;

    /**
     * 字典值排序
     */
    private Integer order;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}