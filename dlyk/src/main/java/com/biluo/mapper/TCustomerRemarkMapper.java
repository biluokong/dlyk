package com.biluo.mapper;

import com.biluo.model.po.TCustomerRemark;

/**
* @author 797799421
* @description 针对表【t_customer_remark(客户跟踪记录表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TCustomerRemark
*/
public interface TCustomerRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TCustomerRemark record);

    int insertSelective(TCustomerRemark record);

    TCustomerRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCustomerRemark record);

    int updateByPrimaryKey(TCustomerRemark record);

}
