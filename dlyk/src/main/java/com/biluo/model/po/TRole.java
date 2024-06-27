package com.biluo.model.po;

import java.io.Serializable;
import lombok.Data;

/**
 * 角色表
 * @TableName t_role
 */
@Data
public class TRole implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String role;

    /**
     * 
     */
    private String roleName;

    private static final long serialVersionUID = 1L;
}