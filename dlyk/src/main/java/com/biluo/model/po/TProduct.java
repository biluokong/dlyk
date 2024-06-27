package com.biluo.model.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 产品表
 * @TableName t_product
 */
@Data
public class TProduct implements Serializable {
    /**
     * 主键，自动增长，线索ID
     */
    private Integer id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 官方指导起始价
     */
    private BigDecimal guidePriceS;

    /**
     * 官方指导最高价
     */
    private BigDecimal guidePriceE;

    /**
     * 经销商报价
     */
    private BigDecimal quotation;

    /**
     * 状态 0在售 1售罄
     */
    private Integer state;

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

    private static final long serialVersionUID = 1L;
}