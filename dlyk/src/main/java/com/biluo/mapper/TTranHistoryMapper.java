package com.biluo.mapper;

import com.biluo.model.po.TTranHistory;

/**
* @author 797799421
* @description 针对表【t_tran_history(交易历史记录表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TTranHistory
*/
public interface TTranHistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TTranHistory record);

    int insertSelective(TTranHistory record);

    TTranHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTranHistory record);

    int updateByPrimaryKey(TTranHistory record);

}
