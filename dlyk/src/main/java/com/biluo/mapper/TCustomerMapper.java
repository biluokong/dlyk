package com.biluo.mapper;

import com.biluo.annotation.DataScope;
import com.biluo.model.dto.CustomerDto;
import com.biluo.model.po.TCustomer;
import com.biluo.model.query.BaseQuery;
import com.biluo.model.query.CustomerQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 797799421
* @description 针对表【t_customer(客户表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TCustomer
*/
@Mapper
public interface TCustomerMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TCustomer record);

    int insertSelective(TCustomer record);

    TCustomer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCustomer record);

    int updateByPrimaryKey(TCustomer record);

	@DataScope(tableAlias = "tc", tableField = "owner_id" )
	List<CustomerDto> list(BaseQuery query);

	@DataScope(tableAlias = "tc", tableField = "owner_id" )
	List<CustomerDto> getCustomerExcelData(CustomerQuery query);

	int count();
}
