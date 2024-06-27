package com.biluo.service;

import com.biluo.model.result.EchartsData;
import com.biluo.model.result.SummaryData;

import java.util.List;

public interface DashboardService {
	SummaryData getSummaryData();

	List<EchartsData> getSalesFunnelData();

	List<EchartsData> getClueSourcePieData();
}
