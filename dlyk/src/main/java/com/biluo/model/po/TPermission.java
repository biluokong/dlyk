package com.biluo.model.po;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 权限表
 * @TableName t_permission
 */
@Data
public class TPermission implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String code;

    /**
     * 权限url
     */
    private String url;

    /**
     * 权限类型
     */
    private String type;

    /**
     * 权限父级id
     */
    private Integer parentId;

    /**
     * 权限排序字段
     */
    private Integer orderNo;

    /**
     * 权限图标
     */
    private String icon;

    /**
     * 子级权限
     */
    private List<TPermission> subPermissionList;

    private static final long serialVersionUID = 1L;
}