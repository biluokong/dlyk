package com.biluo.mapper;

import com.biluo.model.po.TDicValue;

/**
* @author 797799421
* @description 针对表【t_dic_value(字典值表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TDicValue
*/
public interface TDicValueMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TDicValue record);

    int insertSelective(TDicValue record);

    TDicValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TDicValue record);

    int updateByPrimaryKey(TDicValue record);

}
