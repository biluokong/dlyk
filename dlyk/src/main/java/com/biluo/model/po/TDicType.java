package com.biluo.model.po;

import java.io.Serializable;
import lombok.Data;

/**
 * 字典类型表
 * @TableName t_dic_type
 */
@Data
public class TDicType implements Serializable {
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

    private static final long serialVersionUID = 1L;
}