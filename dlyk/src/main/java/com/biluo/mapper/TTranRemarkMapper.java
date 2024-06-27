package com.biluo.mapper;

import com.biluo.model.po.TTranRemark;

/**
* @author 797799421
* @description 针对表【t_tran_remark(交易跟踪记录表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TTranRemark
*/
public interface TTranRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TTranRemark record);

    int insertSelective(TTranRemark record);

    TTranRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTranRemark record);

    int updateByPrimaryKey(TTranRemark record);

}
