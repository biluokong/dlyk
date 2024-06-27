package com.biluo.service;

import com.biluo.common.easyexcel.CustomerExcel;
import com.biluo.model.dto.CustomerDto;
import com.biluo.model.po.TCustomer;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CustomerService {
	boolean addCustomer(TCustomer customer);

	PageInfo<CustomerDto> getCustomerList(Integer pageNum, Integer pageSize);

	List<CustomerExcel> getCustomerExcelData(Integer[] ids);
}
