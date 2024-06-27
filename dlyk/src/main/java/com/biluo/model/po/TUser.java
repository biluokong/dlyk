package com.biluo.model.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.biluo.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

/**
 * 用户表
 * @TableName t_user
 */
@Data
public class TUser implements UserDetails, Serializable {
    /**
     * 主键，自动增长，用户ID
     */
    private Integer id;

    /**
     * 登录账号
     */
    private String loginAct;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 账户是否没有过期，0已过期 1正常
     */
    private Integer accountNoExpired;

    /**
     * 密码是否没有过期，0已过期 1正常
     */
    private Integer credentialsNoExpired;

    /**
     * 账号是否没有锁定，0已锁定 1正常
     */
    private Integer accountNoLocked;

    /**
     * 账号是否启用，0禁用 1启用
     */
    private Integer accountEnabled;

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

    /**
     * 最近登录时间
     */
    private Date lastLoginTime;

    /**
     * 角色列表
     */
    private List<String> roleList;

    /**
     * 权限标识符（功能button权限列表）
     */
    private List<String> permistionList;

    /**
     * 菜单权限列表
     */
    private List<TPermission> menuPermissionList;

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        // 角色列表
        if (!ObjectUtils.isEmpty(getRoleList())) {
            getRoleList().forEach(role -> list.add(()->role));
        }
        // 权限列表
        if (!ObjectUtils.isEmpty(getPermistionList())) {
            getPermistionList().forEach(permission -> list.add(()->permission));
        }
        return list;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return loginPwd;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return loginAct;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return accountNoExpired == 1;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return accountNoLocked == 1;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNoExpired == 1;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return accountEnabled == 1;
    }
}