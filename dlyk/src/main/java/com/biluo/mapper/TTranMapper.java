package com.biluo.mapper;

import com.biluo.model.po.TTran;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

/**
* @author 797799421
* @description 针对表【t_tran(交易表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TTran
*/
@Mapper
public interface TTranMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TTran record);

    int insertSelective(TTran record);

    TTran selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTran record);

    int updateByPrimaryKey(TTran record);

    BigDecimal selectSuccessTranAmount();

    BigDecimal selectTotalTranAmount();

    int count();

    int countSuccessTran();
}
