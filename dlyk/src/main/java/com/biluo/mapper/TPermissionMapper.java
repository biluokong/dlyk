package com.biluo.mapper;

import com.biluo.model.po.TPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 797799421
* @description 针对表【t_permission(权限表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TPermission
*/
@Mapper
public interface TPermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TPermission record);

    int insertSelective(TPermission record);

    TPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TPermission record);

    int updateByPrimaryKey(TPermission record);

	List<TPermission> menuListByUserId(Integer userId);

    List<TPermission> buttonListByUserId(Integer userId);
}
