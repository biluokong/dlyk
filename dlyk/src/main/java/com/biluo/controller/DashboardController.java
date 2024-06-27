package com.biluo.controller;

import com.biluo.model.result.R;
import com.biluo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard")
@RequiredArgsConstructor
public class DashboardController {
	private final DashboardService dashboardService;

	/**
	 * 获取仪表盘概述数据
	 */
	@GetMapping(value = "summary")
	public R getSummaryData() {
		return R.OK(dashboardService.getSummaryData());
	}

	/**
	 * 获取销售漏斗图数据
	 */
	@GetMapping("salesFunnel")
	public R getSalesFunnelData() {
		return R.OK(dashboardService.getSalesFunnelData());
	}

	/**
	 * 获取线索来源饼图数据
	 */
	@GetMapping("clueSourcePie")
	public R getClueSourcePieData() {
		return R.OK(dashboardService.getClueSourcePieData());
	}
}
