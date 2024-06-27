package com.biluo.controller;

import com.biluo.DlykApllication;
import com.biluo.model.result.R;
import com.biluo.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dict")
@RequiredArgsConstructor
public class DictController {
	private final DictService dictService;

	/**
	 * 根据字典类型code获取字段值列表
	 */
	@GetMapping("values/{typeCode}")
	public R getDictValues(@PathVariable String typeCode) {
		return R.OK(DlykApllication.dictCacheMap.get(typeCode));
	}

	/**
	 * 获取所有字典值列表和其对应关系（map）
	 */
	@GetMapping("all")
	public R getAllDictValues() {
		return R.OK(DlykApllication.dictCacheMap);
	}
}
