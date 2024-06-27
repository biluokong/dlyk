package com.biluo.mapper;

import com.biluo.model.po.TSystemInfo;

/**
* @author 797799421
* @description 针对表【t_system_info(系统信息表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TSystemInfo
*/
public interface TSystemInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TSystemInfo record);

    int insertSelective(TSystemInfo record);

    TSystemInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSystemInfo record);

    int updateByPrimaryKey(TSystemInfo record);

}
