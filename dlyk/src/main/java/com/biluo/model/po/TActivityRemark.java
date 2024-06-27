package com.biluo.model.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 市场活动备注表
 * @TableName t_activity_remark
 */
@Data
@Accessors(chain = true)
public class TActivityRemark implements Serializable {
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
     * 备注编辑时间
     */
    private Date editTime;

    /**
     * 备注编辑人
     */
    private Integer editBy;

    /**
     * 删除状态（0正常，1删除）
     */
    private Integer deleted;

    private static final long serialVersionUID = 1L;
}