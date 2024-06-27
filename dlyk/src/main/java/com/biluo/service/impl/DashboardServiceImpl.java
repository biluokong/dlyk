package com.biluo.service.impl;

import com.biluo.mapper.TActivityMapper;
import com.biluo.mapper.TClueMapper;
import com.biluo.mapper.TCustomerMapper;
import com.biluo.mapper.TTranMapper;
import com.biluo.model.result.EchartsData;
import com.biluo.model.result.SummaryData;
import com.biluo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
	private final TActivityMapper tActivityMapper;
	private final TClueMapper tClueMapper;
	private final TCustomerMapper tCustomerMapper;
	private final TTranMapper tTranMapper;

	@Override
	public SummaryData getSummaryData() {
		int effectiveActivityCount = tActivityMapper.getOngoingActivityList().size();
		int totalActivityCount = tActivityMapper.count();
		int totalClueCount = tClueMapper.count();
		int totalCustomerCount = tCustomerMapper.count();
		BigDecimal successTranAmount = tTranMapper.selectSuccessTranAmount();
		BigDecimal totalTranAmount = tTranMapper.selectTotalTranAmount();
		return SummaryData.builder()
				.effectiveActivityCount(effectiveActivityCount)
				.totalActivityCount(totalActivityCount)
				.totalClueCount(totalClueCount)
				.totalCustomerCount(totalCustomerCount)
				.successTranAmount(successTranAmount)
				.totalTranAmount(totalTranAmount)
				.build();
	}

	@Override
	public List<EchartsData> getSalesFunnelData() {
		int clueCount = tClueMapper.count();
		int customerCount = tCustomerMapper.count();
		int tranCount = tTranMapper.count();
		int tranSuccessCount = tTranMapper.countSuccessTran();
		ArrayList<EchartsData> result = new ArrayList<>();
		result.add(new EchartsData("线索", clueCount));
		result.add(new EchartsData("客户", customerCount));
		result.add(new EchartsData("交易", tranCount));
		result.add(new EchartsData("成交", tranSuccessCount));
		return result;
	}

	@Override
	public List<EchartsData> getClueSourcePieData() {
		tClueMapper.selectSourceCountByClueSource();
		return null;
	}
}
