package com.biluo.model.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 交易历史记录表
 * @TableName t_tran_history
 */
@Data
public class TTranHistory implements Serializable {
    /**
     * 主键，自动增长，交易记录ID
     */
    private Integer id;

    /**
     * 交易ID
     */
    private Integer tranId;

    /**
     * 交易阶段
     */
    private Integer stage;

    /**
     * 交易金额
     */
    private BigDecimal money;

    /**
     * 交易预计成交时间
     */
    private Date expectedDate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createBy;

    private static final long serialVersionUID = 1L;
}