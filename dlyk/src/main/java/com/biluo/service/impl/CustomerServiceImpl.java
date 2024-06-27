package com.biluo.service.impl;

import com.biluo.annotation.InsertAutoFill;
import com.biluo.common.easyexcel.CustomerExcel;
import com.biluo.mapper.TClueMapper;
import com.biluo.mapper.TCustomerMapper;
import com.biluo.model.dto.CustomerDto;
import com.biluo.model.po.TClue;
import com.biluo.model.po.TCustomer;
import com.biluo.model.query.BaseQuery;
import com.biluo.model.query.CustomerQuery;
import com.biluo.service.CustomerService;
import com.biluo.utils.GlobalException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.biluo.utils.Constants.CLUE_STATUS_CUSTOMER;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	private final TCustomerMapper tCustomerMapper;
	private final TClueMapper tClueMapper;

	@Override
	@InsertAutoFill
	@Transactional(rollbackFor = Exception.class)
	public boolean addCustomer(TCustomer customer) {
		// 1.检查该线索状态是否已为-1，如果是，则不能转客户
		TClue clue = tClueMapper.selectByPrimaryKey(Long.valueOf(customer.getClueId()));
		if (clue.getState() == CLUE_STATUS_CUSTOMER) {
			throw new GlobalException("请勿重复转换，该线索已转换为客户");
		}

		// 2.将线索状态改为-1（变为已转客户）
		clue.setState(CLUE_STATUS_CUSTOMER);
		tClueMapper.updateByPrimaryKeySelective(clue);

		// 3.新增客户
		int count = tCustomerMapper.insertSelective(customer);
		return count > 0;
	}

	@Override
	public PageInfo<CustomerDto> getCustomerList(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CustomerDto> list = tCustomerMapper.list(new BaseQuery());
		return new PageInfo<>(list);
	}

	@Override
	public List<CustomerExcel> getCustomerExcelData(Integer[] ids) {
		CustomerQuery query = new CustomerQuery();
		query.setIds(ids);
		List<CustomerDto> customerDtoList = tCustomerMapper.getCustomerExcelData(query);
		return customerDtoList.stream().map(customerDto -> {
			CustomerExcel customerExcel = new CustomerExcel();
			BeanUtils.copyProperties(customerDto, customerExcel);
			customerExcel.setNeedLoadName(customerDto.getNeedLoanName());
			customerExcel.setProductName(customerDto.getIntentionProductName());
			return customerExcel;
		}).collect(Collectors.toList());
	}
}
