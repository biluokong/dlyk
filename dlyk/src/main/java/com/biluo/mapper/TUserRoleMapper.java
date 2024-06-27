package com.biluo.mapper;

import com.biluo.model.po.TUserRole;

/**
* @author 797799421
* @description 针对表【t_user_role(用户角色关系表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TUserRole
*/
public interface TUserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TUserRole record);

    int insertSelective(TUserRole record);

    TUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUserRole record);

    int updateByPrimaryKey(TUserRole record);

}
