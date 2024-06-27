package com.biluo.model.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 交易表
 * @TableName t_tran
 */
@Data
public class TTran implements Serializable {
    /**
     * 主键，自动增长，交易ID
     */
    private Integer id;

    /**
     * 交易流水号
     */
    private String tranNo;

    /**
     * 客户ID
     */
    private Integer customerId;

    /**
     * 交易金额
     */
    private BigDecimal money;

    /**
     * 预计成交日期
     */
    private Date expectedDate;

    /**
     * 交易所处阶段
     */
    private Integer stage;

    /**
     * 交易描述
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

    private static final long serialVersionUID = 1L;
}