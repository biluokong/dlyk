package com.biluo.common.task;

import com.biluo.DlykApllication;
import com.biluo.common.enums.ClueDictEnum;
import com.biluo.model.dto.DictDto;
import com.biluo.service.DictService;
import com.biluo.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 加载数据库字典数据的定时任务
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LoadClueDictTask {
	private final DictService dictService;
	private final ProductService productService;

	@Scheduled(cron = "${project.task.cron}")
	@PostConstruct
	public void loadDict() {
		System.out.println("定时任务执行：开始加载数据库中的字典数据");
		// 加载字典表数据
		List<DictDto> list = dictService.loadAllDict();
		for (DictDto dictDto : list) {
			String key = dictDto.getTypeCode();
			List valueList = dictDto.getValueList();
			DlykApllication.dictCacheMap.put(key, valueList);
		}

		// 加载产品表数据（对应意向产品字典）
		List productList = productService.getAllOnSaleProduct();
		DlykApllication.dictCacheMap.put(ClueDictEnum.INTENTION_PRODUCT.getCode(), productList);
	}
}
